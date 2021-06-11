package com.excilys.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.slf4j.LoggerFactory;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.config.AppConfig;
import com.excilys.dto.ComputerDTO;
import com.excilys.exceptions.ExceptionComputerVide;
import com.excilys.mapper.Mapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.ServiceS;
import org.slf4j.Logger;



@Controller
public class EditComputerServlet extends  HttpServlet {

	private static final long serialVersionUID = 1L;
	private static String idPCMaj;
	
	private static Logger logger = LoggerFactory.getLogger(EditComputerServlet.class);
	private Mapper mappy;
	private ServiceS service;
	
	private EditComputerServlet(Mapper mappy,ServiceS service) {
		this.mappy = mappy;
		this.service = service;
	}
	
	@GetMapping(value="/editComputer",params="computerID")
	public ModelAndView editComputer(@RequestParam("computerID") int id) {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("editComputer");

		Computer computer=null;
		try {
			computer = service.getOneComputer(id);
		} catch (ExceptionComputerVide e) {
			logger.error("Aucun ordinateur trouvé");
		}
		List<Company> company = service.getListCompany();
		
		mv.addObject("computerActual",computer);
		mv.addObject("listeCompany",company);
		mv.addObject("computerDTO",new ComputerDTO());
		return mv;
	}
	
	@PostMapping(value ="/sendComputer")
	public String add(@ModelAttribute("computerDTO") ComputerDTO computerDTO) {
		Computer computerMaj = mappy.createComputer(computerDTO);
		service.majComputer(computerMaj);
	    return "redirect:/dashboard";
    }
	
	/*
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
		Computer computer = null;
		
		if(request.getParameter("computerID")!=null) {
			request.setAttribute("idActualComputer",request.getParameter("computerID") );
			idPCMaj = request.getParameter("computerID");
			try {
				computer = service.getOneComputer(Integer.parseInt(idPCMaj));
			} catch (NumberFormatException | ExceptionComputerVide e) {
				e.printStackTrace();
			}
			request.setAttribute("computerActual",computer);
		}
		
		
		List<Company> companies = service.getListCompany();
		request.setAttribute("listeCompany",companies );
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/editComputer.jsp" ).forward( request, response );
	}
	
	@Override
	 public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		 	
		 	
		 	String name = request.getParameter( "computerName");
	        String date_entree = request.getParameter( "introduced" );
	        String date_sortie = request.getParameter( "discontinued");
	        String companyId = request.getParameter( "companyId" );
	        
	        Computer computer=null;
	        
			try {
				computer = service.getOneComputer(Integer.parseInt(idPCMaj));
			} catch (NumberFormatException | ExceptionComputerVide e) {
				e.printStackTrace();
			}
			

			ComputerDTO computerDTO = mappy.createComputerDTO(computer);
			
			if(!name.isBlank()) 
				computerDTO.setName(name);
		
			if(!date_entree.isBlank()) 
				computerDTO.setDateEntree(date_entree);
		
			if(!date_sortie.isBlank())	
				computerDTO.setDateSortie(date_sortie);
			
			if(!companyId.isBlank()) {
				computerDTO.setCompany(companyId);
			}
			
			
			Computer computerMaj = mappy.createComputer(computerDTO);
			
			service.majComputer(computerMaj);
			response.sendRedirect("dashboard");
		
			
			
	    }
	
	*/
	
	
}
