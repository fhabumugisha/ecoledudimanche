package com.buseni.ecoledimanche.core.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buseni.ecoledimanche.core.domain.ThemeLecon;
import com.buseni.ecoledimanche.core.service.ThemeLeconService;

@RestController
public class ThemeRestController {
	
	@Autowired
	private ThemeLeconService themeLeconService;
	@GetMapping("/api/themes")
	public List<ThemeLecon>  getThemes(Model model,  Pageable page){

		Page<ThemeLecon> pageThemes = themeLeconService.findAll(page);
		return pageThemes.getContent();
		
		
	}

}
