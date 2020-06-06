package com.example.MetabolismNetwork.API;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import Cheminformatics.Utilities.structureValidation;
import Siyang.CypReact.ReactantPred;
import weka.core.Instances;

@RestController
@RequestMapping("/api/")
public class CypReact {
	
	
	
	private  ArrayList<String> available_protein = getAvailableProtein();
	private  String supportFoldPath = String.format("%s/", System.getProperty("user.dir"));
	
	@PostMapping(path = "/cypreact/")
	@ResponseStatus(code = HttpStatus.OK)
    public Map<String, Object> generateStructure(@RequestParam("structure") String structure, 
    		@RequestParam("protein") String protein) {
		
		
		Map<String, Object> json = new LinkedHashMap<String, Object>();
		if(!available_protein.contains(protein)) {
			json.put("Error", "Protein not covered. Available transporter include: CYP1A2, CYP2B6, "
					+ "CYP2A6, CYP2C8, CYP2C9, CYP2C19, CYP2D6, CYP2E1, CYP3A4.");
			return json;
		}
		
		if(structureValidation.isStructureValid(structure) == false) {
			json.put("Error", "SMILES string is not valid.");
			return json;
		}
		
		ReactantPred CypReact = new ReactantPred();
		try {
			Instances testinstance = CypReact.CreateTestInstances(String.format("SMILES=%s", structure));
			IAtomContainerSet inputMolecule = CypReact.CreateInputMolecule(String.format("SMILES=%s", structure));
			
			if (testinstance != null && inputMolecule !=null) {
				ArrayList<HashMap<String,String>> temp_cypreact_result = CypReact.RunClassification(supportFoldPath,testinstance, protein, inputMolecule);
				if(temp_cypreact_result != null && temp_cypreact_result.size() > 0) {
					for(String key : temp_cypreact_result.get(0).keySet()) {
						json.put(key, temp_cypreact_result.get(0).get(key));
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.put("Error", "Internal Error.");
		}
		
		
		
		
		return json;
	}
	
	
	private ArrayList<String> getAvailableProtein(){
		ArrayList<String> port = new ArrayList<String>();
		port.add("CYP1A2");
		port.add("CYP2B6");
		port.add("CYP2A6");
		port.add("CYP2C8");
		port.add("CYP2C9");
		port.add("CYP2C19");
		port.add("CYP2D6");
		port.add("CYP2E1");
		port.add("CYP3A4");
		return port;
	}
}
