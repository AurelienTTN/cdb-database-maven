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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.service.ServiceS;


@Controller

public class DashboardServlet extends HttpServlet {

	private static final String VUE_DASHBOARD_REDIRECT = "redirect:/dashboard";
	private static final long serialVersionUID = 1L;
	private Session session;
	private ServiceS service;
	private static Logger logger = LoggerFactory.getLogger(DashboardServlet.class);

	
	public DashboardServlet(ServiceS service,Session session) {
		this.service = service;
		this.session = session;
		session.setPage(new Page());
		this.session.getPage().setNbElementTotal(service.getNombreTotalComputer());
	}
	
	@GetMapping(value="/new")
	public String newDashboard() {
		this.session = new Session();
		this.session.setPage(new Page());
		this.session.getPage().setNbElementTotal(service.getNombreTotalComputer());
		return VUE_DASHBOARD_REDIRECT;
	}
	
	
	@GetMapping(value="/dashboard")
	public ModelAndView dashboard() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("dashboard");
		List<Computer> listeComputer = this.getListeComputer();
		mv.addObject("listeComputer",listeComputer);
		mv.addObject("session",this.session);
		mv.addObject("selection",new String());
		return mv;
	}
	
	@GetMapping(value = "/getNbElement", params = "bouton")
	public String updateNbElementPage(@RequestParam("bouton") int numLigne) {
		this.session.getPage().setNbLigne(numLigne);
		return VUE_DASHBOARD_REDIRECT;
	}
	
	@GetMapping(value = "/getNumPage", params = "num")
	public String updateNumPage(@RequestParam("num") int num) {
		this.session.getPage().setNumeroPage(this.session.getPage().getNumeroPage()+num);
		return VUE_DASHBOARD_REDIRECT;
	}
	
	@GetMapping(value = "/extremites", params = "boutonDebFin")
	public String updatePage(@RequestParam("boutonDebFin") String num) {
		if(num.equals("0")) {
			session.getPage().setNumeroPage(1);
		}
		else {
			int nbTotal = session.getPage().getNbElementTotal();
			int nbParPage = session.getPage().getNbLigne();
			this.session.getPage().setNumeroPage((nbTotal%nbParPage) == 0 ? nbTotal/nbParPage : nbTotal/nbParPage +1);
		
		}
		return VUE_DASHBOARD_REDIRECT;
	}
	
	@GetMapping(value = "/order", params = "orderBy")
	public String updateOrder(@RequestParam("orderBy") String orderBy) {
		this.session.setOrderBy(orderBy);
		return VUE_DASHBOARD_REDIRECT;
	}
	
	@GetMapping(value="/search",params="search")
	public String updateSearch(@RequestParam("search") String search) {
		this.session.setSearch(search);
		return VUE_DASHBOARD_REDIRECT;
	}
	
	@PostMapping(value="/delete")
	public String delete(@RequestParam("selection") String selection) {
		List<String> computerListID = Arrays.asList(selection.split(","));
		for(String id : computerListID) {
			this.service.effacerComputer(Integer.parseInt(id));
		}
		return VUE_DASHBOARD_REDIRECT;
	}
	
	
	private List<Computer> getListeComputer(){
		
		Page page = session.getPage();
		List<Computer> liste=service.getElementPage((page.getNumeroPage()-1)*page.getNbLigne(), page.getNbLigne());
		
		if(session.getSearch()!=null) {
			liste = service.getListeComputerByName(session.getSearch(),(page.getNumeroPage()-1)*page.getNbLigne(), page.getNbLigne());
		}
		if(session.getOrderBy()!=null) {

			switch(session.getOrderBy()) {
			case "name":{
					liste = service.getListeComputerOrderedByName((page.getNumeroPage()-1)*page.getNbLigne(), page.getNbLigne());
					break;
				}
				case "introduced":{
					liste = service.getListeComputerOrderedByIntroduced((page.getNumeroPage()-1)*page.getNbLigne(), page.getNbLigne());
					break;
				}
				case "discontinued":{
					liste = service.getListeComputerOrderedByDiscontinued((page.getNumeroPage()-1)*page.getNbLigne(), page.getNbLigne());
					break;
				}
				
				case "company_id":{
					liste = service.getListeComputerOrderedByCompany((page.getNumeroPage()-1)*page.getNbLigne(), page.getNbLigne());
					break;
				}
			}
		}
	
		return liste;
	}
	
	
	/*
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
	*/
}
