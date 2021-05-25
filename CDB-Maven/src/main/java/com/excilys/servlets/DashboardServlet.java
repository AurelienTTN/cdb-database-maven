package com.excilys.servlets;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.model.Computer;
import com.excilys.service.Service;

@WebServlet("/toto")
public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
		Service service = Service.getInstance();
		List<Computer> listeComputer= service.getListComputer();
	

		request.setAttribute("listeComputer",listeComputer );
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/Dashboard.jsp" ).forward( request, response );
	}
}
