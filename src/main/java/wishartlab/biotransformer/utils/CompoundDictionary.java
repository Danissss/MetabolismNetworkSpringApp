/**
 * 
 * @author Djoumbou Feunang, Yannick, PhD
 *
 */

package wishartlab.biotransformer.utils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class CompoundDictionary {

	private static CompoundDictionary cpDictionary;
	private static final LinkedHashMap<String, LinkedHashMap<String, String>> allCompounds
															= new LinkedHashMap<String, LinkedHashMap<String, String>>();

	private CompoundDictionary()  throws JsonParseException, JsonMappingException, IOException{
		// TODO Auto-generated constructor stub
//		collectCompounds();
	}

	public static synchronized CompoundDictionary getInstance()  throws JsonParseException, JsonMappingException, IOException{
		
		if(cpDictionary == null){
			cpDictionary = new CompoundDictionary();
		}
		
		return cpDictionary;
	}
	
	
	private void collectCompounds() throws JsonParseException, JsonMappingException, IOException{
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ALLOW_COMMENTS, true);
		mapper.configure(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
		File cpds = new File("database/compounds_.json");
		LinkedHashMap<String,Object> compoundObjets = (LinkedHashMap<String,Object>) mapper.readValue(cpds, Map.class).get("compounds");
		System.out.println(compoundObjets.get("InChIKey=FIWBOGJEPUEWGH-CCGHMXMISA-N"));
		
		for(Map.Entry<String, Object> obj : compoundObjets.entrySet()){
			System.out.println(obj.getKey() + " - " + obj.getValue());
//			LinkedHashMap<String,LinkedHashMap<String,Object>> compounds = obj.
		}
		System.out.println(compoundObjets.size());
	
	}
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException{
		CompoundDictionary c = CompoundDictionary.getInstance();
		
		c.collectCompounds();
		System.out.print("COOL NOW");
	}
}
