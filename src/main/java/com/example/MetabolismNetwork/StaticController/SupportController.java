package com.example.MetabolismNetwork.StaticController;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * This Controller is for rendering pages from support drop down 
 * @author xuan
 *
 */
@Controller
public class SupportController {
	
	// render html page has to match the name of index
//	@RequestMapping("/tutorial")
//	public String tutorial() {
//		return "/index";
//	}
	
	
//	@RequestMapping("/FAQ")
//	public String FAQ() {
//		return "/index";
//	}
	
	@RequestMapping("/downloads")
	public String downloads() {
		return "staticpage/download";
	}


	@RequestMapping("/statistics")
	public String statistics() {
		return "staticpage/statistics";
	}
	
}
