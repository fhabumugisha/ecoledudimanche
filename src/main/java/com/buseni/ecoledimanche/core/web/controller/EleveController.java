package com.buseni.ecoledimanche.core.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buseni.ecoledimanche.breadcrumbs.Navigation;
import com.buseni.ecoledimanche.core.domain.Eleve;
import com.buseni.ecoledimanche.core.domain.Groupe;
import com.buseni.ecoledimanche.core.repo.GroupeRepo;
import com.buseni.ecoledimanche.core.service.EleveService;
import com.buseni.ecoledimanche.exception.BusinessException;
import com.buseni.ecoledimanche.exception.ErrorsHelper;
import com.buseni.ecoledimanche.utils.PageWrapper;


@Controller
@Navigation(url="/eleves", name="Liste des élèves", parent={  EspaceMonoController.class})
public class EleveController {
	public static final Logger LOGGER = LoggerFactory.getLogger( EleveController.class );
	
	@Autowired
	private EleveService eleveService;
	
	@Autowired
	private GroupeRepo groupeRepo;
	
	@InitBinder
	public void dateBinder(WebDataBinder binder) {
	    //The date format to parse or output your dates
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    //Create a new CustomDateEditor
	    CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
	   //Register it as custom editor for the Date type
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@GetMapping("/eleves")
	public String eleves(Model model, Pageable page){	
		LOGGER.info("IN: Eleves/list-GET");

		Page<Eleve> pageEleves = eleveService.findAll(page);
		PageWrapper<Eleve> pageWrapper = new PageWrapper<Eleve>(pageEleves, "/eleves");
		model.addAttribute("page", pageWrapper);
		model.addAttribute("eleves", pageEleves.getContent());	
		if(!model.containsAttribute("eleve")){
			model.addAttribute("eleve", new Eleve());
		}
		return "eleves/listeEleves";
	}
	
	
	@GetMapping("/eleves/edit")
	public String editEleve(@RequestParam(value="id", required=true) Integer id, Model model, HttpServletRequest request){			
		LOGGER.info("IN: Eleves/edit-GET");
		if (isRememberMeAuthenticated()) {
			//send login for update
			setRememberMeTargetUrlToSession(request, "/eleves/edit?id="+id);
			model.addAttribute("loginUpdate", true);
			return "signin";
		}
		Eleve eleve =  eleveService.findById(id);
		model.addAttribute("eleve", eleve);
		return "eleves/editEleve";
	}

	@GetMapping("/eleves/new")
	public String addEleve(Model model){		
		LOGGER.info("IN: Eleves/new-GET");
		if(!model.containsAttribute("eleve")){
			Eleve	eleve =  new Eleve();
			eleve.setEnabled(true);
			model.addAttribute("eleve", eleve);
		}
		
		return "eleves/editEleve";
	}
	
	@GetMapping("/eleves/delete")
	public String deleteEleve(@RequestParam(value="id", required=true) Integer id, RedirectAttributes attributes){	
		
		LOGGER.info("IN: Provinces/delete-GET");
		eleveService.delete(id);
		String message = "Eleve " + id + " was successfully deleted";
		attributes.addFlashAttribute("message", message);		
		return "redirect:/eleves";
	}
	
	@PostMapping("/eleves/save")
	public String saveEleve(@Valid @ModelAttribute Eleve eleve , BindingResult result, RedirectAttributes attributes){			
		
		LOGGER.info("IN: Eleves/save-POST");
		//Validation erros	
		if (result.hasErrors()) {
			LOGGER.info("Eleves/save-POST error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.eleve", result);
			attributes.addFlashAttribute("eleve", eleve);
			return  "eleves/editEleve";

		}else{

			try {
				eleveService.addOrUpdate(eleve);
				//Business errors	
			} catch (final BusinessException e) {
				ErrorsHelper.rejectErrors(result, e.getErrors());
				LOGGER.info("Eleves/save-POST error: " + result.toString());
				attributes.addFlashAttribute("org.springframework.validation.BindingResult.eleve", result);
				attributes.addFlashAttribute("eleve", eleve);
				return  "eleves/editEleve";
			}

			String message = "Eleve " + eleve.getId() + " was successfully added";
			attributes.addFlashAttribute("message", message);
			return "redirect:/eleves";
		}

	}
	@ModelAttribute("currentMenu")
	public String module(){
		return "eleves";
	}
	@ModelAttribute("listeGroupes")
	public List<Groupe> listeGroupes(){
		return groupeRepo.findByEnabledTrue();
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
