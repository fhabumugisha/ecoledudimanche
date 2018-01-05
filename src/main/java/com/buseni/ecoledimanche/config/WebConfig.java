package com.buseni.ecoledimanche.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
//@Configuration
//@EnableWebMvc
public class WebConfig 
//implements WebMvcConfigurer
{
	//@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/")
		//.setCachePeriod(2592000)
        .resourceChain(true)
        .addResolver(new GzipResourceResolver())
        .addResolver(new PathResourceResolver());
		
		//String workingDir = System.getProperty("user.dir");
		/*registry.addResourceHandler("/images/**")
				.addResourceLocations("file:"+env.getProperty("files.location"))
				.setCachePeriod(3600)
	            .resourceChain(true)
	            .addResolver(new GzipResourceResolver())
	       .addResolver(new PathResourceResolver());*/
		
//		registry.addResourceHandler("/sitemap.xml")
//		.addResourceLocations("/sitemap.xml");
//		
//		registry.addResourceHandler("/robots.txt")
//		.addResourceLocations("/robots.txt")
		;
		
	}
	
}