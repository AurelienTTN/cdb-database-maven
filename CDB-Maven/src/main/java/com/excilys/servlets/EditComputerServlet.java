package com.excilys.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.model.Company;
import com.excilys.service.Service;



@WebServlet("/editComputer")
public class EditComputerServlet extends  HttpServlet {

	
	private static final long serialVersionUID = 1L;

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
		Service service = Service.getInstance();
		List<Company> companies = service.getListCompany();
		request.setAttribute("listeCompany",companies );

		if(request.getParameter("computer")!=null) {
			request.setAttribute("computer",request.getParameter("computer"));
			System.out.println("ça marche");
		}
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/editComputer.jsp" ).forward( request, response );
		
		
	}
	

	
	
}
