package com.example.MetabolismNetwork.StaticController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticPageController {
	
	
	
	// render html page has to match the name of index
	@RequestMapping("/")
	public String LandingPage() {
		return "redirect:/transporter"; 
	}
	
//	@RequestMapping("/index")
//	public String index() {
//		return "/index";		
//	}
	
	
	@RequestMapping("/about")
	public String AboutUs() {
		return "staticpage/aboutus";
	}
	
	@RequestMapping("/contactus")
	public String ContactUs() {
		return "staticpage/contactus";
	}
	
	
	
	
//	// not the best way to render static page by redirect it 
//	@RequestMapping("/one")
//    public String one() {
//        return "redirect:/samplestatic.html";
//    }
	
}
