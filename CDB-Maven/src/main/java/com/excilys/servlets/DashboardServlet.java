package com.excilys.servlets;


import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.service.Service;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Page page = new Page();
	private String search;
	private String sortedBy;

	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
		if(request.getParameter("new")!=null) {
			this.search=null;
			this.page = new Page();
			this.sortedBy = null;
		}
		
		
		Service service = Service.getInstance();
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
				case "company":{
					listeComputer = service.getListeComputerOrderedByCompany((page.getNumeroPage()-1)*page.getNbLigne(), page.getNbLigne());
					break;
				}
			}
		}

		request.setAttribute("listeComputer",listeComputer );
		request.setAttribute("page",this.page);
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/dashboard.jsp" ).forward( request, response );
	
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
		
			Service service = Service.getInstance();
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
