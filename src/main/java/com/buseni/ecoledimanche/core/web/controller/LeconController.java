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
import com.buseni.ecoledimanche.core.domain.Lecon;
import com.buseni.ecoledimanche.core.service.LeconService;
import com.buseni.ecoledimanche.exception.BusinessException;
import com.buseni.ecoledimanche.exception.ErrorsHelper;
import com.buseni.ecoledimanche.utils.PageWrapper;

@Controller
@Navigation(url="/lecons", name="Liste des leçons", parent={  EspaceMonoController.class})
public class LeconController {
public static final Logger LOGGER = LoggerFactory.getLogger( LeconController.class );

@Autowired
private LeconService leconService;
	
	@GetMapping("/lecons")
	public String lecons(Model model, Pageable page){		
		
		LOGGER.info("IN: Lecons/list-GET");

		Page<Lecon> pageLecons = leconService.findAll(page);
		PageWrapper<Lecon> pageWrapper = new PageWrapper<Lecon>(pageLecons, "/lecons");
		model.addAttribute("page", pageWrapper);
		model.addAttribute("lecons", pageLecons.getContent());	
		if(!model.containsAttribute("lecon")){
			model.addAttribute("lecon", new Lecon());
		}
		return "lecons/listeLecons";
	}

	
	@GetMapping("/lecons/new")
	public String newLecon(Model model){	

		LOGGER.info("IN: Lecons/new-GET");
		model.addAttribute("lecon", new Lecon());
		return "lecons/editLecon";
	}

	
	@GetMapping("/lecons/edit")
	public String editLecon(@RequestParam(value="id", required=true) Integer id, Model model, HttpServletRequest request){	
		LOGGER.info("IN: Lecons/edit-GET");
		if (isRememberMeAuthenticated()) {
			//send login for update
			setRememberMeTargetUrlToSession(request, "/lecons/edit?id="+id);
			model.addAttribute("loginUpdate", true);
			return "signin";
		}
		Lecon lecon =  leconService.findById(id);
		model.addAttribute("lecon", lecon);
		return "lecons/editLecon";
	}

	
	@GetMapping("/lecons/delete")
	public String deleteLecon(@RequestParam(value="id", required=true) Integer id, RedirectAttributes attributes){		
		LOGGER.info("IN: Lecons/delete-GET");
		leconService.delete(id);
		String message = "Leçon " + id + " was successfully deleted";
		attributes.addFlashAttribute("message", message);		
		return "redirect:/lecons";
	}

	
	@PostMapping("/lecons/save")
	public String saveLecon(@Valid @ModelAttribute Lecon lecon , BindingResult result, RedirectAttributes attributes){		
		LOGGER.info("IN: Lecons/save-POST");
		//Validation erros	
		if (result.hasErrors()) {
			LOGGER.info("Lecons/save-POST error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.lecon", result);
			attributes.addFlashAttribute("lecon", lecon);
			return "lecons/editLecon";

		}else{

			try {
				leconService.addOrUpdate(lecon);
				//Business errors	
			} catch (final BusinessException e) {
				ErrorsHelper.rejectErrors(result, e.getErrors());
				LOGGER.info("Lecons/save-POST error: " + result.toString());
				attributes.addFlashAttribute("org.springframework.validation.BindingResult.lecon", result);
				attributes.addFlashAttribute("lecon", lecon);
				return "lecons/editLecon";
			}

			String message = "Lecon " + lecon.getId() + " was successfully added";
			attributes.addFlashAttribute("message", message);
			return "redirect:/lecons";
		}
	}

	@ModelAttribute("currentMenu")
	public String module(){
		return "lecons";
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
