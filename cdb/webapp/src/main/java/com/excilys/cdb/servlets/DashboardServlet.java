package com.excilys.cdb.servlets;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ServiceS;




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
		this.session.setNbComputerFound(service.getNombreTotalComputer());
		
	}
	
	@GetMapping(value="/")
	public String login() {
		return "redirect:/login";
	}
	
	@GetMapping(value="/new")
	public String newDashboard() {
		this.session = new Session();
		session.setPage(new Page());
		this.session.getPage().setNbElementTotal(service.getNombreTotalComputer());
		this.session.setNbComputerFound(service.getNombreTotalComputer());
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
		if(this.session.getOrderBy()!=null && orderBy.equals(this.session.getOrderBy()))
			this.session.setSort("DESC");
		else {
			this.session.setOrderBy(orderBy);
			this.session.setSort("ASC");
		}
		return VUE_DASHBOARD_REDIRECT;
	}
	
	
	@GetMapping(value="/search",params="search")
	public String updateSearch(@RequestParam("search") String search) {
		this.session.setSearch(search);
		session.getPage().setNumeroPage(1);
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

		Pageable page = null;
		if(session.getOrderBy()!=null){
			if(this.session.getSort().equals("ASC")){
				page = PageRequest.of(this.session.getPage().getNumeroPage()-1,this.session.getPage().getNbLigne(),Sort.by(Sort.Order.asc(this.session.getOrderBy())));
			}
			else {
				page = PageRequest.of(this.session.getPage().getNumeroPage()-1,this.session.getPage().getNbLigne(),Sort.by(Sort.Order.desc(this.session.getOrderBy())));
			}
		}
		else {
			page=PageRequest.of(this.session.getPage().getNumeroPage()-1,this.session.getPage().getNbLigne());	
		}
		
		List<Computer> liste =null;
		if(session.getSearch()!=null) {
			liste = service.getListeComputerByNameOrderBy("%"+session.getSearch()+"%",page);
			this.session.setNbComputerFound(service.getNbElementListeSearch("%"+session.getSearch()+"%"));
		}else {
			 liste=service.getElementPage(page);
		}
		
		
		return liste;
	}

	
	
	

}
