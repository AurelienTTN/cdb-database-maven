package com.excilys.cdb.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.context.annotation.Bean;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

public class WebInitializer implements WebApplicationInitializer {
	
	  @Override
	    public void onStartup(ServletContext servletContext) {
		  	
		  	System.out.println("ça passe ici");
		  	AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
	        context.register(WebConfig.class);
	        context.setServletContext(servletContext);

	        servletContext.addFilter("securityFilter", new DelegatingFilterProxy("springSecurityFilterChain"))
	        		.addMappingForUrlPatterns(null, false, "/*");
	        

	        DispatcherServlet dispatcher = new DispatcherServlet(context);
	        dispatcher.setThrowExceptionIfNoHandlerFound(true);

	        Dynamic servlet = servletContext.addServlet("dispatcher", dispatcher);
	        servlet.setLoadOnStartup(1);
	        servlet.addMapping("/");
	    }
	  
	  
	
	 
}
