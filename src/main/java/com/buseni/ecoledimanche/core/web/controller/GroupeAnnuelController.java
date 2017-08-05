package com.buseni.ecoledimanche.core.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.buseni.ecoledimanche.breadcrumbs.Navigation;
import com.buseni.ecoledimanche.core.service.GroupeAnnuelService;

@Controller
@Navigation(url="/groupes-annuel", name="Liste des groupes par année scolaire", parent={  EspaceMonoController.class})
public class GroupeAnnuelController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger( GroupeAnnuelController.class );
	
	
	@Autowired
	private GroupeAnnuelService groupeAnnuelservice;
	
	@GetMapping("/groupes-annuel")
	public String groupes(Model model){		
		return "groupes/listeGroupesAnnuel";
	}

}
