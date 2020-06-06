package com.example.MetabolismNetwork.API;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openscience.cdk.exception.CDKException;
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

import com.example.MetabolismNetwork.Helper.SiteOfMetabolismHelper;

import Cheminformatics.Utilities.structureValidation;
import Xuan.SiteOfMetabolism.SomPrediction;

@RestController
@RequestMapping("/api/")
public class SOMPred {
	
	
	private ArrayList<String> available_protein = getAvailableProtein();
	private String current_dir = System.getProperty("user.dir");
	
	
	@PostMapping(path = "/sompred/")
	@ResponseStatus(code = HttpStatus.OK)
    public Map<String, Object> generateStructure(@RequestParam("structure") String structure, 
    		@RequestParam("protein") String protein) throws InvalidSmilesException {
		
		
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
		
		
		SiteOfMetabolismHelper helper = new SiteOfMetabolismHelper();
		IChemObjectBuilder bldr   = SilentChemObjectBuilder.getInstance();
		SmilesParser smipar = new SmilesParser(bldr);
		IAtomContainer mol = smipar.parseSmiles(structure);
		
		try {

			String new_file_path = helper.Get3DConfirmation("-s", structure);
			if(new_file_path != null) {
				System.out.println("RDK confirmation");
				File sdfFile = new File(String.format("%s/%s", current_dir, new_file_path));					
				mol = SomPrediction.read_SDF_file(String.format("%s/%s", current_dir, new_file_path));
				if(sdfFile.exists()) {
					sdfFile.delete();
				}
			}else {
				System.out.println("CDK confirmation");
				mol = helper.Get3DConfirmationCDK(mol);
				if(mol == null) {
					json.put("Error", "Failed to generate 3D structure for molecule");
					return json;
				}
			}
		}
		catch (Exception e){
			json.put("Error", "Failed to generate 3D structure for molecule");
			return json;
		}
		
		
		
		try {
			HashMap<Integer,String> SoMresult = helper.RunSomPrediction(mol, protein);
			for(Integer key : SoMresult.keySet()) {
				json.put(String.valueOf(key), SoMresult.get(key));
			}
			
		} catch (CDKException | IOException | CloneNotSupportedException e) {
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
