package com.example.MetabolismNetwork.API;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;

import wishartlab.cfmid_plus.fragmentation.Fragmenter;

public class CFMID3 {
	
	public static void runCFMID() {

		
		IChemObjectBuilder bldr = SilentChemObjectBuilder.getInstance();
		SmilesParser sParser = new SmilesParser(SilentChemObjectBuilder.getInstance());
		String outputName = String.format("%s/test.txt", "result" );
		String adductType 	= "[M+H]+;M+;[M+H4]+;[M+Na]+;[M+K]+;[M+Li]+";
		ArrayList<String> adduct_list = new ArrayList<String>(Arrays.asList(adductType.split(";")));
		String smiles = "CNC[C@H](O)C1=CC=C(O)C(O)=C1";
		Fragmenter fr = new Fragmenter();

    
		try {

			IAtomContainer molecule = sParser.parseSmiles(smiles.replace("[O-]", "O"));
//			int status = fr.saveSingleCfmidLikeMSPeakList(molecule, bldr, outputName, adduct_list);

		} catch (InvalidSmilesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public LinkedHashMap<Integer, LinkedHashMap<String, ArrayList<String>>> runCFMID(IAtomContainer mole, String adduct ) {
//		Map<String, Object> result = new HashMap<String, Object>();
		Fragmenter fr = new Fragmenter();
		LinkedHashMap<Integer, LinkedHashMap<String, ArrayList<String>>> result = null;
		try {
			result = fr.generateCfmidLikeMSPeakList(mole, adduct);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}
}
