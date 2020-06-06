package com.example.MetabolismNetwork.API;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import Cheminformatics.Utilities.structureValidation;
import Xuan.DrugExporter.RunClassification;
import weka.core.Instances;


@RestController
@RequestMapping("/api/")
public class DrugPorter {
	
	private  ArrayList<String> available_transporter = getAvailablePorter();
	
	@PostMapping(path = "/drugporter/")
	@ResponseStatus(code = HttpStatus.OK)
    public Map<String, Object> generateStructure(@RequestParam("structure") String structure, 
    		@RequestParam("transporter") String transporter, @RequestParam("role") String role) {
		
		Map<String, Object> json = new LinkedHashMap<String, Object>();
		
		if(!available_transporter.contains(transporter)) {
			json.put("Error", "Transporter not covered. Available transporter include: MDR1, BCRP, MPR1, MPR2.");
			return json;
		}
		
		if(role != "substrate" && role != "inhibitor") {
			json.put("Error", "Role not covered. Available role include: substrate, inhibitor.");
			return json;
		}
		
		if(structureValidation.isStructureValid(structure) == false) {
			json.put("Error", "SMILES string is not valid.");
			return json;
		}
		
		try {
			RunClassification newclassifier = new RunClassification();
			Instances testinstance = newclassifier.CreateTestingInstance("-s", structure, role);
			String result_role = newclassifier.ClassifyInstance(role, transporter, testinstance);
			json.put(role, result_role);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.put("Error", "Internal Error.");
		}
		
		return json;
    }
	
	private ArrayList<String> getAvailablePorter(){
		ArrayList<String> port = new ArrayList<String>();
		port.add("MDR1");
		port.add("BCRP");
		port.add("MPR1");
		port.add("MPR2");
		return port;
	}
	
	
	
}















