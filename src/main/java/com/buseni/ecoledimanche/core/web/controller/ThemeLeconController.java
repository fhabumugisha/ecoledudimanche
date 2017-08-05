package com.buseni.ecoledimanche.core.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.buseni.ecoledimanche.breadcrumbs.Navigation;
import com.buseni.ecoledimanche.core.service.ThemeLeconService;
@Controller
@Navigation(url="/themes", name="Liste des themes", parent={  EspaceMonoController.class})
public class ThemeLeconController {
	
public static final Logger LOGGER = LoggerFactory.getLogger( ThemeLeconController.class );

@Autowired
private ThemeLeconService themeLeconService;
	
	@GetMapping("/themes")
	public String themes(Model model){		
		return "lecons/listeThemes";
	}


}
