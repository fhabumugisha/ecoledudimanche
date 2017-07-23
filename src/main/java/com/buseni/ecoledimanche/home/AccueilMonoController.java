package com.buseni.ecoledimanche.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccueilMonoController {
	
	@GetMapping("/accueilMono")
	public String accueilMono(Model model){
		
		return "accueilMono";
	}
	
	@GetMapping("/seances")
	public String seances(Model model){		
		return "seances";
	}

	
	@GetMapping("/eleves")
	public String eleves(Model model){		
		return "eleves";
	}
	
	@GetMapping("/moninteurs")
	public String moninteurs(Model model){		
		return "moninteurs";
	}
}
