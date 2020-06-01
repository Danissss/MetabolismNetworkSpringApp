/**
 * This class implements the class of biosystems. They can represent either individual
 * species/organisms or a collection thereof
 * 
 * @author Djoumbou Feunang, Yannick, PhD
 *
 */
package wishartlab.biotransformer.biosystems;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

import com.google.common.io.Files;

import ambit2.smarts.SMIRKSManager;
import wishartlab.biotransformer.biomolecule.Enzyme;
import wishartlab.biotransformer.biomolecule.Enzyme.EnzymeName;
import wishartlab.biotransformer.transformation.MReactionsFilter;
import wishartlab.biotransformer.transformation.MetabolicReaction;
import wishartlab.biotransformer.transformation.MRPatterns.ReactionName;
import wishartlab.biotransformer.transformation.MetabolicPathway.MPathwayName;

/**
 * @author Yannick Djoumbou Feunang
 *
 */
public class BioSystem {

	/**
	 * 
	 */
	protected LinkedHashMap<String, MetabolicReaction> reactionsHash = new LinkedHashMap<String, MetabolicReaction> ();
	protected LinkedHashMap<String, Double>	reactionsOcurrenceRatios = new LinkedHashMap<String, Double>();
	protected ArrayList<Enzyme>	enzymes = new  ArrayList<Enzyme>();
	protected LinkedHashMap<EnzymeName, Enzyme>	enzymesHash = new  LinkedHashMap<EnzymeName, Enzyme>();
	
	// Update this to include references too
	protected LinkedHashMap<MPathwayName, ArrayList<Enzyme>> metPathwaysHash = new LinkedHashMap<MPathwayName,ArrayList<Enzyme>>();
	public MReactionsFilter mrFilter;
	protected SMIRKSManager 		smrkMan	= new SMIRKSManager(SilentChemObjectBuilder.getInstance());	
	public BioSystemName name;


	public BioSystem(BioSystemName bsName, ObjectMapper mapper) throws JsonParseException, JsonMappingException, IOException {
		this.name 			= bsName;
		this.mrFilter		= new MReactionsFilter(bsName);
		setEnzymesList(mapper);
	}
	
	
	public BioSystem(BioSystemName bsName, ArrayList<Enzyme> enzymes){
		this.name 			= bsName;
		this.enzymes		= enzymes;
	}
	
	
	public enum BioSystemName {
		HUMAN, ENVMICRO, GUTMICRO
	}

