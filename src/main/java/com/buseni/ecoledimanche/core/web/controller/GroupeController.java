package com.buseni.ecoledimanche.core.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.buseni.ecoledimanche.breadcrumbs.Navigation;
import com.buseni.ecoledimanche.core.service.GroupeService;

@Controller
@Navigation(url="/groupes", name="Liste des groupes", parent={  EspaceMonoController.class})
public class GroupeController {
	
public static final Logger LOGGER = LoggerFactory.getLogger( GroupeController.class );

@Autowired
private GroupeService groupeService;
	
	@GetMapping("/groupes")
	public String groupes(Model model){		
		return "groupes/listeGroupes";
	}

}
