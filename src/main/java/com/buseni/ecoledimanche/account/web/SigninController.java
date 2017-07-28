package com.buseni.ecoledimanche.account.web;

import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buseni.ecoledimanche.account.domain.PasswordResetToken;
import com.buseni.ecoledimanche.account.domain.UserAccount;
import com.buseni.ecoledimanche.account.service.UserAccountService;

@Controller
//@Navigation(url="/signin", name="Sign in" , parent = HomeController.class)
public class SigninController {

	
	public  static final Logger LOGGER = LoggerFactory.getLogger(SigninController.class);
	
	
	
	@Autowired
	private UserAccountService userAccountService;
	
	
	
	 @Autowired
	 private MessageSource messages;

	
	 @Autowired
	private ApplicationEventPublisher eventPublisher;
	 

	
	
	@GetMapping(value="/signin")
	public String login(@RequestParam(value = "error", required = false) String error, 
			Model model, HttpServletRequest request){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(!(auth instanceof AnonymousAuthenticationToken)){
			model.asMap().clear();
			return "redirect:/";
		}
		Boolean browserSessionTimeout =  (Boolean) request.getSession().getAttribute("browserSessionTimeout");
		if (BooleanUtils.isTrue(browserSessionTimeout)) {
			String errorMessage  =  messages.getMessage("message.sessiontimeout", null, request.getLocale());
			model.addAttribute("error", errorMessage);
			request.getSession().removeAttribute("browserSessionTimeout");
		}
		return "home";
	}
	
	@GetMapping(value="/forgot-password")
	public String forgotPassword(Model model){
		return "forgotPassword";
	}
	
	@PostMapping(value = "/forgot-password")
	public String sendResetPassword(HttpServletRequest request, @RequestParam("email") String userEmail, RedirectAttributes attributes) {
	     
	    UserAccount account = userAccountService.findByUsername(userEmail);
	    if (account == null) {	    	
	    	String error = messages.getMessage("message.resetPasswordInvalidEmail", null, request.getLocale());		
	    	LOGGER.error("The entered email is not found");
			attributes.addFlashAttribute("error", error);
			attributes.addFlashAttribute("email", userEmail);	
			return "redirect:/forgot-password";
	    }
	 
	    String token = UUID.randomUUID().toString();
	    userAccountService.createPasswordResetTokenForUser(account, token);
	    String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	  //  eventPublisher.publishEvent(new ResetPasswordEvent(providerService.findProviderByUsername(userEmail), request.getLocale(), appUrl, token));
	  
	    	   
	    LOGGER.info("email sent");
	    String message = messages.getMessage("message.resetPasswordEmail", null, request.getLocale());			
		attributes.addFlashAttribute("message", message);		
		return "redirect:/forgot-password";	
	}
	
	@GetMapping(value="/reset-password")
	public String resetPassword(HttpServletRequest request,Locale locale, @RequestParam("id") Integer id,  @RequestParam("token") String token, RedirectAttributes model){
		PasswordResetToken passToken = userAccountService.getPasswordResetToken(token);
	   
	    if (passToken == null || passToken.getUserAccount().getId() != id) {
	        String error = messages.getMessage("auth.message.resetpasswordtoken.invalid", null, locale);
	        LOGGER.error("Invalid reset password token.");
	        model.addFlashAttribute("error", error);
	        return "redirect:/reset-password-error";
	    }
	 
	    Calendar cal = Calendar.getInstance();
	    if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	    	String error = messages.getMessage("auth.message.resetpasswordtoken.expired", null, locale);
	    	LOGGER.error("Your reset password token has expired.");
	        model.addFlashAttribute("error", error);
	        return "redirect:/reset-password-error";
	    }
	   
	  UserAccount account = passToken.getUserAccount();
	  //TODO dont do this
	  /*   UserDetails userDetails  = userAccountService.loadUserByUsername(account.getEmail());
	    Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	    SecurityContextHolder.getContext().setAuthentication(auth);*/
	  model.addFlashAttribute("token", passToken.getToken());
		model.addFlashAttribute("email", account.getEmail());
		return "redirect:/change-password";
	}
	
	@GetMapping(value="/reset-password-error")
	public String resetPasswordError(Model model){
		return "resetPasswordError";
	}
	
	@GetMapping(value="/change-password")
	public String changePassword(Model model){
		return "frontend/account/changePassword";
	}
	
	@PostMapping(value = "/change-password")
	//@ResponseBody
	public String savePassword(HttpServletRequest request, @RequestParam("token") String token, @RequestParam("email") String email, @RequestParam("password" ) String password, @RequestParam("passwordConfirm") String passwordConfirm, RedirectAttributes attributes) {
	  if(!password.equals(passwordConfirm)){
		  String error = messages.getMessage("error.passwordMatches", null, request.getLocale());		
	    	LOGGER.error("Password does not match!");
			attributes.addFlashAttribute("error", error);		
			attributes.addFlashAttribute("email", email);
			attributes.addFlashAttribute("token",token);
			return "redirect:/change-password";
	  }		
	  PasswordResetToken passToken = userAccountService.getPasswordResetToken(token);
	  UserAccount account  =  userAccountService.findByUsername(email);
	  if (passToken == null || account == null || passToken.getUserAccount().getId() != account.getId()) {
	        return "redirect:/";
	    }
		userAccountService.changeUserPassword(account, password);
	    String message = messages.getMessage("message.resetPasswordSuc", null, request.getLocale());			
		attributes.addFlashAttribute("message", message);
	    return "redirect:/profile/signin";
	}
	
	
	
	@ModelAttribute("currentMenu")
	public String module(){
		return "login";
	}
	
}
