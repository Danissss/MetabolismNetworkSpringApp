package com.example.MetabolismNetwork.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ChemicalPropertyController {
	
	private String current_dir = System.getProperty("user.dir");
	
	
	@GetMapping("//properties")
	public String cypreactQuery(Model model) {
		return "chemical_properties/ChemicalProperties";
	}
}
