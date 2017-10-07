package com.buseni.ecoledimanche.core.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
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

import com.buseni.ecoledimanche.account.domain.UserAccount;
import com.buseni.ecoledimanche.account.repo.UserAccountRepository;
import com.buseni.ecoledimanche.breadcrumbs.Navigation;
import com.buseni.ecoledimanche.core.domain.Planning;
import com.buseni.ecoledimanche.core.service.PlanningService;
import com.buseni.ecoledimanche.core.web.MoniteurEditor;
import com.buseni.ecoledimanche.exception.BusinessException;
import com.buseni.ecoledimanche.exception.ErrorsHelper;
import com.buseni.ecoledimanche.utils.PageWrapper;


@Controller
@Navigation(url="/planning", name="Planning", parent={  EspaceMonoController.class})
public class PlanningController {
	public static final Logger LOGGER = LoggerFactory.getLogger( PlanningController.class );
	
	@Autowired
	private PlanningService planningService;
	
	@Autowired
	private	UserAccountRepository moniteurRepo;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) throws Exception {
		
		 //The date format to parse or output your dates
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    //Create a new CustomDateEditor
	    CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
	   //Register it as custom editor for the Date type
	    binder.registerCustomEditor(Date.class, editor);
	    
		binder.registerCustomEditor(Set.class, "moniteurs", new CustomCollectionEditor(Set.class) {
			protected Object convertElement(Object element) {
				if (element instanceof UserAccount) {
					System.out.println("Converting from Moniteur to moniteurs: " + element);
					return element;
				}
				if (element instanceof String) {
					Integer id = Integer.valueOf((String) element);
					UserAccount moniteur = moniteurRepo.findById(id).get();
					System.out.println("Looking up staff for id " + element + ": " + moniteur);
					return moniteur;
				}
				System.out.println("Don't know what to do with: " + element);
				return null;
			}
		});
		//binder.registerCustomEditor(UserAccount.class,  new MoniteurEditor(moniteurRepo));
	}
	
	@GetMapping("/planning")
	public String plannings(Model model, Pageable page){	
		LOGGER.info("IN: Planning/list-GET");

		Page<Planning> pagePlanning = planningService.findAll(page);
		PageWrapper<Planning> pageWrapper = new PageWrapper<Planning>(pagePlanning, "/planning");
		model.addAttribute("page", pageWrapper);
		model.addAttribute("listePlanning", pagePlanning.getContent());	
		if(!model.containsAttribute("planning")){
			Planning	planning = new Planning();
			planning.setEnabled(true);
			model.addAttribute("planning", planning);
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
		if(!model.containsAttribute("planning")){
			Planning	planning = new Planning();
			planning.setEnabled(true);
			model.addAttribute("planning", planning);
		}
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
	@ModelAttribute("listeMoniteurs")
	public Set<UserAccount> listeMoniteurs(){
		return moniteurRepo.findByEnabledTrue().stream().collect(Collectors.toSet());
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
