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
import com.buseni.ecoledimanche.core.domain.Groupe;
import com.buseni.ecoledimanche.core.service.GroupeService;
import com.buseni.ecoledimanche.exception.BusinessException;
import com.buseni.ecoledimanche.exception.ErrorsHelper;
import com.buseni.ecoledimanche.utils.PageWrapper;

@Controller
@Navigation(url="/groupes", name="Liste des groupes", parent={  EspaceMonoController.class})
public class GroupeController {
	
public static final Logger LOGGER = LoggerFactory.getLogger( GroupeController.class );

@Autowired
private GroupeService groupeService;
	
	@GetMapping("/groupes")
	public String groupes(Model model, Pageable page){		
		
		LOGGER.info("IN: Groupes/list-GET");

		Page<Groupe> pageGroupes = groupeService.findAll(page);
		PageWrapper<Groupe> pageWrapper = new PageWrapper<Groupe>(pageGroupes, "/groupes");
		model.addAttribute("page", pageWrapper);
		model.addAttribute("groupes", pageGroupes.getContent());	
		if(!model.containsAttribute("groupe")){
			model.addAttribute("groupe", new Groupe());
		}
		return "groupes/listeGroupes";
	}

	
	@GetMapping("/groupes/new")
	public String newGroupe(Model model){	

		LOGGER.info("IN: Groupes/new-GET");
		model.addAttribute("groupe", new Groupe());
		return "groupes/editGroupe";
	}

	
	@GetMapping("/groupes/edit")
	public String editGroupe(@RequestParam(value="id", required=true) Integer id, Model model, HttpServletRequest request){	
		LOGGER.info("IN: Groupes/edit-GET");
		if (isRememberMeAuthenticated()) {
			//send login for update
			setRememberMeTargetUrlToSession(request, "/groupes/edit?id="+id);
			model.addAttribute("loginUpdate", true);
			return "signin";
		}
		Groupe groupe =  groupeService.findById(id);
		model.addAttribute("groupe", groupe);
		return "groupes/editGroupe";
	}

	
	@GetMapping("/groupes/delete")
	public String deleteGroupe(@RequestParam(value="id", required=true) Integer id, RedirectAttributes attributes){		
		LOGGER.info("IN: Groupes/delete-GET");
		groupeService.delete(id);
		String message = "Groupe " + id + " was successfully deleted";
		attributes.addFlashAttribute("message", message);		
		return "redirect:/groupes";
	}

	
	@PostMapping("/groupes/save")
	public String saveGroupe(@Valid @ModelAttribute Groupe groupe , BindingResult result, RedirectAttributes attributes){		
		LOGGER.info("IN: Groupes/save-POST");
		//Validation erros	
		if (result.hasErrors()) {
			LOGGER.info("Groupes/save-POST error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.groupe", result);
			attributes.addFlashAttribute("groupe", groupe);
			return "groupes/editGroupe";

		}else{

			try {
				groupeService.addOrUpdate(groupe);
				//Business errors	
			} catch (final BusinessException e) {
				ErrorsHelper.rejectErrors(result, e.getErrors());
				LOGGER.info("Groupes/save-POST error: " + result.toString());
				attributes.addFlashAttribute("org.springframework.validation.BindingResult.groupe", result);
				attributes.addFlashAttribute("groupe", groupe);
				return "groupes/editGroupe";
			}

			String message = "Groupe " + groupe.getId() + " was successfully added";
			attributes.addFlashAttribute("message", message);
			return "redirect:/groupes";
		}
	}

	@ModelAttribute("currentMenu")
	public String module(){
		return "groupes";
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
