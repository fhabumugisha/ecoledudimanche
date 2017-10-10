package com.buseni.ecoledimanche.core.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.buseni.ecoledimanche.account.domain.UserAccount;
import com.buseni.ecoledimanche.account.service.UserAccountService;
import com.buseni.ecoledimanche.core.domain.Eleve;
import com.buseni.ecoledimanche.core.domain.Groupe;
import com.buseni.ecoledimanche.core.domain.Lecon;
import com.buseni.ecoledimanche.core.domain.ThemeLecon;
import com.buseni.ecoledimanche.core.service.EleveService;
import com.buseni.ecoledimanche.core.service.GroupeService;
import com.buseni.ecoledimanche.core.service.LeconService;
import com.buseni.ecoledimanche.core.service.ThemeLeconService;
import com.buseni.ecoledimanche.exception.BusinessException;

@RestController
@RequestMapping("/api/themes")
public class ApiRestController {
	
	@Autowired
	private ThemeLeconService themeLeconService;
	
	
	@Autowired
	private UserAccountService  userAccountService;
	
	@Autowired
	private EleveService eleveService;
	
	
	@Autowired
	private GroupeService groupeService;
	

@Autowired
private LeconService leconService;
	
	@GetMapping("/all")
	public List<ThemeLecon>  getThemes(Model model,  Pageable page){

		Page<ThemeLecon> pageThemes = themeLeconService.findAll(page);
		return pageThemes.getContent();
		
		
	}
	

	@GetMapping(value = "/getTheme/{themeId}")
	public ThemeLecon getTheme(@PathVariable("themeId") Integer themeId) {
		return themeLeconService.findById(themeId);
	}
 
	@PostMapping(value = "/createTheme")
	public ThemeLecon createTheme(@RequestBody ThemeLecon theme) {
		ThemeLecon newTheme = null;
		try {
			newTheme=  themeLeconService.addOrUpdate(theme);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newTheme;
	}
 
	@PostMapping(value = "/updateTheme")
	public ThemeLecon updateTheme(@RequestBody ThemeLecon theme) {
		ThemeLecon newTheme = null;
		try {
			newTheme =  themeLeconService.update(theme);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newTheme;
	}
	
	@GetMapping(value = "/deleteTheme/{themeId}")
	public void deleteTheme(@PathVariable("themeId") Integer themeId) {
		themeLeconService.delete(themeId);
	}
	
	@GetMapping("/api/groupes")
	public List<Groupe>  getGroupes(Model model,  Pageable page){

		Page<Groupe> pageThemes = groupeService.findAll(page);
		return pageThemes.getContent();
		
		
	}
	
	
	@GetMapping("/api/moniteurs")
	public List<UserAccount>  getMoniteurs(Model model,  Pageable page){

		Page<UserAccount> pageThemes = userAccountService.findAll(page);
		return pageThemes.getContent();
		
		
	}
	
	@GetMapping("/api/lecons")
	public List<Lecon>  getLecons(Model model,  Pageable page){

		Page<Lecon> pageThemes = leconService.findAll(page);
		return pageThemes.getContent();
		
		
	}
	
	@GetMapping("/api/eleves")
	public List<Eleve>  getEleves(Model model,  Pageable page){

		Page<Eleve> pageThemes = eleveService.findAll(page);
		return pageThemes.getContent();
		
		
	}
	
	

}
