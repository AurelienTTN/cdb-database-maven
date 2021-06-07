package com.excilys.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.dto.ComputerDTO;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.ServiceS;

@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
		ServiceS service = ServiceS.getInstance();
		
		List<Company> companies = service.getListCompany();
		request.setAttribute("listeCompany",companies );

		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/addComputer.jsp" ).forward( request, response );
		
		
	}
	
	 public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		 	String name = request.getParameter( "computerName");
	        String date_entree = request.getParameter( "introduced" );
	        String date_sortie = request.getParameter( "discontinued");
	        String companyId = request.getParameter( "companyId" );
	        
	        
	        ComputerDTO computerDTO = new ComputerDTO(name,date_entree,date_sortie,companyId);
	        ServiceS service = ServiceS.getInstance();
	        service.ajouterComputer(computerDTO);
	        response.sendRedirect("dashboard?new=1");
	    }
}
