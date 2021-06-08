package com.excilys.servlets;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import com.excilys.config.AppConfig;
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.service.ServiceS;


@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Page page = new Page();
	private String search;
	private String sortedBy;
	
	private ServiceS service;
	private static Logger logger = LoggerFactory.getLogger(DashboardServlet.class);

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
		
		
		if(request.getParameter("new")!=null) {
			this.search=null;
			this.page = new Page();
			this.sortedBy = null;
		}
		
		page.setNbElementTotal(service.getNombreTotalComputer());
		
		if(request.getParameter("num")!=null)
			page.setNumeroPage(page.getNumeroPage()+ Integer.parseInt(request.getParameter("num")));
		
		if(request.getParameter("bouton")!=null) {
				page.setNbLigne(Integer.parseInt(request.getParameter("bouton")));
		}
		
		if(request.getParameter("boutonDebFin")!=null) {
			this.choisirPage(request.getParameter("boutonDebFin"));
		}
	
		List<Computer> listeComputer= service.getElementPage((page.getNumeroPage()-1)*page.getNbLigne(), page.getNbLigne());
		
		
		if(request.getParameter("search")!=null) {
			this.search = request.getParameter("search");
		}
		
		if(this.search!=null) {
			listeComputer = service.getListeComputerByName(search,(page.getNumeroPage()-1)*page.getNbLigne(), page.getNbLigne());
			int count = service.getNbElementListeSearch(search);
			page.setNbElementTotal(count);
		}
		
		if(request.getParameter("orderBy")!=null) {
			this.sortedBy = request.getParameter("orderBy");
		}
		
		if(this.sortedBy!=null) {

			switch(sortedBy) {
			case "name":{
					listeComputer = service.getListeComputerOrderedByName((page.getNumeroPage()-1)*page.getNbLigne(), page.getNbLigne());
					break;
				}
				case "introduced":{
					listeComputer = service.getListeComputerOrderedByIntroduced((page.getNumeroPage()-1)*page.getNbLigne(), page.getNbLigne());
					break;
				}
				case "discontinued":{
					listeComputer = service.getListeComputerOrderedByDiscontinued((page.getNumeroPage()-1)*page.getNbLigne(), page.getNbLigne());
					break;
				}
				case "company_id":{
					listeComputer = service.getListeComputerOrderedByCompany((page.getNumeroPage()-1)*page.getNbLigne(), page.getNbLigne());
					break;
				}
			}
		}
		
		
		if((this.sortedBy!=null)&&(this.search!=null)) {
			System.out.println(sortedBy);
			System.out.println(search);
			listeComputer = service.getListeOrdered((page.getNumeroPage()-1)*page.getNbLigne(), page.getNbLigne(),sortedBy,search);
		}

		request.setAttribute("listeComputer",listeComputer );
		request.setAttribute("page",this.page);
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/dashboard.jsp" ).forward( request, response );
	
	}
	
	 @Override
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
		
			if(request.getParameter("selection")!=null) {
				String string = request.getParameter("selection");
				List<String> computerListID = Arrays.asList(string.split(","));
				for(String id : computerListID) {
					service.effacerComputer(Integer.parseInt(id));
				}
			}
			
			
			response.sendRedirect("dashboard");
	}
	
	
	private void choisirPage(String s) {
		if(s.equals("0")) {
			this.page.setNumeroPage(1);
		}
		else {
			int nbTotal = this.page.getNbElementTotal();
			int nbParPage = this.page.getNbLigne();
			this.page.setNumeroPage((nbTotal%nbParPage) == 0 ? nbTotal/nbParPage : nbTotal/nbParPage +1);
		
		}
	}
}
