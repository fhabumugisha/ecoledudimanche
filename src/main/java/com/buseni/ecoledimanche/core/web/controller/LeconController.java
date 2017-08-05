package com.buseni.ecoledimanche.core.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.buseni.ecoledimanche.breadcrumbs.Navigation;
import com.buseni.ecoledimanche.core.service.LeconService;

@Controller
@Navigation(url="/lecons", name="Liste des leçons", parent={  EspaceMonoController.class})
public class LeconController {
public static final Logger LOGGER = LoggerFactory.getLogger( LeconController.class );

@Autowired
private LeconService leconService;
	
	@GetMapping("/lecons")
	public String lecons(Model model){		
		return "lecons/listeLecons";
	}


}
