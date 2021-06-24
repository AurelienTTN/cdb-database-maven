package com.excilys.cdb.servlets;



import java.util.List;

import javax.servlet.http.HttpServlet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.ServiceS;



@Controller
public class AddComputerServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);
	
	private ServiceS service;
	
	public AddComputerServlet(ServiceS service) {
		this.service = service;
	}
	

	@GetMapping(value="/addComputer")
	public ModelAndView addComputer() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("addComputer");
		List<Company> listeCompany = service.getListCompany();
		mv.addObject("listeCompany", listeCompany);
		mv.addObject("computerDTO",new ComputerDTO());
		return mv;
	}
	
	@PostMapping(value ="/addComputer")
		public String add(@ModelAttribute("computerDTO") ComputerDTO computerDTO) {
	    service.ajouterComputer(computerDTO);
	    return "redirect:/addComputer";
	}
	
}