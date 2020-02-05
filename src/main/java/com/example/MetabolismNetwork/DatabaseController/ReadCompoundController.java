package com.example.MetabolismNetwork.DatabaseController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.MetabolismNetwork.Model.Compound;
import com.example.MetabolismNetwork.Repository.CompoundRepository;

@Controller
public class ReadCompoundController {
	
	
	@Autowired
	private final CompoundRepository compoundRepository;

	
	public ReadCompoundController(CompoundRepository compoundRepository) {
        this.compoundRepository = compoundRepository;
    }
    
    @GetMapping("/compounds/index")
    public ModelAndView Compounds() {
    	
    	List<Compound> compounds = compoundRepository.findAll();
    	System.out.println(compounds.size());
    	for(int i = 0; i < compounds.size(); i++) {
    		System.out.println(compounds.get(i).getSmiles());
    	}
    	// successfully print CN1C=NC(C[C@H](N)C(O)=O)=C1
    	
        ModelAndView mav = new ModelAndView();
        mav.addObject("compounds", compounds);
        mav.setViewName("compoundDatabase/compoundIndex");
        
        
        
        return mav;
    }
    
    
}
