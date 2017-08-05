package com.buseni.ecoledimanche.core.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.buseni.ecoledimanche.breadcrumbs.Navigation;
import com.buseni.ecoledimanche.core.service.EleveService;

@Controller
@Navigation(url="/eleves", name="Liste des élèves", parent={  EspaceMonoController.class})
public class EleveController {
	public static final Logger LOGGER = LoggerFactory.getLogger( EleveController.class );
	
	@Autowired
	private EleveService eleveService;
	
	@GetMapping("/eleves")
	public String eleves(Model model){		
		return "eleves/listeEleves";
	}

}
