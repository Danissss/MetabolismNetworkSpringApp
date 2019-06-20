package com.example.MetabolismNetwork.Controller;


import java.util.HashMap;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.MetabolismNetwork.Model.NmrPredModel;

import Xuan.NMRShiftPrediction.NMRPred;


@Controller
public class NmrPredController {

	private String current_dir = System.getProperty("user.dir");

	
	@GetMapping("/nmrpred")
	public String NmrPred(Model model) {
		
		model.addAttribute("NmrPredModel", new NmrPredModel());
		return "nmrprediction/nmrpredict";
	}
	
	
	
	/**
	 * 1.Get molecule format (file,smiles,molblock)
	 * 2.return the shift value 
	 * 3. use spinich to draw the graph (probably save the graph so that no need for using other library)
	 * 4. render the image
	 * @return
	 */
	@PostMapping("/nmrpred/result")
	public String NmrPredQuery() {
		
		
		
		
		return null;
	}
	
	
	
	
	/**
	 * 
	 * @param mol
	 * @param model_type
	 * @param solvent
	 * @return the hashmap with information of atom position and predicted shift
	 */
	
	public HashMap<Integer, Double> GetShiftFromMol(IAtomContainer mol, String model_type,String solvent) {
		
		NMRPred gps  = new NMRPred();
		HashMap<Integer, Double> result = gps.GetPredictedShift(mol,solvent,model_type);
		return result;
		
		
	}
}
