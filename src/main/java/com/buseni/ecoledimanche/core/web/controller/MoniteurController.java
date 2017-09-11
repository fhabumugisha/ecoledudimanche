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

import com.buseni.ecoledimanche.account.domain.Role;
import com.buseni.ecoledimanche.account.domain.UserAccount;
import com.buseni.ecoledimanche.account.service.RoleService;
import com.buseni.ecoledimanche.account.service.UserAccountService;
import com.buseni.ecoledimanche.breadcrumbs.Navigation;
import com.buseni.ecoledimanche.core.domain.Groupe;
import com.buseni.ecoledimanche.core.repo.GroupeRepo;
import com.buseni.ecoledimanche.exception.BusinessException;
import com.buseni.ecoledimanche.exception.ErrorsHelper;
import com.buseni.ecoledimanche.utils.PageWrapper;

@Controller
@Navigation(url="/moniteurs", name="Liste des moniteurs", parent={  EspaceMonoController.class})
public class MoniteurController {

	public static final Logger LOGGER = LoggerFactory.getLogger( MoniteurController.class );

	@Autowired
	private UserAccountService  userAccountService;

	@Autowired
	private GroupeRepo groupeRepo;
	
	@Autowired
	private RoleService roleService;
	
	
	@InitBinder
	public void dateBinder(WebDataBinder binder) {
	    //The date format to parse or output your dates
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    //Create a new CustomDateEditor
	    CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
	   //Register it as custom editor for the Date type
	    binder.registerCustomEditor(Date.class, editor);
	}

	@GetMapping("/moniteurs")
	public String moniteurs(Model model, Pageable page){		

		LOGGER.info("IN: Moniteurs/list-GET");

		Page<UserAccount> pageMoniteurs = userAccountService.findAll(page);
		PageWrapper<UserAccount> pageWrapper = new PageWrapper<UserAccount>(pageMoniteurs, "/moniteurs");
		model.addAttribute("page", pageWrapper);
		model.addAttribute("moniteurs", pageMoniteurs.getContent());	
		if(!model.containsAttribute("moniteur")){
			model.addAttribute("moniteur", new UserAccount());
		}
		return "moniteurs/listeMoniteurs";
	}


	@GetMapping("/moniteurs/new")
	public String newMoniteur(Model model){	

		LOGGER.info("IN: Moniteurs/new-GET");
		if(!model.containsAttribute("moniteur")){
			UserAccount moniteur = new UserAccount();
			moniteur.setEnabled(true);
			model.addAttribute("moniteur", moniteur);
		}
		
		return "moniteurs/editMoniteur";
	}


	@GetMapping("/moniteurs/edit")
	public String editMoniteur(@RequestParam(value="id", required=true) Integer id, Model model, HttpServletRequest request){	
		LOGGER.info("IN: Moniteurs/edit-GET");
		if (isRememberMeAuthenticated()) {
			//send login for update
			setRememberMeTargetUrlToSession(request, "/moniteurs/edit?id="+id);
			model.addAttribute("loginUpdate", true);
			return "signin";
		}
		UserAccount moniteur =  userAccountService.findById(id);
		for(Role role : moniteur.getRoles()){
			moniteur.getListRoles().add(role.getName());
		}
		model.addAttribute("moniteur", moniteur);
		return "moniteurs/editMoniteur";
	}


	@GetMapping("/moniteurs/delete")
	public String deleteMoniteur(@RequestParam(value="id", required=true) Integer id, RedirectAttributes attributes){		
		LOGGER.info("IN: Moniteurs/delete-GET");
		userAccountService.delete(id);
		String message = "Moniteur " + id + " was successfully deleted";
		attributes.addFlashAttribute("message", message);		
		return "redirect:/moniteurs";
	}


	@PostMapping("/moniteurs/save")
	public String saveMoniteur(@Valid @ModelAttribute UserAccount moniteur , BindingResult result, RedirectAttributes attributes){		
		LOGGER.info("IN: Moniteurs/save-POST");
		//Validation erros	
		if (result.hasErrors()) {
			LOGGER.info("Moniteurs/save-POST error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.moniteur", result);
			attributes.addFlashAttribute("moniteur", moniteur);
			return "redirect:/moniteurs/new";

		}else{

			try {
				userAccountService.addOrUpdate(moniteur);
				//Business errors	
			} catch (final BusinessException e) {
				ErrorsHelper.rejectErrors(result, e.getErrors());
				LOGGER.info("Moniteurs/save-POST error: " + result.toString());
				attributes.addFlashAttribute("org.springframework.validation.BindingResult.moniteur", result);
				attributes.addFlashAttribute("moniteur", moniteur);
				return "redirect:/moniteurs/new";
			}

			String message = "Moniteur " + moniteur.getId() + " was successfully added";
			attributes.addFlashAttribute("message", message);
			return "redirect:/moniteurs";
		}
	}

	@ModelAttribute("currentMenu")
	public String module(){
		return "moniteurs";
	}

	@ModelAttribute("listeGroupes")
	public List<Groupe> listeGroupes(){
		return groupeRepo.findByEnabledTrue();
	}
	
	/**
	 * Retourne la liste des roles
	 * @return
	 */
	@ModelAttribute("allRoles")
	public List<Role> getAllRoles(){	
		return  roleService.findAdminRoles();
		
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
