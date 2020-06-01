
/**
 * 
 * @author Djoumbou Feunang, Yannick, PhD
 *
 */

package wishartlab.biotransformer.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONValue;
import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.fingerprint.IBitFingerprint;
import org.openscience.cdk.fingerprint.MACCSFingerprinter;
import org.openscience.cdk.fingerprint.PubchemFingerprinter;
import org.openscience.cdk.inchi.InChIGeneratorFactory;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.io.SDFWriter;
import org.openscience.cdk.io.iterator.IteratingSDFReader;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import wishartlab.biotransformer.biosystems.BioSystem;
import wishartlab.biotransformer.biosystems.BioSystem.BioSystemName;
import wishartlab.biotransformer.fingerprint.ChemStructureFingerprinter;


public class ErdbTask {

	public ErdbTask() {
		// TODO Auto-generated constructor stub
	
	}

	
	public void filter() throws IOException{
		BufferedReader bReader = new BufferedReader (new FileReader("data/CYP_3D_annotated_metabolites_withAllFeats_21012017_new.tsv"));

		ArrayList<String> lipinskiCompounds = new ArrayList<String>();
		ArrayList<String> leadLikeCompounds = new ArrayList<String>();
		ArrayList<String> lipinskiOrleadLikeCompounds = new ArrayList<String>();
		ArrayList<String> filtered = new ArrayList<String>();
		
		String line;
		int counter = 0;
		int reactantNr = 0;
		boolean lipinski, leadLike;
				
		// nHBAcc 15
		// nHBDon 16
		// nB 19
		// nRotB 21
		// MLogP 26
		// TopoPSA 28
		// MW 29
		
		while((line = bReader.readLine()) != null){
			counter++;
			System.out.println(counter);
			
			if(counter > 1 && counter < 501){
				String[] sline = line.split("\t");
				System.out.println(sline[29].getClass());
				if(sline[4] == "R" || sline[5] == "R" || sline[6] == "R" || sline[7] == "R"|| sline[8] == "R"
						|| sline[9] == "R"|| sline[10] == "R" || sline[11] == "R" || sline[12] == "R"){
					filtered.add(line);
					reactantNr++;
				} else{
					lipinski = (Integer.valueOf(sline[29]) < 500 && Integer.valueOf(28) < 5 &&
							Integer.valueOf(16) < 5 && Integer.valueOf(15) < 10 );
					leadLike = (Integer.valueOf(sline[29]) < 300 && Integer.valueOf(28) < 3 &&
							Integer.valueOf(16) < 3 && Integer.valueOf(3) < 10 &&
							Integer.valueOf(21) < 3);
					
					if(lipinski && leadLike){
						lipinskiCompounds.add(line);
						leadLikeCompounds.add(line);
						lipinskiOrleadLikeCompounds.add(line);
						filtered.add(line);
					} 
					else if(lipinski){
						lipinskiCompounds.add(line);
						lipinskiOrleadLikeCompounds.add(line);
						filtered.add(line);
						} 
					else if(leadLike){
						leadLikeCompounds.add(line);
						lipinskiOrleadLikeCompounds.add(line);
						filtered.add(line);
					}
	
				}	
			}			
		}	
		bReader.close();
	}
	
