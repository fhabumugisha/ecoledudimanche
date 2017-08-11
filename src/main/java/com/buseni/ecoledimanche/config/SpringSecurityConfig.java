package com.buseni.ecoledimanche.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.buseni.ecoledimanche.account.service.UserAccountService;
import com.buseni.ecoledimanche.filters.CustomSavedRequestAwareAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SpringSecurityConfig   extends WebSecurityConfigurerAdapter
{
	

@Autowired
	private UserAccountService userAccountService;
	
	
	@Autowired
	
	public  void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
		auth.userDetailsService(userAccountService).passwordEncoder(passwordEncoder());
		//auth.inMemoryAuthentication().withUser("admin@edd").password("password").roles("MONITEUR");
	}
	
	@Autowired
	private  DataSource dataSource;
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		 http.antMatcher("/espaceMono/**").authorizeRequests().anyRequest().hasRole("MONITEUR")
//		.and()
//			.formLogin().loginPage("/signin").permitAll().failureUrl("/signin?error").usernameParameter("email")
//				.passwordParameter("password").successHandler(savedRequestAwareAuthenticationSuccessHandler())
//        .and()
//        	.logout().logoutUrl("/logout").logoutSuccessUrl("/signin?logout").permitAll()
//        	.deleteCookies("JSESSIONID")
//        .and()
//        	.exceptionHandling().accessDeniedPage("/error403")
//        .and()
//           .sessionManagement()
//           .maximumSessions(1)
//             .sessionRegistry(sessionRegistry())
//             .expiredUrl("/signin?expired")
//             .and()
//          .and()
//        	//.rememberMe().tokenRepository(persistentTokenRepository()).tokenValiditySeconds(604800)
//        //.and()
//        .csrf().disable();
//	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.authorizeRequests()
		// .antMatchers("/","/home").permitAll()
		 .anyRequest().authenticated()
		.and()
			.formLogin().loginPage("/login").permitAll().usernameParameter("email")
				.passwordParameter("password")
				
        .and()
        	.logout().permitAll() 	
         .and()
         .rememberMe().tokenRepository(persistentTokenRepository()).tokenValiditySeconds(604800)	;
	}
	@Bean(name = "sessionRegistry")
	public SessionRegistry sessionRegistry() {
		 return new SessionRegistryImpl();
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
	web.ignoring().antMatchers("/resources/**").antMatchers("/static/**").antMatchers("/css/**").antMatchers("/images/**");
	 
	}
	
	@Bean(name="customSavedRequestAwareAuthenticationSuccessHandler")
	public  CustomSavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() { 
		CustomSavedRequestAwareAuthenticationSuccessHandler auth = new CustomSavedRequestAwareAuthenticationSuccessHandler();
		auth.setTargetUrlParameter("targetUrl");
		auth.setDefaultTargetUrl("/espaceMono");
		//auth.setUseReferer(true);
		return auth;
	}
	
	@Bean
	public  PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}
	/*@Bean
	public AccessDeniedHandler customAccessDeniedHandler(){
		CustomAccessDeniedHandlerImpl customAccessDeniedHandler = new CustomAccessDeniedHandlerImpl();
		customAccessDeniedHandler.setErrorPage("/admin403");
		return customAccessDeniedHandler;
	}*/

	

	
	
	
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	
}