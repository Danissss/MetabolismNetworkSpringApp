package com.example.MetabolismNetwork.API;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import wishartlab.cfmid_plus.fragmentation.Fragmenter;
import wishartlab.cfmid_plus.fragmentation.StructureExplorer;


@RestController
@RequestMapping("/api/")
public class CFMID3 {
	
	private SmilesParser sParser = new SmilesParser(SilentChemObjectBuilder.getInstance());
	
	@PostMapping(path = "/cfmid3")
	@ResponseStatus(code = HttpStatus.OK)
	public Map<String, Object> run(@RequestParam("structure") String structure, 
    		@RequestParam("adduct") String adduct) {
		
		
		Map<String, Object> json = new LinkedHashMap<String, Object>();
		
		IAtomContainer molecule;
		try {
			molecule = sParser.parseSmiles(structure.replace("[O-]", "O"));
			LinkedHashMap<Integer, LinkedHashMap<String, ArrayList<String>>> result = runCFMID(molecule, adduct);
			System.out.println(result);
//			for(Integer key : result.keySet()) {
//				System.out.println(result.get(key));
//				for(String keys : result.get(key).keySet()) {
//					System.out.println(result.get(key).get(keys));
//				}
//				
//			}
		} catch (InvalidSmilesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return json;
	}

	public LinkedHashMap<Integer, LinkedHashMap<String, ArrayList<String>>> runCFMID(IAtomContainer molecule, String adduct ) throws Exception {
		return null;
	}
}
