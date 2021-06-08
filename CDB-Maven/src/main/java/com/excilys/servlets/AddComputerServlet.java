package com.excilys.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.config.AppConfig;
import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.Mapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.ServiceS;

@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);
	private ServiceS service;
	
	@Override
	public void init() {
		try {
			super.init();
			ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
			
			service = context.getBean(ServiceS.class);
	}catch(ServletException e) {
		logger.error(e.getMessage(),e);
	}
	}
	
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
		
		List<Company> companies = service.getListCompany();
		request.setAttribute("listeCompany",companies );

		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/addComputer.jsp" ).forward( request, response );
		
		
	}
	
	@Override
	 public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		 	String name = request.getParameter( "computerName");
	        String date_entree = request.getParameter( "introduced" );
	        String date_sortie = request.getParameter( "discontinued");
	        String companyId = request.getParameter( "companyId" );
	        
	        
	        ComputerDTO computerDTO = new ComputerDTO(name,date_entree,date_sortie,companyId);
	        service.ajouterComputer(computerDTO);
	        response.sendRedirect("dashboard?new=1");
	    }
}
