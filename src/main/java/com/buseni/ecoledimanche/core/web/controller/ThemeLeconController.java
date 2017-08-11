package com.buseni.ecoledimanche.core.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buseni.ecoledimanche.breadcrumbs.Navigation;
import com.buseni.ecoledimanche.core.domain.ThemeLecon;
import com.buseni.ecoledimanche.core.service.ThemeLeconService;
import com.buseni.ecoledimanche.exception.BusinessException;
import com.buseni.ecoledimanche.exception.ErrorsHelper;
import com.buseni.ecoledimanche.utils.PageWrapper;
@Controller
@Navigation(url="/themes", name="Liste des themes", parent={  EspaceMonoController.class})
public class ThemeLeconController {
	
public static final Logger LOGGER = LoggerFactory.getLogger( ThemeLeconController.class );

@Autowired
private ThemeLeconService themeLeconService;
	
	@GetMapping("/themes")
	public String themes(Model model, Pageable page){		
		
		LOGGER.info("IN: Themes/list-GET");

		Page<ThemeLecon> pageThemes = themeLeconService.findAll(page);
		PageWrapper<ThemeLecon> pageWrapper = new PageWrapper<ThemeLecon>(pageThemes, "/themes");
		model.addAttribute("page", pageWrapper);
		model.addAttribute("themes", pageThemes.getContent());	
		if(!model.containsAttribute("theme")){
			model.addAttribute("theme", new ThemeLecon());
		}
		return "lecons/listeLecons";
	}

	
	@GetMapping("/themes/new")
	public String newTheme(Model model){	

		LOGGER.info("IN: Themes/new-GET");
		model.addAttribute("theme", new ThemeLecon());
		return "lecons/editTheme";
	}

	
	@GetMapping("/themes/edit")
	public String editTheme(@RequestParam(value="id", required=true) Integer id, Model model, HttpServletRequest request){	
		LOGGER.info("IN: Themes/edit-GET");
		if (isRememberMeAuthenticated()) {
			//send login for update
			setRememberMeTargetUrlToSession(request, "/themes/edit?id="+id);
			model.addAttribute("loginUpdate", true);
			return "signin";
		}
		ThemeLecon theme =  themeLeconService.findById(id);
		model.addAttribute("theme", theme);
		return "lecons/editTheme";
	}

	
	@GetMapping("/themes/delete")
	public String deleteTheme(@RequestParam(value="id", required=true) Integer id, RedirectAttributes attributes){		
		LOGGER.info("IN: Themes/delete-GET");
		themeLeconService.delete(id);
		String message = "Thème " + id + " was successfully deleted";
		attributes.addFlashAttribute("message", message);		
		return "redirect:/themes";
	}

	
	@PostMapping("/themes/save")
	public String saveTheme(@Valid @ModelAttribute ThemeLecon theme , BindingResult result, RedirectAttributes attributes){		
		LOGGER.info("IN: Themes/save-POST");
		//Validation erros	
		if (result.hasErrors()) {
			LOGGER.info("Themes/save-POST error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.theme", result);
			attributes.addFlashAttribute("theme", theme);
			return "lecons/editTheme";

		}else{

			try {
				themeLeconService.addOrUpdate(theme);
				//Business errors	
			} catch (final BusinessException e) {
				ErrorsHelper.rejectErrors(result, e.getErrors());
				LOGGER.info("Themes/save-POST error: " + result.toString());
				attributes.addFlashAttribute("org.springframework.validation.BindingResult.theme", result);
				attributes.addFlashAttribute("theme", theme);
				return "lecons/editTheme";
			}

			String message = "ThemeLecon " + theme.getId() + " was successfully added";
			attributes.addFlashAttribute("message", message);
			return "redirect:/themes";
		}
	}

	@ModelAttribute("currentMenu")
	public String module(){
		return "themes";
	}
	

	/**
	 * save targetURL in session
	 */
	private void setRememberMeTargetUrlToSession(HttpServletRequest request, String targetUrl){
		HttpSession session = request.getSession(false);
		if(session!=null){
			LOGGER.info("setting target url : " + targetUrl);
			session.setAttribute("targetUrl", targetUrl);
		}
	}
	/**
	 * Check if user is login by remember me cookie, refer
	 * org.springframework.security.authentication.AuthenticationTrustResolverImpl
	 */
	private boolean isRememberMeAuthenticated() {
 
		Authentication authentication = 
                    SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return false;
		}
 
		return RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass());
	}


}
