/**
 * 
 * @author Djoumbou Feunang, Yannick, PhD
 *
 */

package wishartlab.biotransformer.utils;


import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openscience.cdk.AtomContainerSet;
import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.io.SDFWriter;
import org.openscience.cdk.io.iterator.IteratingSDFReader;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;

import ambit2.smarts.SMIRKSManager;
import ambit2.smarts.query.SMARTSException;
import wishartlab.biotransformer.biomolecule.Enzyme.EnzymeName;
import wishartlab.biotransformer.biosystems.BioSystem.BioSystemName;
import wishartlab.biotransformer.btransformers.Biotransformer;
import wishartlab.biotransformer.transformation.Biotransformation;
import wishartlab.biotransformer.transformation.MetabolicReaction;
import wishartlab.biotransformer.transformation.MRPatterns.ReactionName;

public class BioTransformerDBBuilder {

	public BioTransformerDBBuilder() {
		// TODO Auto-generated constructor stub
				
	}
	
	public void buildCYPspecificitySDFFileFromDB(String inputFileName, String outputFileName) throws IOException, CDKException{
		// Opening file
		BufferedReader bReader = new BufferedReader (new FileReader("data/CYP450_Substrate-Product_BioTransformerDB.tsv"));
		LinkedHashMap<String,IAtomContainer> compounds = new LinkedHashMap<String,IAtomContainer>();
		LinkedHashMap<String,HashSet<String>> compoundsToCyps= new LinkedHashMap<String,HashSet<String>>();
		
		SmilesParser sp =  new SmilesParser(SilentChemObjectBuilder.getInstance());
		SDFWriter sdfWriter = new SDFWriter(new FileOutputStream(outputFileName));
		
		SDFWriter sdfWriterNovel = new SDFWriter(new FileOutputStream( "data/novel.sdf"));
		
		
		String line;
		int counter = 0;
		boolean novel = false;
		ArrayList<String> novel_inchis = new ArrayList<String>();
		
		while((line = bReader.readLine()) != null ){
			counter++;
			
			String[] sline = line.split("\t");
			
			System.out.println(line);
			System.out.println(sline.length);
			

			
			
			if(sline.length > 9 && sline[2] != null && sline[2].split("-").length == 3){
				
				if(novel == false && sline[2].trim().contains("VHYCDWMUTMEGQY-KRWDZBQOSA-N")){
					novel = true;
				}
				
				if(novel == true){
					novel_inchis.add(sline[2]);
				}
				
				System.out.println(novel);
				if(!compounds.containsKey(sline[2])){
					IAtomContainer cpd = sp.parseSmiles(sline[1]);
					StructureDiagramGenerator sdg = new StructureDiagramGenerator();
					sdg.setMolecule(cpd);
					sdg.generateCoordinates();
					IAtomContainer layedOutMol = sdg.getMolecule();
					
					layedOutMol.setProperty(CDKConstants.TITLE, sline[0]);
					layedOutMol.setProperty("InChIKey", sline[2]);
					layedOutMol.setProperty("PubChemID", sline[5]);
					layedOutMol.setProperty("PubChemID", sline[5]);
					if(sline.length > 14 && sline[15] != null){
						layedOutMol.setProperty("References", sline[15].replace("||", "\n"));
					}else
					{
						layedOutMol.setProperty("References", null);
					}
					
					compounds.put(sline[2], layedOutMol);
					compoundsToCyps.put(sline[2], new HashSet<String>());
				}
				
				String[] enzymes = sline[9].split(";");
				for(String e : enzymes){
					compoundsToCyps.get(sline[2]).add(e.trim());
				}

			}
		}
		
		for(Entry<String, IAtomContainer> cp : compounds.entrySet()){
			if(compoundsToCyps.get(cp.getKey()).size()>0){
				ArrayList sortedList = new ArrayList(compoundsToCyps.get(cp.getKey()));
				Collections.sort(sortedList);
				String sorted = StringUtils.join( sortedList, "; " );
				
			cp.getValue().setProperty("Metabolizing Enzymes", sorted);
			sdfWriter.write(cp.getValue());
			
			if(novel_inchis.contains(cp.getKey())){
				sdfWriterNovel.write(cp.getValue());
			}
			
			} else{
				compounds.remove(cp.getKey());
			}
		}
		
		

		sdfWriter.close();
		sdfWriterNovel.close();
		bReader.close();

	}
	
	
	public void buildBiotransformationList(String reactionOntologyFileName, String biotransformationsFileName) throws Exception{

		IChemObjectBuilder 	builder = SilentChemObjectBuilder.getInstance();
		SmilesParser sp = new SmilesParser(builder);
		
		/**
		 * Parse reaction ontology
		 */
		BufferedReader roReader = new BufferedReader(new FileReader(reactionOntologyFileName));
		LinkedHashMap<String,ArrayList<String>> childrenParents = new LinkedHashMap<String,ArrayList<String>>();
		LinkedHashMap<ReactionName,MetabolicReaction> mReact =  new LinkedHashMap<ReactionName,MetabolicReaction>();
		SMIRKSManager smrkMan = new SMIRKSManager(SilentChemObjectBuilder.getInstance());
		
		
		String line;		
		while((line = roReader.readLine()) != null){
			
			String[] sline = line.split("\t");		
//			System.out.println(line);
//			System.out.println(sline.length);

			if(sline.length>0 && !sline[0].contentEquals("Children")){				
				// TO BE PERFECTED. THIS IS NOT ADDING THE ANCESTORS FOR EACH OTHER THE PARENTS YET.
				ArrayList<String> p = new ArrayList<String>();
				if(sline.length > 3 && sline[1] != null){					
					for( String a : sline[1].split("; ")){
						if(a.trim().length()>1){
							p.add(a);
						}
						
					}
					ArrayList<String> smarts = new ArrayList<String>();
					smarts.add(sline[3]);
//					System.out.println(sline[2]);
					MetabolicReaction mm = new MetabolicReaction(ReactionName.valueOf(sline[0]), sline[2], smarts, new ArrayList<String>(), smrkMan);
					mReact.put(ReactionName.valueOf(sline[0]), mm);
				}				
				childrenParents.put(sline[0],p);
			}	
		}
		
		/**
		 * extend the ontology
		 * 
		 */
		
		LinkedHashMap<String,ArrayList<String>> childrenParentsExtended = new LinkedHashMap<String,ArrayList<String>>();
		childrenParentsExtended = extendChildrenParentAssociations(childrenParents);

		/**
		 * Create report files
		 */
		
		String bioTline;
		BufferedReader bReader = new BufferedReader (new FileReader(biotransformationsFileName));
		LinkedHashMap<String, AtomContainerSet> btSubstrates = new  LinkedHashMap<String, AtomContainerSet>();
		ArrayList<Biotransformation> biotransformations =  new ArrayList<Biotransformation>();
		LinkedHashMap<EnzymeName, ArrayList<Biotransformation>>  cypBiotransformations = new LinkedHashMap<EnzymeName, ArrayList<Biotransformation>>();
		ArrayList<Biotransformation>  cypBiotransformationsAllCYPs = new ArrayList<Biotransformation>();
		int btCounter = 0;
		int annotatedCompounds = 0;
		int bioTransformationExtenedCount = 0;
		
		LinkedHashMap<String, IAtomContainer> containers = new LinkedHashMap<String, IAtomContainer>();
		
		// object mapping reactions with their nr of reported associations to compounds (biotransformations), 
		// and number of triggering compounds (compounds that theoretically fulfill the reaction's constraints
		// (expressed by smarts and negative smarts).
		LinkedHashMap<ReactionName,ArrayList<Integer>> reactTriggers = new LinkedHashMap<ReactionName,ArrayList<Integer>>();


		// for every compound, give the list of reactions that it triggers and undergoes (based on reported data) 
		// and a list of reaction it triggers but is not reported to undergo.
		LinkedHashMap<IAtomContainer, ArrayList<ArrayList<ReactionName>>> reactSubstrates = new LinkedHashMap<IAtomContainer, ArrayList<ArrayList<ReactionName>>>();
		
		// for every reaction, give the list of compounds that triggers the reaction but is NOT reported as 
		// undergoing that reaction.
		LinkedHashMap<ReactionName, IAtomContainerSet> triggerNoSubAllCYPs = new LinkedHashMap<ReactionName, IAtomContainerSet>();

		while((bioTline = bReader.readLine()) != null){
			
			/**
			 * Read the line. If there is at least one reaction and one enzyme, then:
			 * - extend the list of reactions by adding their parents
			 * - for each reaction, create a biotransformation
			 * - for each enzyme, add the biotransformation 
			 * in their respective biotransformations array
			 */
			
			String[] sbioTline = bioTline.split("\t");
			if(sbioTline.length > 10 && sbioTline[2] != null && sbioTline[2].split("-").length == 3
					&& sbioTline[7] != null && sbioTline[9].contains("CYP")){
				btCounter++;
				ArrayList<EnzymeName> enz = new ArrayList<EnzymeName>();
				for(String e : sbioTline[9].split(";")){
					if(EnumUtils.isValidEnum(EnzymeName.class, e.trim().split(" ")[0])){
						enz.add(EnzymeName.valueOf(e.trim().split(" ")[0]));
					}			 
				}
				
				 IAtomContainer sub = sp.parseSmiles(sbioTline[1]);
				 sub.setProperty(CDKConstants.TITLE, sbioTline[0]);
				 sub.setProperty("InChIKey", sbioTline[2]);

				 if(!containers.containsKey(sub.getProperty("InChIKey"))){
					 containers.put((String) sub.getProperty("InChIKey"), sub);
				 }

				 IAtomContainerSet substrates = DefaultChemObjectBuilder.getInstance().newInstance(IAtomContainerSet.class);	
				 substrates.addAtomContainer(sub);
				 IAtomContainerSet products = DefaultChemObjectBuilder.getInstance().newInstance(IAtomContainerSet.class);		

				 IAtomContainer prod = sp.parseSmiles(sbioTline[11]);
				 prod.setProperty(CDKConstants.TITLE, sbioTline[10]);
				 
				 products.addAtomContainer(prod);
				 
				 ArrayList<String> allReact = new ArrayList<String>();
				 
				 for(String rc : sbioTline[7].trim().split("; ")){
					 if(rc.trim().length()>0 && EnumUtils.isValidEnum(ReactionName.class, rc)){
						 allReact.add(rc.trim());
						 allReact.addAll(childrenParentsExtended.get(rc.trim()));						 
					 }
				 }
				 System.out.println(StringUtils.join(Utilities.removeDuplicateStrings(allReact), "; "));
				 
				for( String r : Utilities.removeDuplicateStrings(allReact)){	
					if(r.length()>1 && EnumUtils.isValidEnum(ReactionName.class, r)){					
						
						Biotransformation bt = new Biotransformation(
								
								substrates, ReactionName.valueOf(r), enz, products, BioSystemName.HUMAN);
						cypBiotransformationsAllCYPs.add(bt);
						bioTransformationExtenedCount++;
						for(EnzymeName n : enz){
							if(cypBiotransformations.containsKey(n)){
								cypBiotransformations.get(n).add(bt);
							}else
							{
								cypBiotransformations.put(n, new ArrayList<Biotransformation>());
								cypBiotransformations.get(n).add(bt);
							}						
						}	
					}
				}
			}	
		}
		bReader.close();
		ArrayList<EnzymeName> reportedReactions = new ArrayList<EnzymeName>(cypBiotransformations.keySet());
		Collections.sort(reportedReactions);
		for(EnzymeName m : reportedReactions){
			System.out.println(String.format("%6s\t%4d", m, cypBiotransformations.get(m).size()));
		}
		
		LinkedHashMap<ReactionName,IAtomContainerSet> occurrencesallCYPs = new LinkedHashMap<ReactionName,IAtomContainerSet>();
		LinkedHashMap<ReactionName,IAtomContainerSet> occurrencesallCypSpecific = new LinkedHashMap<ReactionName,IAtomContainerSet>();
			
		for(Biotransformation b : cypBiotransformationsAllCYPs){
			if(occurrencesallCYPs.containsKey(b.getReactionType())){				
				occurrencesallCYPs.get(b.getReactionType()).addAtomContainer(b.getSubstrates().getAtomContainer(0));; 
			}else{
				IAtomContainerSet ct = DefaultChemObjectBuilder.getInstance().newInstance(IAtomContainerSet.class);
				ct.addAtomContainer(b.getSubstrates().getAtomContainer(0));
				occurrencesallCYPs.put(b.getReactionType(), ct);
			}
		}
		
//		System.out.println("Nr of compounds in the database: " + containers.size());
		LinkedHashMap<ReactionName,IAtomContainerSet> triggersAllCYPs = new LinkedHashMap<ReactionName,IAtomContainerSet>();
		LinkedHashMap<ReactionName,Double> occurrenceRatiosAllCYPs = new LinkedHashMap<ReactionName,Double>();
		
		
		
		for(Map.Entry<ReactionName, MetabolicReaction> rno : mReact.entrySet()){		
//			System.out.println("REACTION: " + rno.getKey());
			
			triggerNoSubAllCYPs.put(rno.getKey(), DefaultChemObjectBuilder.getInstance().newInstance(IAtomContainerSet.class));
			
//			if(occurrencesallCYPs.containsKey(rno.getKey()) ){
//				System.out.println("AC count: "+ occurrencesallCYPs.get(rno.getKey()).getAtomContainerCount());
//			}
			
			for(Map.Entry<String, IAtomContainer> c : containers.entrySet()){
				IAtomContainer cs = ChemStructureManipulator.preprocessContainer(c.getValue());
				boolean t = ChemStructureExplorer.compoundMatchesReactionConstraints(rno.getValue(), cs);
				if(t){
					if(!triggersAllCYPs.containsKey(rno.getKey())){
						
						IAtomContainerSet ctt = DefaultChemObjectBuilder.getInstance().newInstance(IAtomContainerSet.class);
//						ctt.addAtomContainer(cs);	
						ctt.addAtomContainer(c.getValue());
						triggersAllCYPs.put(rno.getKey(), ctt);
					}else{
//						triggersAllCYPs.get(rno.getKey()).addAtomContainer(cs);
						triggersAllCYPs.get(rno.getKey()).addAtomContainer(c.getValue());
					}
					
					if(!reactSubstrates.containsKey( c.getValue() )){
						ArrayList<ArrayList<ReactionName>> n = new ArrayList<ArrayList<ReactionName>>();
						// reactions it triggers and undergoes
						n.add(new ArrayList<ReactionName>() );
						// reactions it triggers but does not undergoes
						n.add(new ArrayList<ReactionName>() );						
						reactSubstrates.put(c.getValue(), n);
					}
//					System.out.println("Compound: " + c.getValue().getProperty("InChIKey"));
					
					if( occurrencesallCYPs.containsKey(rno.getKey()) && ChemStructureExplorer.atomContainerInclusionHolds(occurrencesallCYPs.get(rno.getKey()), c.getValue())   ){
						reactSubstrates.get(c.getValue()).get(0).add(rno.getKey());						
					}else{
						reactSubstrates.get(c.getValue()).get(1).add(rno.getKey());
						triggerNoSubAllCYPs.get(rno.getKey()).addAtomContainer(c.getValue());
					}
				}
			}
			
			if(!triggersAllCYPs.containsKey(rno.getKey())){
				triggersAllCYPs.put(rno.getKey(), DefaultChemObjectBuilder.getInstance().newInstance(IAtomContainerSet.class));
			}
			
			if(!occurrencesallCYPs.containsKey(rno.getKey())){
				 occurrencesallCYPs.put(rno.getKey(), DefaultChemObjectBuilder.getInstance().newInstance(IAtomContainerSet.class));
			}

			reactTriggers.put(rno.getKey(), new ArrayList<Integer>(Arrays.asList(occurrencesallCYPs.get(rno.getKey()).getAtomContainerCount(), triggersAllCYPs.get(rno.getKey()).getAtomContainerCount())
					)); 
		}

		BufferedWriter bw0 = new BufferedWriter(new FileWriter("/Users/yandj/Programming/Projects/Metabolism/BioTransformerDB/CYP450_Reactions_OCRatios_Apr24.tsv"));

		for(Map.Entry<ReactionName, ArrayList<Integer>> rno : reactTriggers.entrySet()){
			if(rno.getValue().get(1)>0){
				bw0.write(rno.getKey() + "\t" + rno.getValue().get(0) + "\t" + rno.getValue().get(1) + 
						"\t" + (float)rno.getValue().get(0)/(float)rno.getValue().get(1)  );
//				System.out.println(String.format("%-40s\t%10d\t%5d\t%5.3f", rno.getKey(), rno.getValue().get(0), rno.getValue().get(1), (float)rno.getValue().get(0)/(float)rno.getValue().get(1)     ));
				bw0.newLine();
			}
			
//			else
//				System.out.println(String.format("%-40s\t%10d\t%5d", rno.getKey(), rno.getValue().get(0), rno.getValue().get(1) ));		
		}
		
		bw0.close();
		
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/yandj/Programming/Projects/Metabolism/BioTransformerDB/CYP450_Compound_reports_Apr24.tsv"));
			
			bw.write("Compound'sName\tCompound's InChIKey\tReactionsUndergone\tReactionsTriggeredButNotUndergone");
			bw.write("\n");
			for(Map.Entry<IAtomContainer, ArrayList<ArrayList<ReactionName>>> assoc : reactSubstrates.entrySet()){				
				String content = "";
				content = content + assoc.getKey().getProperty(CDKConstants.TITLE) + "\t";
				content = content + assoc.getKey().getProperty("InChIKey") + "\t";
				content = content + StringUtils.join(assoc.getValue().get(0), "; ") + "\t";
				content = content + StringUtils.join(assoc.getValue().get(1), "; ");				
				bw.write(content);
				bw.write("\n");
			}
			
			bw.close();
			
		}catch (IOException e) {

			e.printStackTrace();

		}
		
		
		try {
			BufferedWriter bw2 = new BufferedWriter(new FileWriter("/Users/yandj/Programming/Projects/Metabolism/BioTransformerDB/CYP450_Reactions_Substrates_and_NotReportedTriggers.tsv"));

			for(Map.Entry<ReactionName, IAtomContainerSet> rcpd : triggerNoSubAllCYPs.entrySet()){
				String content_ = rcpd.getKey().toString() + "\t";

				if(occurrencesallCYPs.containsKey(rcpd.getKey())){
					ArrayList<String> cNames =  new ArrayList<String>();
					for(IAtomContainer c : occurrencesallCYPs.get(rcpd.getKey()).atomContainers()){
						cNames.add(c.getProperty(CDKConstants.TITLE).toString());
					}
					content_ = content_ + StringUtils.join(cNames, "; ");
				}
				
				content_ = content_ + "\t";
				
				ArrayList<String> tnsNames = new ArrayList<String>();
				
				for(IAtomContainer cc : rcpd.getValue().atomContainers()){
					tnsNames.add(cc.getProperty(CDKConstants.TITLE).toString());
				}
				
				content_ = content_ + StringUtils.join(tnsNames, "; ");
				
				bw2.write(content_);
				bw2.newLine();
			}
			
			bw2.close();
			
		}catch  (IOException f) {
			f.printStackTrace();
		}
		
		
		System.out.println("biotransformations: " + btCounter);
		System.out.println("bioTransformationExtenedCount: " + bioTransformationExtenedCount);
		System.out.println("annotated compounds: " + containers.keySet().size());
		
		
		System.out.println("Done");
	}
	
	public void buildCYP450ReactionLibrary(String biotransformationsFileName) throws IOException{
		BufferedReader bReader = new BufferedReader (new FileReader(biotransformationsFileName));
		LinkedHashMap<EnzymeName, Set<ReactionName>>  cypReactionList = new LinkedHashMap<EnzymeName, Set<ReactionName>>();
		String bioTline;
		int btCounter = 0;
				
		while((bioTline = bReader.readLine()) != null){
			String[] sbioTline = bioTline.split("\t");
			btCounter++;
			
			if(sbioTline.length > 10 && sbioTline[2] != null && sbioTline[2].split("-").length == 3
					&& sbioTline[7] != null && sbioTline[7].trim().length()>1 && sbioTline[9].contains("CYP")){
				
				System.out.println(btCounter);
				System.out.println(sbioTline[7] + "<-->" + sbioTline[9]);
				
				
				String[] enzlist = sbioTline[9].trim().split(";");
				String[] rlist = sbioTline[7].trim().split(";");
				for(String i : enzlist){
					i = i.trim().replaceAll("major", "").replaceAll("minor", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("implied", "").trim();
					if(cypReactionList.containsKey(EnzymeName.valueOf(i))){
						
						for(String r : rlist){
							cypReactionList.get(EnzymeName.valueOf(i.trim())).add(ReactionName.valueOf(r.trim()));
						}
						
					}else{
						cypReactionList.put(EnzymeName.valueOf(i.trim()),new HashSet<ReactionName>());
						for(String r : rlist){
							cypReactionList.get(EnzymeName.valueOf(i.trim())).add(ReactionName.valueOf(r.trim()));
						}
						
					}					
				}				
			}
		}
		bReader.close();
		for(Map.Entry<EnzymeName, Set<ReactionName>> clist : cypReactionList.entrySet()){
			System.out.println('"' + clist.getKey().name() + "\": [");
			for(ReactionName rn : clist.getValue()){
				System.out.println("\t\"" + rn.name() + "\",");
			}
			System.out.println("],");
		}
	}

	
    public void updateMetabolicReactions() throws JsonParseException, JsonMappingException, IOException{
    	ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ALLOW_COMMENTS, true);
		mapper.configure(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
		SMIRKSManager smrkMan = new SMIRKSManager(SilentChemObjectBuilder.getInstance());
		
		/*
		 * Parsing the JSON files that constitute the database.
		 */				
		File metabolicReactions = new File("database/metabolicReactions_orig.json");
		LinkedHashMap<String,Object> allRe = (LinkedHashMap<String, Object>) mapper.readValue(metabolicReactions, Map.class).get("reactions");
		LinkedHashMap<ReactionName, MetabolicReaction> mReactionsOld = new LinkedHashMap<ReactionName, MetabolicReaction>();
		
		for(Map.Entry<String, Object> mre : allRe.entrySet()){
//			System.out.println(mre.getKey());
//			System.out.println(mre.getValue().getClass());
			LinkedHashMap<String, String> l_ = (LinkedHashMap<String, String>) mre.getValue();
			LinkedHashMap<String, ArrayList<String>> l = (LinkedHashMap<String, ArrayList<String>>) mre.getValue();
//			System.out.println(l.get("smirks").getClass());
//			System.out.println(l.get("smarts").getClass());
//			System.out.println(l.get("negativeSmarts").getClass());
			String smirks = (String) l.values().toArray()[0];
			ArrayList<String> smarts = (ArrayList<String>) l.values().toArray()[1];
			ArrayList<String> negativeSmarts = (ArrayList<String>) l.values().toArray()[2];
//			ArrayList<String> smarts = l.get("smarts");
//			ArrayList<String> negativeSmarts = l.get("negativeSmarts");
//			
			System.out.println(mre.getKey());
			System.out.println(l.values().toArray()[0]);
			System.out.println(l.values().toArray()[1]);
			System.out.println(l.values().toArray()[2]);
//			System.out.println(smarts);
//			System.out.println(negativeSmarts);
			
			
			MetabolicReaction m =  new MetabolicReaction(ReactionName.valueOf(mre.getKey()), smirks,
					smarts, negativeSmarts, smrkMan);
			
			mReactionsOld.put(ReactionName.valueOf(mre.getKey()),m);
		}

		
		LinkedHashMap<ReactionName,ArrayList<String>> rDescriptions = new LinkedHashMap<ReactionName,ArrayList<String>>();
		BufferedReader bReader = new BufferedReader(new FileReader("/Users/yandj/Programming/Projects/Metabolism/BioTransformerDB/Reaction_Descriptions_May142017.tsv"));
		
		LinkedHashMap<ReactionName, MetabolicReaction>  mReactionsAdded = new LinkedHashMap<ReactionName, MetabolicReaction>();
		
		String bioTline;
		int btCounter = 0;
				
		while((bioTline = bReader.readLine()) != null){
			String[] sbioTline = bioTline.split("\t");
			btCounter++;
			// (sbioTline.length>2 && !sbioTline[2].contains("Reaction ID"))
			if( (!bioTline.contains("Curator"))  
					&& sbioTline.length > 11 
					&& sbioTline[2] != null && ReactionName.valueOf(sbioTline[2].trim()) != null
					&& sbioTline[11].trim() != null && sbioTline[12].trim() != null
					){
				
				if(!rDescriptions.containsKey(sbioTline[2].trim())){
//					System.out.println(sbioTline[2].trim());
					rDescriptions.put(ReactionName.valueOf(sbioTline[2].trim()), new ArrayList<String>());
					rDescriptions.get(ReactionName.valueOf(sbioTline[2].trim())).add(sbioTline[11].trim());
					rDescriptions.get(ReactionName.valueOf(sbioTline[2].trim())).add(sbioTline[12].trim());
				}
//				else{
//					System.err.println(sbioTline[2]);
//				}
//				ArrayList<String> s1 =  new ArrayList<String>();
				ArrayList<String> s1 =  new ArrayList<String>();
				s1.add(sbioTline[12].trim());
				MetabolicReaction n = new MetabolicReaction(ReactionName.valueOf(sbioTline[2].trim()),
						sbioTline[11].trim(),
						s1,
						new ArrayList<String>(),
						smrkMan					
					);
				
				mReactionsAdded.put(ReactionName.valueOf(sbioTline[2].trim()), n);
			}
			else{
//				System.err.println(sbioTline[2]);
			}
		}

		
		System.out.println(mReactionsOld.size());
		System.out.println(mReactionsAdded.size());
		
		for(Map.Entry<ReactionName, MetabolicReaction> n : mReactionsOld.entrySet()){
			if(mReactionsAdded.containsKey(n.getKey()) && n.getValue().getNegativeReactantsSMARTS().size() == 0 ){
				mReactionsOld.put(n.getKey(), mReactionsAdded.get(n.getKey()));				
			}
		}
		
		for(Map.Entry<ReactionName, MetabolicReaction> nn : mReactionsAdded.entrySet()){
			if(!mReactionsOld.containsKey(nn.getKey())){
				mReactionsOld.put(nn.getKey(), nn.getValue());	
			}
			
		}
		
		System.out.println(mReactionsOld.size());
		
//		File metaboReact = new File("/Users/yandj/Programming/Projects/Metabolism/BioTransformerDB/metabolicReactions.json");
		
		BufferedWriter bw0 = new BufferedWriter(new FileWriter("/Users/yandj/Programming/Projects/Metabolism/BioTransformerDB/metabolicReactions.json"));
		
		bw0.write("{");
		bw0.newLine();
		bw0.write("\t" + "\"table\" : \"metabolicReactions\",");
		bw0.newLine();
		bw0.write("\t" + "\"description\" : \"this table provides the list of reactions, their smirks, smarts, and negative smarts\",");
		bw0.newLine();
		bw0.write("\"reactions\" : {");
		bw0.newLine();
		
		for(Map.Entry<ReactionName, MetabolicReaction> z : mReactionsOld.entrySet()){
			System.out.println(z.getValue().name);
			bw0.write("\t" + "\"" + z.getKey().name()  + "\" : {");
			bw0.newLine();
			bw0.write("\t\t" + "\"smirks\" : \"");
			bw0.write(z.getValue().getReactionSMIRKS().toString());
			bw0.write("\",");
			bw0.newLine();
			
			bw0.write("\t\t" + "\"smarts\" : [" );
			bw0.newLine();
			if(z.getValue().getReactantSMARTS().size() == 1){
				bw0.write("\t\t\t" + '"' +  z.getValue().getReactantSMARTS().get(0) + '"');
			} 
			else if(z.getValue().getReactantSMARTS().size() > 1){
				for(int i=0; i < z.getValue().getReactantSMARTS().size()-1; i++){
					bw0.write("\t\t\t" +  '"' +  z.getValue().getReactantSMARTS().get(i) + "\",");
					bw0.newLine();
				}	
			bw0.write("\t\t\t" + "\"" +  z.getValue().getReactantSMARTS().get(z.getValue().getReactantSMARTS().size()-1) + "\"");

			}
			bw0.newLine();
			bw0.write("\t\t],");
			bw0.newLine();
			
			bw0.write("\t\t" + "\"negativeSmarts\" : [" );
			bw0.newLine();
			if(z.getValue().getNegativeReactantsSMARTS().size() == 1){
				bw0.write("\t\t\t" +  "\"" +  z.getValue().getNegativeReactantsSMARTS().get(0) + "\"");
			} 
			else if(z.getValue().getNegativeReactantsSMARTS().size() > 1){
				for(int i=0; i < z.getValue().getNegativeReactantsSMARTS().size()-1; i++){
					bw0.write("\t\t\t" +  "\"" +  z.getValue().getNegativeReactantsSMARTS().get(i) + "\",");
					bw0.newLine();
				}	
			bw0.write("\t\t\t" +  "\"" +  z.getValue().getNegativeReactantsSMARTS().get(z.getValue().getNegativeReactantsSMARTS().size()-1) + "\"");

			}
			bw0.newLine();
			bw0.write("\t\t]");
			bw0.newLine();
			bw0.write("\t},");
			bw0.newLine();
		}
		
		bw0.write("}");
		bw0.close();
		bReader.close();
		
		
    }
	
	
	
	
	public ArrayList<String> findAllAncestors(String node, LinkedHashMap<String,ArrayList<String>> cpPairs){
		ArrayList<String> ancestors = new ArrayList<String>();

		if(cpPairs.containsKey(node) && !cpPairs.get(node).isEmpty()){			
			for(String dp :  cpPairs.get(node)){
				ancestors.add(dp);
//				System.out.println("dp: " + dp);
				// cpPairs.containsKey(dp) && 
				if(!cpPairs.get(dp).isEmpty()){
					ancestors.addAll(findAllAncestors(dp, cpPairs));
				}
			}
		}
	
		return  Utilities.removeDuplicateStrings(ancestors);	
	}
	
	public  LinkedHashMap<String,ArrayList<String>> extendChildrenParentAssociations(LinkedHashMap<String,ArrayList<String>> cpAssociations){
		 
		LinkedHashMap<String,ArrayList<String>> extended = new  LinkedHashMap<String,ArrayList<String>>();
		 
		for(Map.Entry<String, ArrayList<String>> a : cpAssociations.entrySet()){
		
			ArrayList<String> ancestors = new ArrayList<String>();		
			for(String dp :  a.getValue()){
				ancestors.add(dp);
				System.out.println("dp: " + dp);
				if(!cpAssociations.get(dp).isEmpty()){
					if(extended.containsKey(dp)){
						ancestors.addAll(extended.get(dp));
					}else{
						ancestors.addAll(findAllAncestors(dp, cpAssociations));
					}					
				}
			}
			extended.put(a.getKey(), Utilities.removeDuplicateStrings(ancestors));	 
		 }
		 
		 return extended;
	}

	
	
	public static void main(String[] args) throws Exception{
//		BioTransformerDBBuilder bt = new BioTransformerDBBuilder();
//		bt.updateMetabolicReactions();
		
//		bt.buildCYPspecificitySDFFileFromDB("data/CYP450_BioTransformations_Apr152017.tsv", "data/CYP450_BioTransformations_Apr152017.sdf");

//		bt.buildBiotransformationList("/Users/yandj/Programming/Projects/Metabolism/BioTransformerDB/CYP450_MReactionAssociations.tsv",
//				"/Users/yandj/Programming/Projects/Metabolism/BioTransformerDB/CYP450_BioTransformations_Apr242017.tsv");
		
//		bt.buildCYP450ReactionLibrary("/Users/yandj/Programming/Projects/Metabolism/BioTransformerDB/CYP450_BioTransformations_May142017.tsv");
		

		
//		IChemObjectBuilder bldr = SilentChemObjectBuilder.getInstance();
//		IteratingSDFReader sdfr = new IteratingSDFReader(new FileReader("/Users/yandj/Programming/Projects/Metabolism/reactantpredictor/data/novelCompoundsFromBioTransformerDB_1A2_predictions.sdf"),
//				bldr);
//		
//		int fp, fn, tn, tp;
//		fp = tp = fn = tn = 0;
//		
//		while (sdfr.hasNext()){
//			IAtomContainer mol = sdfr.next();
//			if(mol.getProperty("Metabolizing Enzymes").toString().trim().length() >0){
//				if( mol.getProperty("1A2").toString().contains("R") ){
//					if(mol.getProperty("Metabolizing Enzymes").toString().contains("1A2")){
//						tp++;
//					}else{
//						fp++;
//					}
//				} else {
//					if ( mol.getProperty("1A2").toString().contains("T") ){
//						if(mol.getProperty("Metabolizing Enzymes").toString().contains("1A2")){
//							fn++;
//						}else{
//							tn++;
//						}
//					}
//				}
//			
//			}
//		}
//		
//		System.out.println("\tR\tT");
//		System.out.println("R\t" + tp + "\t" + fp);
//		System.out.println("T\t" + fn + "\t" + tn);
//
		
//		IAtomContainerSet containers = FileUtils.parseSdf("/Users/yandj/Programming/Projects/Metabolism/ExternalDataSets/Export_phytohub.sdf");
//		BufferedWriter bw0 = new BufferedWriter(new FileWriter("/Users/yandj/Programming/Projects/Metabolism/ExternalDataSets/PytoHub_extended.tsv"));
//		bw0.write("ID" + "\t" + "Name" + "\t" + "SMILES" + "\t" + "InChIKey" + "\t" + "MOLECULAR_WEIGHT" + "\t" + "EXACT_MASS" + "\t" + "FORMULA");
//		bw0.newLine();
//		for(IAtomContainer a : containers.atomContainers()){
//			bw0.write(a.getProperty("DATABASE_ID") + "\t" + a.getProperty(CDKConstants.TITLE) + "\t" +
//					a.getProperty("SMILES") + "\t" + a.getProperty("INCHI_KEY") + "\t" +  a.getProperty("MOLECULAR_WEIGHT") + "\t" +  a.getProperty("EXACT_MASS") 
//					+ "\t" +  a.getProperty("FORMULA") );
//			bw0.newLine();
//		}
//		bw0.close();
		
//		IAtomContainerSet containers = FileUtils.parseSdf("/Users/yandj/Programming/Projects/Metabolism/ExternalDataSets/UNPD_total_229358.sdf");
//
//		System.out.println(containers.getAtomContainerCount());
//		System.out.println(containers.getAtomContainer(0).getProperties());
		
//		IAtomContainerSet containers = FileUtils.parseSdf("/Users/yandj/Programming/Projects/Metabolism/ExternalDataSets/AfroDB_3D.sdf");
//		IAtomContainerSet containers = FileUtils.parseSdf("/Users/yandj/Programming/Projects/Metabolism/ExternalDataSets/DSSTox_Pubchem_20160720.sdf");
//
//		
//		System.out.println(containers.getAtomContainerCount());
//		System.out.println(containers.getAtomContainer(0).getProperties());
		
//		IAtomContainerSet containers = FileUtils.parseSdf("/Users/yandj/Programming/Projects/Metabolism/ExternalDataSets/MeSH_Flavonoids.sdf");
//		BufferedWriter bw0 = new BufferedWriter(new FileWriter("/Users/yandj/Programming/Projects/Metabolism/ExternalDataSets/MeSH_Flavonoids.tsv"));
//		bw0.write("ID" + "\t" + "Name" + "\t" + "InChIKey");
//		bw0.newLine();
//		for(IAtomContainer a : containers.atomContainers()){
//			String name = a.getProperty("PUBCHEM_IUPAC_NAME");
//			if(name == null){
//				name = a.getProperty("PUBCHEM_IUPAC_TRADITIONAL_NAME");
//				if(name == null){
//					name = a.getProperty("PUBCHEM_IUPAC_SYSTEMATIC_NAME");
//				}
////				if(name == null){
////					System.err.println(a.getProperty("PUBCHEM_COMPOUND_CID") + " has no name.");
////				}
//			}
//			
//			bw0.write(a.getProperty("PUBCHEM_COMPOUND_CID") + "\t" + name  + "\t" + a.getProperty("PUBCHEM_IUPAC_INCHIKEY"));
//			bw0.newLine();
//		}
//		bw0.close();
		
		FileUtilities.divideSdfFile("/Users/yandj/Programming/Projects/Metabolism/ExternalDataSets/UNPD_total_229358.sdf",3000);
			
//		FileUtils.buildSdfFromTSV("/Users/yandj/Programming/Projects/Metabolism/STRUCTURES_FOR_PREDICTION/Glycerophospholipids-HMDB30.tsv");
//		FileUtils.divideSdfFile("/Users/yandj/Programming/Projects/Metabolism/STRUCTURES_FOR_PREDICTION/Sphingolipids_by_Hasan/Sphingolipids_Hasan_Sep1.sdf",200);
//		FileUtils.buildSdfFromTSV("/Users/yandj/Programming/Projects/Metabolism/STRUCTURES_FOR_PREDICTION/HMDB_Metabolite_Complement.tsv");
		
		
	}

	
}
