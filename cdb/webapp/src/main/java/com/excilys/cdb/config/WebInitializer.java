package com.excilys.cdb.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.context.annotation.Bean;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
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
	  public void onStartup(ServletContext sc) throws ServletException {
	    AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
	    root.scan("com.excilys.cdb.config");
	 
	    sc.addListener(new ContextLoaderListener(root));
	 
	    ServletRegistration.Dynamic appServlet = sc.addServlet("appServlet",
	        new DispatcherServlet(new GenericWebApplicationContext()));
	    appServlet.setLoadOnStartup(1);
	    appServlet.addMapping("/");
	  }
	  
	
	 
}
