package com.buseni.ecoledimanche.core.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/espaceMono")
public class EspaceMonoController {
	
	@GetMapping({"/", ""})
	public String espaceMono(Model model){		
		return "espaceMono";
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