	@SuppressWarnings("unchecked")
	private void setEnzymesList(ObjectMapper mapper) throws JsonParseException, JsonMappingException, IOException {
			
		String current_dir = System.getProperty("user.dir");
		String enzymeJson = String.format("%s/src/main/resources/database/%s", current_dir,"enzymes.json");
		File file = new File(enzymeJson);
		String enzymes = FileUtils.readFileToString(file);
		
		
		
		InputStream biosystemEnzymes = BioSystem.class.getResourceAsStream("/biosystemEnzymes.json");
		InputStream enzymeReactions = BioSystem.class.getResourceAsStream("/enzymeReactions.json");
		InputStream metabolicReactions = BioSystem.class.getResourceAsStream("/metabolicReactions.json");
		InputStream bioSystemsReactionORatios = BioSystem.class.getResourceAsStream("/biosystemsReactionORatios.json");
		InputStream pathways = BioSystem.class.getResourceAsStream("/pathways.json");
		LinkedHashMap<String,Object> allEnzymes = (LinkedHashMap<String,Object>) mapper.readValue(enzymes, Map.class).get("enzymes");
		LinkedHashMap<String,Object> allRe = (LinkedHashMap<String, Object>) mapper.readValue(metabolicReactions, Map.class).get("reactions");

		Map<String,Object> allEnzymesByBiosystem = mapper.readValue(biosystemEnzymes, Map.class);
		ArrayList<String> bioSysEnzymeList = (ArrayList<String>) ((LinkedHashMap<String, ArrayList<String>>) 
				allEnzymesByBiosystem.get("enzymeLists")).get(this.name.toString());
		
		Map<String,Object> allEnzToReactions = mapper.readValue(enzymeReactions, Map.class);
		LinkedHashMap<String, ArrayList<String>> enzymeReactionList = (LinkedHashMap<String, ArrayList<String>>) 
				allEnzToReactions.get("eReactionLists");

		
		this.reactionsOcurrenceRatios = (LinkedHashMap<String, Double>) ((LinkedHashMap<String, Object>) 
				mapper.readValue(bioSystemsReactionORatios, Map.class).get("reactionsORatios")).get(this.name.toString());
		
		/*
		 * create a unique list of reactions and create them.
		 * This helps because a reaction can be catalyzed by many 
		 * enzymes, and we do not one to create the same object many times.
		 */				
		/*
		 * Now for each enzyme associated with the biosystem, built the reactions arraylist and then create the enzyme.
		 */		
		for( String e : bioSysEnzymeList ){			
			ArrayList<MetabolicReaction> enzymeSpecificReactionObjects = new ArrayList<MetabolicReaction>();
			for(String s : enzymeReactionList.get(e)){
				if(this.reactionsHash.containsKey(s)){
					enzymeSpecificReactionObjects.add(this.reactionsHash.get(s));
				} 				
				else {						
					LinkedHashMap<String,Object> mrObj = (LinkedHashMap<String,Object>) allRe.get(s);
					String commonName = (String)mrObj.get("commonName");
					if(commonName == null || commonName.contentEquals("")){
						commonName = s;
					}
					String reactionBTMRID = (String)mrObj.get("btmrID");
					MetabolicReaction r = new MetabolicReaction(ReactionName.valueOf(s), commonName, reactionBTMRID, (String)mrObj.get("smirks"), 
							(ArrayList<String>)mrObj.get("smarts"), (ArrayList<String>)mrObj.get("negativeSmarts"), this.smrkMan);
					
					this.reactionsHash.put(s,r);
					enzymeSpecificReactionObjects.add(r);
				}
			}
			
			 LinkedHashMap<String, Object> enz = (LinkedHashMap<String, Object>) allEnzymes.get(e);
			 LinkedHashMap<String, Object> biosystems = (LinkedHashMap<String, Object>) enz.get("biosystems");
			 String description = (String) enz.get("description");
			 
			 
			 ArrayList<String> uniprot_ids = null;
			 ArrayList<String> cellularLocations = null;
			 if(biosystems.containsKey(this.name.toString())){
				 uniprot_ids = (ArrayList<String>)  ((LinkedHashMap<String, Object>) 
						 biosystems.get(this.name.toString())).get("uniprot_ids");
				 cellularLocations = (ArrayList<String>)  ((LinkedHashMap<String, Object>) 
						 biosystems.get(this.name.toString())).get("cellular_locations");
				 		 
			 }

			 String acceptedName =  (String) enz.get("acceptedName");
			 if(acceptedName == null || acceptedName.contentEquals("")){
				 acceptedName =  (String)e;
			 }
			 
			Enzyme enzy = new Enzyme(e, description, uniprot_ids, cellularLocations, enzymeSpecificReactionObjects, acceptedName); 
			this.enzymes.add(enzy);
			this.enzymesHash.put(EnzymeName.valueOf(e), enzy);
		}

		
		LinkedHashMap<String,Object> allPathways = (LinkedHashMap<String, Object>) mapper.readValue(pathways, Map.class).get("metabolicPathways");
		
		for(String pName : allPathways.keySet()) {
			LinkedHashMap<String,Object> lm = (LinkedHashMap<String,Object>) allPathways.get(pName);
			
			if(lm.containsKey(this.name.toString())){
				LinkedHashMap<String,Object> bPaths = (LinkedHashMap<String,Object>) lm.get(this.name.toString());
				ArrayList<String> enz = (ArrayList<String>) bPaths.get("enzymes");
				
				this.metPathwaysHash.put(MPathwayName.valueOf(pName), new ArrayList<Enzyme>());
				
				for(String e_n : enz) {
					this.metPathwaysHash.get(MPathwayName.valueOf(pName)).add(enzymesHash.get(EnzymeName.valueOf(e_n)));
				}

			}

		}
		
	}

	public ArrayList<Enzyme> getEnzymesList(){
		return this.enzymes;
	}

	
	public SMIRKSManager getSmirksManager(){
		return this.smrkMan;
	}
	
	public  LinkedHashMap<String, MetabolicReaction> getReactionsHash(){
		return this.reactionsHash;
	}
	
	public LinkedHashMap<String, Double> getReactionsORatios(){
		return this.reactionsOcurrenceRatios;
	}


	public  LinkedHashMap<EnzymeName, Enzyme> getEnzymeHash(){
		return this.enzymesHash;
	}
	
	public  LinkedHashMap<MPathwayName, ArrayList<Enzyme>> getMetPathwaysHash(){
		return this.metPathwaysHash;
	}
	

}
