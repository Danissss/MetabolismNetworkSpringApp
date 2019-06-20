package Xuan.NMRShiftPrediction;



import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.aromaticity.CDKHueckelAromaticityDetector;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.io.iterator.IteratingSDFReader;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmiFlavor;
import org.openscience.cdk.smiles.SmilesGenerator;



/**
 * Construct Dataset for 13C chemical shift
 * @author xuan
 *
 */
public class BuildHoseCodeLib {
	
	public static SmilesGenerator smigen = new SmilesGenerator(SmiFlavor.Isomeric);
//	public static String DbName = "HoseDB.db";
	GenerateHoseCode GHC = new GenerateHoseCode();
	HoseDB hosedb = new HoseDB();
	
	/**
	 * Construct hose code based on solvent
	 * @param SdfFile
	 * @return
	 * @throws Exception 
	 */
	public ArrayList<String[]> BuildAsList(String SdfFile) throws Exception{
		IChemObjectBuilder bldr = SilentChemObjectBuilder.getInstance();
		IteratingSDFReader sdfr = new IteratingSDFReader(new FileReader(SdfFile),bldr);
		
		
		
		Connection conn = hosedb.ConnectToDB();
	    Statement stat = conn.createStatement();
	    stat.executeUpdate("create table if not exists Hose13CTable  (Smiles text, HoseCode text, Shift text, Sphere text, Solvent text);");
	    stat.executeUpdate("create table if not exists Hose1HTable  (Smiles text, HoseCode text, Shift text, Sphere text, Solvent text);");
	    
	    // sqlite> create index sphere_index on Hose13CTable(Sphere);
	    // sqlite> create index solvent_index  on Hose13CTable(Solvent);
	    // sqlite> create index hose_code_index on Hose13CTable(HoseCode);
	    // sqlite> create index sphere_index_1H on Hose1HTable(Sphere);
	    // sqlite> create index solvent_index_1H  on Hose1HTable(Solvent);
	    // sqlite> create index hose_code_index_1H on Hose1HTable(HoseCode);
	    
		IAtomContainerSet MOLS = DefaultChemObjectBuilder.getInstance().newInstance(IAtomContainerSet.class);
		while (sdfr.hasNext()) {
			try {
			IAtomContainer mole = sdfr.next();
			String smiles = smigen.create(mole);
			boolean aromatic = CDKHueckelAromaticityDetector.detectAromaticity(mole);
			Map<Object,Object> object = mole.getProperties();
			String solvent = (String) object.get("Solvent");
			HashMap<Integer,String> solventMap = new HashMap<Integer,String>();
			// add
			try {
				solventMap = BuildSolventMap(solvent);
			}
			catch (Exception e) {
				System.out.println(solvent);
			}
			
			
			
			
			for (Object key : object.keySet()) {
				String keyValue = (String) key;
				if(keyValue.contains("13C") || keyValue.contains("1H")) {			
					String spectrum =  (String) object.get(key);
					String[] spectrum_list = spectrum.split("\\|");
					String keys = keyValue.split(" ")[2];
					String matchingsolvent = solventMap.get(Integer.valueOf(keys));
					
					if(keyValue.contains("13C")) {
						
						if (matchingsolvent.contains("Unreported") || matchingsolvent.contains("Unknown")) {
							continue;
						}else {
							AddNewQuery(spectrum_list,mole,matchingsolvent,conn,"Hose13CTable");
						}
					}
					else if(keyValue.contains("1H")) {
						if (matchingsolvent.contains("Unreported") || matchingsolvent.contains("Unknown")) {
							continue;
						}else {
							
							AddNewQuery(spectrum_list,mole,matchingsolvent,conn,"Hose1HTable");
						}
					}
					
					
					
					
				}
				}
			}
			catch (Exception e) {
				IAtomContainer mole = sdfr.next();
				System.out.println(mole.getTitle());
			}
			
		    }
			
		
		conn.commit();
	    conn.close();
		
		return null;
	}
	
	
	
	
	/**
	 * 
	 * @param solvent
	 * @return HashMap<Integer,String)
	 */
	public static HashMap<Integer,String> BuildSolventMap(String solvent){
		HashMap<Integer,String> solvent_map = new HashMap<Integer,String>();
		
		// determine number of ":"
		int num_of_semi = 0;
		ArrayList<Integer> colon_position = new ArrayList<Integer>();
		int string_length = solvent.length();
	    for (int i = 0; i < solvent.length(); i++) {
	        if (solvent.charAt(i) == ':') {
	            num_of_semi++;
	            colon_position.add(i);
	        }
	    }
	    
	    if(num_of_semi == 1) {
	    		// case 1: only one solvent
	    		if(solvent.contains("Unreported") || solvent.contains("Unknown")) {
	    			// no solvent reported, pass this 
	    			return null;
	    		}
	    		else {
	    			// split by : 
	    			String[] tmp = solvent.split(":");
	    			String newBuffer = tmp[1].substring(0, tmp[1].length()-1);
		    		solvent_map.put(Integer.valueOf(tmp[0]),newBuffer);
		    		return solvent_map;
	    		}
	    		
	    }else {
	    		// more than one solvent reported
	    		// mark the ":" and manipulate the char array (string).
	    		// for 1 and 6 append all char between them.
		    for(int i = 0; i < colon_position.size()-1; i++) {
		    		String buffer = new String();
		    		
		    		int first = colon_position.get(i);
		    		int second = colon_position.get(i+1);
		    		char index = solvent.charAt(first-1);
		    		
		    		while (first < second-2) { // this ignore the indexing and white space
		    			buffer = buffer + solvent.charAt(first);
		    			first++;
		    		}
		    		int y = index - '0';
		    		String newBuffer = buffer.replace(":", "");
		    		solvent_map.put(y,newBuffer);
		    		
		    }
		    
		    // add last index
		    int last_colon = colon_position.get(colon_position.size()-1);
		    char index = solvent.charAt(last_colon-1);
		    String buffer = new String();
		    while (last_colon < string_length-1) { // this ignore the indexing and white space
    				buffer = buffer + solvent.charAt(last_colon);
    				last_colon++;
    			}
		    int y = index - '0';
		    String newBuffer = buffer.replace(":", "");
		    solvent_map.put(y, newBuffer);
		    // parse the solvent_map HashMap
		    return solvent_map;
		    
	    		
	    }
	}
	
	
	/**
	 * Add query helper function
	 * @param spectrum_list
	 * @param mole
	 * @param solvent
	 * @param conn
	 * @throws Exception
	 */
	public void AddNewQuery(String[] spectrum_list, IAtomContainer mole, String solvent, Connection conn, String TableName) throws Exception {
		// for each spectrum
		String smiles = smigen.create(mole);
		boolean aromatic = CDKHueckelAromaticityDetector.detectAromaticity(mole);
		
		for(int i = 0; i < spectrum_list.length; i++) {
			
			//create 6 different sphere
			for(int sphere = 3; sphere<=6; sphere++) {
				String[] single_spectrum = spectrum_list[i].split(";");
				String shift = single_spectrum[0];
				int atom_index = Integer.valueOf(single_spectrum[2]);
				//extract the feature here 
				String hoseCode = GHC.GetHoseCodesForMolecule(mole, mole.getAtom(atom_index), aromatic,sphere);
				// put into the DB
				
				// schema: hose code, shift, sphere
				hosedb.AddQuery(new String[] {smiles,hoseCode,shift,Integer.toString(sphere),solvent}, conn,TableName);
				
			}
			
		}
	}
}
