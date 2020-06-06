package com.example.MetabolismNetwork.API;

import java.util.ArrayList;
import java.util.HashMap;
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

import Cheminformatics.Utilities.structureValidation;
import Xuan.NMRShiftPrediction.NMRPred;

@RestController
@RequestMapping("/api/")
public class NMRPredAPI {
	
	
	private  ArrayList<String> available_solvent = getAvailableSolvent();
	
	@PostMapping(path = "/nmrpred/")
	@ResponseStatus(code = HttpStatus.OK)
    public Map<String, Object> generateStructure(@RequestParam("structure") String structure, 
    		@RequestParam("solvent") String solvent, @RequestParam("proton") String proton) {
		
		Map<String, Object> json = new LinkedHashMap<String, Object>();
		if(proton != "H" || proton != "C") {
			json.put("Error", "Proton not covered. Available proton include: H, C.");
			return json;
		}
		
		if(!available_solvent.contains(solvent)) {
			json.put("Error", "Solvent not covered. Available solvent include: Acetone, Chloroform, Dimethylsulphoxide"
					+ "Methanol, Water.");
			return json;
		}
		
		if(structureValidation.isStructureValid(structure) == false) {
			json.put("Error", "SMILES string is not valid.");
			return json;
		}
		
		
		IChemObjectBuilder bldr   = SilentChemObjectBuilder.getInstance();
		SmilesParser smipar = new SmilesParser(bldr);
		NMRPred nmpred = new NMRPred();
	
		try {
			IAtomContainer mole = smipar.parseSmiles(structure);
			HashMap<Integer, Double> shifts = nmpred.GetPredictedShift(mole, solvent, proton);
			for(Integer key : shifts.keySet()) {
				json.put(String.valueOf(key), String.valueOf(shifts.get(key)));
			}
		} catch (InvalidSmilesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.put("Error", "Internal Error.");
		}
		
		return json;
		
	}
	
	private ArrayList<String> getAvailableSolvent(){
		ArrayList<String> solvent = new ArrayList<String>();
		solvent.add("Acetone");
		solvent.add("Chloroform");
		solvent.add("Dimethylsulphoxide");
		solvent.add("Methanol");
		solvent.add("Water");
		return solvent;
	}

}
