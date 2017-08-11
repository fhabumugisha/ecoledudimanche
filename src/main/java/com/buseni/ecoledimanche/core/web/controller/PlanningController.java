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
import com.buseni.ecoledimanche.core.domain.Planning;
import com.buseni.ecoledimanche.core.service.PlanningService;
import com.buseni.ecoledimanche.exception.BusinessException;
import com.buseni.ecoledimanche.exception.ErrorsHelper;
import com.buseni.ecoledimanche.utils.PageWrapper;


@Controller
@Navigation(url="/planning", name="Planning", parent={  EspaceMonoController.class})
public class PlanningController {
	public static final Logger LOGGER = LoggerFactory.getLogger( PlanningController.class );
	
	@Autowired
	private PlanningService planningService;
	
	@GetMapping("/planning")
	public String plannings(Model model, Pageable page){	
		LOGGER.info("IN: Planning/list-GET");

		Page<Planning> pagePlannings = planningService.findAll(page);
		PageWrapper<Planning> pageWrapper = new PageWrapper<Planning>(pagePlannings, "/planning");
		model.addAttribute("page", pageWrapper);
		model.addAttribute("plannings", pagePlannings.getContent());	
		if(!model.containsAttribute("planning")){
			model.addAttribute("planning", new Planning());
		}
		return "planning/listePlanning";
	}
	
	
	@GetMapping("/planning/edit")
	public String editPlanning(@RequestParam(value="id", required=true) Integer id, Model model, HttpServletRequest request){			
		LOGGER.info("IN: Planning/edit-GET");
		if (isRememberMeAuthenticated()) {
			//send login for update
			setRememberMeTargetUrlToSession(request, "/planning/edit?id="+id);
			model.addAttribute("loginUpdate", true);
			return "signin";
		}
		Planning planning =  planningService.findById(id);
		model.addAttribute("planning", planning);
		return "planning/editPlanning";
	}

	@GetMapping("/planning/new")
	public String addPlanning(Model model){		
		LOGGER.info("IN: Planning/new-GET");
		model.addAttribute("planning", new Planning());
		return "planning/editPlanning";
	}
	
	@GetMapping("/planning/delete")
	public String deletePlanning(@RequestParam(value="id", required=true) Integer id, RedirectAttributes attributes){	
		
		LOGGER.info("IN: Provinces/delete-GET");
		planningService.delete(id);
		String message = "Planning " + id + " was successfully deleted";
		attributes.addFlashAttribute("message", message);		
		return "redirect:/planning";
	}
	
	@PostMapping("/planning/save")
	public String savePlanning(@Valid @ModelAttribute Planning planning , BindingResult result, RedirectAttributes attributes){			
		
		LOGGER.info("IN: Planning/save-POST");
		//Validation erros	
		if (result.hasErrors()) {
			LOGGER.info("Planning/save-POST error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.planning", result);
			attributes.addFlashAttribute("planning", planning);
			return "planning/editPlanning";

		}else{

			try {
				planningService.addOrUpdate(planning);
				//Business errors	
			} catch (final BusinessException e) {
				ErrorsHelper.rejectErrors(result, e.getErrors());
				LOGGER.info("Planning/save-POST error: " + result.toString());
				attributes.addFlashAttribute("org.springframework.validation.BindingResult.planning", result);
				attributes.addFlashAttribute("planning", planning);
				return "planning/editPlanning";
			}

			String message = "Planning " + planning.getId() + " was successfully added";
			attributes.addFlashAttribute("message", message);
			return "redirect:/planning";
		}

	}
	@ModelAttribute("currentMenu")
	public String module(){
		return "planning";
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
