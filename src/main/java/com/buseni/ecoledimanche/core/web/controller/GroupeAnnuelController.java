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
import com.buseni.ecoledimanche.core.domain.GroupeAnnuel;
import com.buseni.ecoledimanche.core.service.GroupeAnnuelService;
import com.buseni.ecoledimanche.exception.BusinessException;
import com.buseni.ecoledimanche.exception.ErrorsHelper;
import com.buseni.ecoledimanche.utils.PageWrapper;

@Controller
@Navigation(url="/groupes-annuel", name="Liste des groupes par année scolaire", parent={  EspaceMonoController.class})
public class GroupeAnnuelController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger( GroupeAnnuelController.class );
	
	@Autowired
	private GroupeAnnuelService groupeAnnuelService;
	@GetMapping("/groupes-annuel")
	public String groupesAnnuel(Model model, Pageable page){		
		
		LOGGER.info("IN: GroupesAnnuel/list-GET");

		Page<GroupeAnnuel> pageGroupesAnnuel = groupeAnnuelService.findAll(page);
		PageWrapper<GroupeAnnuel> pageWrapper = new PageWrapper<GroupeAnnuel>(pageGroupesAnnuel, "/groupes-annuel");
		model.addAttribute("page", pageWrapper);
		model.addAttribute("groupesAnnuel", pageGroupesAnnuel.getContent());	
		if(!model.containsAttribute("groupeAnnuel")){
			model.addAttribute("groupeAnnuel", new GroupeAnnuel());
		}
		return "groupes/listeGroupesAnnuel";
	}

	
	@GetMapping("/groupes-annuel/new")
	public String newGroupeAnnuel(Model model){	

		LOGGER.info("IN: GroupesAnnuel/new-GET");
		model.addAttribute("groupeAnnuel", new GroupeAnnuel());
		return "groupes/editGroupeAnnuel";
	}

	
	@GetMapping("/groupes-annuel/edit")
	public String editGroupeAnnuel(@RequestParam(value="id", required=true) Integer id, Model model, HttpServletRequest request){	
		LOGGER.info("IN: GroupesAnnuel/edit-GET");
		if (isRememberMeAuthenticated()) {
			//send login for update
			setRememberMeTargetUrlToSession(request, "/groupes/edit?id="+id);
			model.addAttribute("loginUpdate", true);
			return "signin";
		}
		GroupeAnnuel groupeAnnuel =  groupeAnnuelService.findById(id);
		model.addAttribute("groupeAnnuel", groupeAnnuel);
		return "groupes/editGroupeAnnuel";
	}

	
	@GetMapping("/groupes-annuel/delete")
	public String deleteGroupeAnnuel(@RequestParam(value="id", required=true) Integer id, RedirectAttributes attributes){		
		LOGGER.info("IN: GroupesAnnuel/delete-GET");
		groupeAnnuelService.delete(id);
		String message = "Groupe annuel " + id + " was successfully deleted";
		attributes.addFlashAttribute("message", message);		
		return "redirect:/groupes-annuel";
	}

	
	@PostMapping("/groupes-annuel/save")
	public String saveGroupeAnnuel(@Valid @ModelAttribute GroupeAnnuel groupeAnnuel , BindingResult result, RedirectAttributes attributes){		
		LOGGER.info("IN: GroupesAnnuel/save-POST");
		//Validation erros	
		if (result.hasErrors()) {
			LOGGER.info("GroupesAnnuel/save-POST error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.groupeAnnuel", result);
			attributes.addFlashAttribute("groupeAnnuel", groupeAnnuel);
			return "groupes/editGroupeAnnuel";

		}else{

			try {
				groupeAnnuelService.addOrUpdate(groupeAnnuel);
				//Business errors	
			} catch (final BusinessException e) {
				ErrorsHelper.rejectErrors(result, e.getErrors());
				LOGGER.info("GroupesAnnuel/save-POST error: " + result.toString());
				attributes.addFlashAttribute("org.springframework.validation.BindingResult.groupeAnnuel", result);
				attributes.addFlashAttribute("groupeAnnuel", groupeAnnuel);
				return "groupes/editGroupeAnnuel";
			}

			String message = "GroupeAnnuel " + groupeAnnuel.getId() + " was successfully added";
			attributes.addFlashAttribute("message", message);
			return "redirect:/groupes-annuel";
		}
	}

	@ModelAttribute("currentMenu")
	public String module(){
		return "groupesAnnuel";
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