	public void generateReactantsSetFromTSV() throws IOException{
		BufferedReader bReader = new BufferedReader (new FileReader("data/CYP_3D_annotated_metabolites_withAllFeats_24022017_ERDB_v60_.tsv"));
		ArrayList<String> reactantsOrInhibitors = new ArrayList<String>();
		ArrayList<String> nonReactantsToAllCPs = new ArrayList<String>();
		
		ArrayList<Double> massesForRIs = new ArrayList<Double>();
		ArrayList<Double> massesForNonReactantsToAllCPs = new ArrayList<Double>();
		String maxMassRIsSmiles = "";
		
		ArrayList<Double> tpsaForRIs = new ArrayList<Double>();
		ArrayList<Double> tpsaForNonReactantsToAllCPs = new ArrayList<Double>();
		String maxTpsaRIsSmiles = "";
		
		
		ArrayList<Double> asaForRIs = new ArrayList<Double>();
		ArrayList<Double> asaForNonReactantsToAllCPs = new ArrayList<Double>();
		String maxAsaRIsSmiles = "";
		
		ArrayList<Double> mLogForRIs = new ArrayList<Double>();
		ArrayList<Double> mLogPForNonReactantsToAllCPs = new ArrayList<Double>();
		String maxMLogPaRIsSmiles = "";		
		
		String line;
		double mass=0.0;
		double tpsa=0.0;
		double asa=0.0;
		double mlogp = 0.0;
		
		int counter = 0;
		
		while((line = bReader.readLine()) != null){
			counter++;
			
			if(counter>1){
			String[] sline = line.split("\t");

			if(sline[4].contains("N") && sline[5].contains("N") && sline[6].contains("N") && sline[7].contains("N") &&sline[8].contains("N")
					&& sline[9].contains("N") && sline[10].contains("N") && sline[4].contains("N") && sline[12].contains("N")){
				nonReactantsToAllCPs.add(line);
				tpsaForNonReactantsToAllCPs.add(Double.valueOf(sline[28]));
				mLogPForNonReactantsToAllCPs.add(Double.valueOf(sline[26]));
				

				if((!sline[50].contains("NaN")) && (sline[50] != null)){
					asaForNonReactantsToAllCPs.add(Double.valueOf(sline[50]));
				}
				
				if(sline[29] != null && Double.valueOf(sline[29]) > 0.0){
					massesForNonReactantsToAllCPs.add(Double.valueOf(sline[29]));
				} else
					System.out.println(sline[29] + " || " + Double.valueOf(sline[29]));

			} else
			
			if(sline[4].contains("R") ||sline[5].contains("R") || sline[6].contains("R") || sline[7].contains("R")|| sline[8].contains("R")
					|| sline[9].contains("R")|| sline[10].contains("R") || sline[11].contains("R") || sline[12].contains("R") ||
					
					sline[4].contains("I") ||sline[5].contains("I") || sline[6].contains("I") || sline[7].contains("I")|| sline[8].contains("I")
					|| sline[9].contains("I")|| sline[10].contains("I") || sline[11].contains("I") || sline[12].contains("I")){
				
				reactantsOrInhibitors.add(line);
				tpsaForRIs.add(Double.valueOf(sline[28]));
				mLogForRIs.add(Double.valueOf(sline[26]));
				
				if((!sline[50].contains("NaN")) && (sline[50] != null)){
					asaForRIs.add(Double.valueOf(sline[50]));
				}
				
				
				if(sline[29] != null && Double.valueOf(sline[29]) > 0.0){
					massesForRIs.add(Double.valueOf(sline[29]));	
				} else {
					System.out.println(sline[29] + " || " + Double.valueOf(sline[29]));
				}
				
				if(Double.valueOf(sline[26])>mlogp){	
					mlogp = Double.valueOf(sline[26]);
					maxMLogPaRIsSmiles = sline[14] + "\n" + sline[4] +  "\t" +sline[5] + "\t" + sline[6] + "\t" + sline[7] + "\t" + sline[8] +  "\t" +sline[9] + "\t" + sline[10] + "\t" + sline[11]+ "\t" + sline[12];
				}
				
				if(Double.valueOf(sline[29])>mass){				
					mass = Double.valueOf(sline[29]);
					maxMassRIsSmiles = sline[14] + "\n" + sline[4] +  "\t" +sline[5] + "\t" + sline[6] + "\t" + sline[7] + "\t" + sline[8] +  "\t" +sline[9] + "\t" + sline[10] + "\t" + sline[11]+ "\t" + sline[12];
				}
				
				if(Double.valueOf(sline[28])>tpsa){				
					tpsa = Double.valueOf(sline[28]);
					maxTpsaRIsSmiles = sline[14] + "\n" + sline[4] + "\t" +sline[5] + "\t" + sline[6] + "\t" + sline[7] + "\t" + sline[8] +  "\t" +sline[9] + "\t" + sline[10] + "\t" + sline[11]+ "\t" + sline[12];
				}
				
				if((!sline[50].contains("NaN")) && (sline[50] != null) && Double.valueOf(sline[50])>asa){				
					asa = Double.valueOf(sline[50]);
					maxAsaRIsSmiles = sline[14] + "\n" + sline[4] + "\t" + sline[5] + "\t" + sline[6] + "\t" + sline[7] + "\t" + sline[8] +  "\t" +sline[9] + "\t" + sline[10] + "\t" + sline[11]+ "\t" + sline[12];;
				}
				
			}
			
		}
	}
		
		bReader.close();
	}
	
	
	public void generateBioTransformerDB() throws Exception{
		BufferedReader bReader = new BufferedReader (new FileReader("/Users/yandj/Programming/Projects/Metabolism/cyp450_metabolites.tsv"));
		BufferedReader bReader_2 = new BufferedReader (new FileReader("/Users/yandj/Programming/Projects/Metabolism/phase2_metabolites.tsv"));
		BufferedReader bReader_3 = new BufferedReader (new FileReader("/Users/yandj/Programming/Projects/Metabolism/GutMicrobialBiotransformationsAndMetabolites.tsv"));
		BufferedWriter bw0 = new BufferedWriter(new FileWriter("/Users/yandj/Programming/Projects/Metabolism/btdb_metabolites.json"));
		
		InChIGeneratorFactory inchiGenFactory = InChIGeneratorFactory.getInstance();
		LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>> lmao = new LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>>();
		LinkedHashMap<String, String> inchikeyToID = new LinkedHashMap<String, String>();
		LinkedHashMap<String, LinkedHashMap<String, Object>> cpdDict = new LinkedHashMap<String, LinkedHashMap<String, Object>>();
		
		LinkedHashMap<String, LinkedHashMap<String, Object>> lmaof = new LinkedHashMap<String, LinkedHashMap<String, Object>>();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ALLOW_COMMENTS, true);
		mapper.configure(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
		BioSystem bsys = new BioSystem(BioSystemName.HUMAN,mapper);
		BioSystem bsys2 = new BioSystem(BioSystemName.GUTMICRO,mapper);
		
//		System.err.println(bsys.getReactionsHash());
//		System.err.println(bsys.getReactionsHash().get("HYDROXYLATION_OF_ALIPHATIC_SECONDARY_ANTEPENULTIMATE_CARBON_PATTERN1").commonName);
		
		 Pattern drugBankPattern = Pattern.compile("^DB[0-9]|^DBMET[0-9]");
		 Pattern hmdbPattern = Pattern.compile("^^HMDB[0-9]");
		
		int id = 1;
		// GET CYP450 TRANSFORMATIONS
		int btCounter = 0;
		String line	 = null;
		String line2 = null;
		String line3 = null;
		
		while((line = bReader.readLine()) != null && (line = bReader.readLine()).trim() != "" 
				&& (line = bReader.readLine()).trim() != "\n"){			
			String[] sline = line.split("\t");
//			System.out.println(line);
			
			if(sline.length>12 && sline[1] != null && sline[1].trim() != "" && (!sline[1].contains("Structure"))
					&& sline[7] != null && sline[7].length()>0 && sline[8] != null && sline[9] != null && sline[9].contains("CYP")
					&& sline[10] !=null && sline[10].trim().length()>0 && sline[11] != null && sline[11].trim().length()>0
					){
				
				String[] pname = sline[10].split(";");
				String[] pstruct = sline[11].split(Pattern.quote("."));
				System.err.println("sline[0] : " + sline[0]);
				System.err.println("sline[10] : " + sline[10]);
				System.err.println("sline[11] : " + sline[11]);	
				
				if(pname != null && pstruct != null && pstruct.length > 0 && pname.length == pstruct.length){

					System.out.println("GOOD LINE");
					IAtomContainer atc = (IAtomContainer) ChemStructureExplorer.createAtomContainerFromSmiles(sline[1].trim(), true).get("atomContainer");
					atc= AtomContainerManipulator.suppressHydrogens(atc); 
					String inchikey = inchiGenFactory.getInChIGenerator(atc).getInchiKey();
					
					if(!cpdDict.containsKey(inchikey)){
						cpdDict.put(inchikey, new LinkedHashMap<String, Object>());
						cpdDict.get(inchikey).put("Name", sline[0].trim());
						cpdDict.get(inchikey).put("SMILES", ChemStructureExplorer.smiGen.create(atc));
						cpdDict.get(inchikey).put("InChIKey", inchikey);
						cpdDict.get(inchikey).put("BTMDB_ID", "BTM" + String.format("%04d", id));
						cpdDict.get(inchikey).put("PubChem CID","NULL");
						cpdDict.get(inchikey).put("DrugBank ID", "NULL");
						cpdDict.get(inchikey).put("HMDB_ID", "NULL");
						
						LinkedHashMap<String,ArrayList<String>> syn = ChemdbRest.getSynonymsObjectViaInChIKey(inchikey);

						if(syn != null && syn.get("CID") != null){						
							cpdDict.get(inchikey).put("PubChem CID", syn.get("CID").get(0));

							for(String s : syn.get("Synonyms")){
								Matcher m = drugBankPattern.matcher(s);
								Matcher n = hmdbPattern.matcher(s);								
								if(m.find()){
									cpdDict.get(inchikey).put("DrugBank ID", s);
								}
								if(n.find()){
									cpdDict.get(inchikey).put("HMDB_ID", s);
								}					
							}						
						}
						
						id++;
					}
									
					LinkedHashMap<String, Object> substrate = new LinkedHashMap<String, Object>();
					substrate = cpdDict.get(inchikey);
					
					for(String i : sline[7].split(";")){

						LinkedHashMap<String, Object> bt = new LinkedHashMap<String, Object>();
						bt.put("Substrate",substrate);
						bt.put("Enzyme(s)", sline[9].replace(" (minor)", "").replace(" (major)", ""));

						System.out.println("Reaction");
						System.out.println('"' + i.trim() + '"');
						bt.put("Reaction Type", bsys.getReactionsHash().get(i.trim()).commonName);
						bt.put("BioTransformer Reaction ID (BTMRID)", bsys.getReactionsHash().get(i.trim()).reactionsBTMRID);
						bt.put("Biotransformation type", "Human Phase I");
						bt.put("Biosystem", "Human");
								
//						if(pname != null && pstruct != null && pstruct.length > 0 && pname.length == pstruct.length){
							btCounter++;
							
							for(int k = 0; k < pname.length; k++){
								ArrayList<LinkedHashMap<String, Object>> products = new ArrayList<LinkedHashMap<String, Object>>();
								System.err.println("K : " + k);						
								System.err.println("pstruct[k] : " + pstruct[k].trim());
								IAtomContainer a = (IAtomContainer) ChemStructureExplorer.createAtomContainerFromSmiles(pstruct[k].trim(), true).get("atomContainer");
								a= AtomContainerManipulator.suppressHydrogens(a); 
								String ikey = inchiGenFactory.getInChIGenerator(a).getInchiKey();
								
								if(cpdDict.containsKey(ikey)){
									products.add(cpdDict.get(ikey));
								}else{
									cpdDict.put(ikey, new LinkedHashMap<String, Object>());
									cpdDict.get(ikey).put("Name", pname[k].trim());
									cpdDict.get(ikey).put("SMILES", ChemStructureExplorer.smiGen.create(a));
									cpdDict.get(ikey).put("InChIKey", ikey);
									cpdDict.get(ikey).put("BTMDB_ID", "BTM" + String.format("%04d", id));
									cpdDict.get(ikey).put("PubChem CID","NULL");
									cpdDict.get(ikey).put("DrugBank ID", "NULL");
									cpdDict.get(ikey).put("HMDB_ID", "NULL");

									LinkedHashMap<String,ArrayList<String>> synp = ChemdbRest.getSynonymsObjectViaInChIKey(ikey);
									
									if(synp != null && synp.get("CID") != null){						
										cpdDict.get(ikey).put("PubChem CID", synp.get("CID").get(0));

										for(String s : synp.get("Synonyms")){
											Matcher m = drugBankPattern.matcher(s);
											Matcher n = hmdbPattern.matcher(s);								
											if(m.find()){
												cpdDict.get(ikey).put("DrugBank ID",s);
											}
											if(n.find()){
												cpdDict.get(ikey).put("HMDB_ID",s);
											}					
										}						
									}
									
									products.add(cpdDict.get(ikey));
									id++;
								}
								
								bt.put("Products", products);
							}

							
							if(sline.length>=14){
	//							bt.put("References", sline[15].replace("(R1)","").replace("(R2)","").replace("(R3)","").replace("(R4)","").trim().replace(" || ", "\n").replace("||", "\n"));						
								bt.put("References", sline[15]);
	//							bt.put("References", String.join("\n", sline[15].replace("(R1)","").replace("(R2)","").replace("(R3)","").replace("(R4)","").trim().split("||")));
								
							}
							
							lmaof.put("BIOTID" + String.format("%04d", btCounter), bt);
	//						btCounter++;
	//						System.err.println(v);	
							
//						}		
					}
									
				} else{
					System.err.println("Check the number of product names and structures on line\n" + line + "\n"+ String.valueOf(pname.length) + " names vs. " + String.valueOf(pstruct.length) + " structures." );
					break;
				}
			}
		}
		
		while( (line2 = bReader_2.readLine()) != null && (line2 = bReader_2.readLine()).trim() != "" 
				&& (line2 = bReader_2.readLine()).trim().length()>0){
			String[] sline2 = line2.split("\t");
			
			System.err.println(line2);
			
			if(sline2.length>=12 && sline2[1] != null && sline2[2] != null && sline2[9] != null && sline2[6] != null && sline2[7] != null){
				LinkedHashMap<String, Object> bt = new LinkedHashMap<String, Object>();
				LinkedHashMap<String, Object> substrate = new LinkedHashMap<String, Object>();
				btCounter++;
				String drugbankID = "NULL";
				String hmdbID = "NULL";
				
				if(cpdDict.containsKey(sline2[2])){				
					substrate = cpdDict.get(sline2[2]);										
				} else{
					IAtomContainer atc = (IAtomContainer) ChemStructureExplorer.createAtomContainerFromSmiles(sline2[1].trim(), true).get("atomContainer");
					atc= AtomContainerManipulator.suppressHydrogens(atc); 
					String inchikey = inchiGenFactory.getInChIGenerator(atc).getInchiKey();
					
					cpdDict.put(inchikey, new LinkedHashMap<String, Object>());
					cpdDict.get(inchikey).put("Name", sline2[0].trim());
					cpdDict.get(inchikey).put("SMILES", ChemStructureExplorer.smiGen.create(atc));
					cpdDict.get(inchikey).put("InChIKey", inchikey);
					cpdDict.get(inchikey).put("BTMDB_ID", "BTM" + String.format("%04d", id));
					cpdDict.get(inchikey).put("PubChem CID","NULL");
					cpdDict.get(inchikey).put("DrugBank ID","NULL");
					cpdDict.get(inchikey).put("HMDB_ID","NULL");
					
					LinkedHashMap<String,ArrayList<String>> syn = ChemdbRest.getSynonymsObjectViaInChIKey(sline2[2]);

					if(syn != null && syn.get("CID") != null){						
						cpdDict.get(inchikey).put("PubChem CID",syn.get("CID").get(0));
						for(String s : syn.get("Synonyms")){
							Matcher m = drugBankPattern.matcher(s);
							Matcher n = hmdbPattern.matcher(s);								
							if(m.find()){
								cpdDict.get(inchikey).put("DrugBank ID",s);
							}
							if(n.find()){
								cpdDict.get(inchikey).put("HMDB_ID",s);
							}					
						}						
					}
					
					substrate = cpdDict.get(inchikey);
					id++;				
				}
				

				
				bt.put("Substrate", substrate);
				bt.put("Enzyme(s)", sline2[11].replace(" (minor)", "").replace(" (major)", ""));
				bt.put("Reaction Type", sline2[6]);
				bt.put("BioTransformer Reaction ID (BTMRID)", sline2[7]);
				bt.put("Biotransformation type", "Human Phase II");
				bt.put("Biosystem", "Human");				
				
				ArrayList<LinkedHashMap<String, Object>> products = new ArrayList<LinkedHashMap<String, Object>>();
				System.err.println(sline2[9]);
				
				IAtomContainer a = (IAtomContainer) ChemStructureExplorer.createAtomContainerFromSmiles(sline2[9].trim(), true).get("atomContainer");
				a = AtomContainerManipulator.suppressHydrogens(a);
				String ikey = inchiGenFactory.getInChIGenerator(a).getInchiKey();				

				if(cpdDict.containsKey(ikey)){
					products.add(cpdDict.get(ikey));
				}else{
					cpdDict.put(ikey, new LinkedHashMap<String, Object>());
					cpdDict.get(ikey).put("Name",sline2[8].trim());
					cpdDict.get(ikey).put("SMILES", ChemStructureExplorer.smiGen.create(a));
					cpdDict.get(ikey).put("InChIKey", ikey);
					cpdDict.get(ikey).put("BTMDB_ID", "BTM" + String.format("%04d", id));
					cpdDict.get(ikey).put("PubChem CID","NULL");
					cpdDict.get(ikey).put("DrugBank ID","NULL");
					cpdDict.get(ikey).put("HMDB_ID","NULL");					

					LinkedHashMap<String,ArrayList<String>> syn2 = ChemdbRest.getSynonymsObjectViaInChIKey(ikey);
					
					syn2 = ChemdbRest.getSynonymsObjectViaInChIKey(ikey);
					if(syn2 != null && syn2.get("CID") != null){						
						cpdDict.get(ikey).put("PubChem CID", syn2.get("CID").get(0));

						for(String s : syn2.get("Synonyms")){
							Matcher m = drugBankPattern.matcher(s);
							Matcher n = hmdbPattern.matcher(s);								
							if(m.find()){
								cpdDict.get(ikey).put("DrugBank ID",s);
							}
							if(n.find()){
								hmdbID = s;
							}					
						}						
					}
										
					products.add(cpdDict.get(ikey));
					id++;
				}
				
				bt.put("Products", products);

				if(sline2.length>=13){
					bt.put("References", sline2[12]);
//							bt.put("References", String.join("\n", sline[15].replace("(R1)","").replace("(R2)","").replace("(R3)","").replace("(R4)","").trim().split("||")));
					
				}
				
				String v = "BIOTID" + String.format("%04d", btCounter);
				lmaof.put(v, bt);
				
				System.err.println(v);					
				
			}
		}
		
		
		while((line3 = bReader_3.readLine()) != null && (line3 = bReader_3.readLine()).trim() != "" 
				&& (line3 = bReader_3.readLine()).trim() != "\n"){			
			String[] sline3 = line3.split("\t");
//			System.out.println(line3);
			
			if(sline3.length>12 && sline3[1] != null && sline3[1].trim() != "" && (!sline3[1].contains("Structure"))
					&& sline3[7] != null && sline3[7].length()>0 && sline3[8] != null && sline3[9] != null
					&& sline3[10] !=null && sline3[10].trim().length()>0 && sline3[11] != null && sline3[11].trim().length()>0
					){
				
				String[] pname = sline3[10].split(";");
				String[] pstruct = sline3[11].split(Pattern.quote("."));
				System.err.println("sline[0] : " + sline3[0]);
				System.err.println("sline[10] : " + sline3[10]);
				System.err.println("sline[11] : " + sline3[11]);	
				
				if(pname != null && pstruct != null && pstruct.length > 0 && pname.length == pstruct.length){

					System.out.println("GOOD LINE");
					IAtomContainer atc = (IAtomContainer) ChemStructureExplorer.createAtomContainerFromSmiles(sline3[1].trim(), true).get("atomContainer");
					atc= AtomContainerManipulator.suppressHydrogens(atc); 
					String inchikey = inchiGenFactory.getInChIGenerator(atc).getInchiKey();

					
					if(!cpdDict.containsKey(inchikey)){
						cpdDict.put(inchikey, new LinkedHashMap<String, Object>());
						cpdDict.get(inchikey).put("Name", sline3[0].trim());
						cpdDict.get(inchikey).put("SMILES", ChemStructureExplorer.smiGen.create(atc));
						cpdDict.get(inchikey).put("InChIKey", inchikey);
						cpdDict.get(inchikey).put("BTMDB_ID", "BTM" + String.format("%04d", id));
						cpdDict.get(inchikey).put("PubChem CID","NULL");
						cpdDict.get(inchikey).put("DrugBank ID","NULL");
						cpdDict.get(inchikey).put("HMDB_ID","NULL");
						
						LinkedHashMap<String,ArrayList<String>> syn = ChemdbRest.getSynonymsObjectViaInChIKey(inchikey);
						if(syn != null && syn.get("CID") != null){						
							cpdDict.get(inchikey).put("PubChem CID", syn.get("CID").get(0));

							for(String s : syn.get("Synonyms")){
								Matcher m = drugBankPattern.matcher(s);
								Matcher n = hmdbPattern.matcher(s);								
								if(m.find()){
									cpdDict.get(inchikey).put("DrugBank ID",s);
								}
								if(n.find()){
									cpdDict.get(inchikey).put("HMDB_ID",s);
								}					
							}						
						}
						
						id++;
					}
					LinkedHashMap<String, Object> substrate = new LinkedHashMap<String, Object>();
					substrate = cpdDict.get(inchikey);
					
					for(String i : sline3[7].split(";")){

						


						LinkedHashMap<String, Object> bt = new LinkedHashMap<String, Object>();
						bt.put("Substrate",substrate);
						bt.put("Enzyme(s)", sline3[9].replace(" (minor)", "").replace(" (major)", ""));

						System.out.println("Reaction");
						System.out.println('"' + i.trim() + '"');
						bt.put("Reaction Type", bsys2.getReactionsHash().get(i.trim()).commonName);
						bt.put("BioTransformer Reaction ID (BTMRID)", bsys2.getReactionsHash().get(i.trim()).reactionsBTMRID);
						bt.put("Biotransformation type", "Human Gut Microbial");
						bt.put("Biosystem", "Human");
								
//						if(pname != null && pstruct != null && pstruct.length > 0 && pname.length == pstruct.length){
							btCounter++;
							
							for(int k = 0; k < pname.length; k++){
								ArrayList<LinkedHashMap<String, Object>> products = new ArrayList<LinkedHashMap<String, Object>>();
								System.err.println("K : " + k);						
								System.err.println("pstruct[k] : " + pstruct[k].trim());
								IAtomContainer a = (IAtomContainer) ChemStructureExplorer.createAtomContainerFromSmiles(pstruct[k].trim(), true).get("atomContainer");
								a= AtomContainerManipulator.suppressHydrogens(a);
								String ikey = inchiGenFactory.getInChIGenerator(a).getInchiKey();
								
								if(cpdDict.containsKey(ikey)){
									products.add(cpdDict.get(ikey));
								}else{
									cpdDict.put(ikey, new LinkedHashMap<String, Object>());
									cpdDict.get(ikey).put("Name", pname[k].trim());
									cpdDict.get(ikey).put("SMILES", ChemStructureExplorer.smiGen.create(a));
									cpdDict.get(ikey).put("InChIKey", ikey);
									cpdDict.get(ikey).put("BTMDB_ID", "BTM" + String.format("%04d", id));
									cpdDict.get(ikey).put("PubChem CID","NULL");
									cpdDict.get(ikey).put("DrugBank ID","NULL");
									cpdDict.get(ikey).put("HMDB_ID","NULL");

									LinkedHashMap<String,ArrayList<String>> syn = ChemdbRest.getSynonymsObjectViaInChIKey(ikey);
									if(syn != null && syn.get("CID") != null){						
										cpdDict.get(ikey).put("PubChem CID", syn.get("CID").get(0));

										for(String s : syn.get("Synonyms")){
											Matcher m = drugBankPattern.matcher(s);
											Matcher n = hmdbPattern.matcher(s);								
											if(m.find()){
												cpdDict.get(ikey).put("DrugBank ID",s);
											}
											if(n.find()){
												cpdDict.get(ikey).put("HMDB_ID", s);
											}					
										}						
									}
									
									products.add(cpdDict.get(ikey));
									id++;
								}
								
								bt.put("Products", products);
							}
							
							if(sline3.length>=14){
								bt.put("References", sline3[15]);
							}
							
							lmaof.put("BIOTID" + String.format("%04d", btCounter), bt);		
					}
									
				} else{
					System.err.println("Check the number of product names and structures on line\n" + line + "\n"+ String.valueOf(pname.length) + " names vs. " + String.valueOf(pstruct.length) + " structures." );
					break;
				}
			}
		}
		
		String jsonText = JSONValue.toJSONString(lmaof);
		bw0.write(jsonText);
		bw0.close();
		
		bReader.close();

	}
}
