package com.excilys.servlets;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.service.Service;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Page page = new Page();
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
		
	
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
		

		request.setAttribute("listeComputer",listeComputer );
		request.setAttribute("page",this.page);
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/dashboard.jsp" ).forward( request, response );
		
		
	}
	
	private void choisirPage(String s) {
		if(s.equals("0")) {
			System.out.println("Boucle pour aller au debut");
			this.page.setNumeroPage(1);
		}
		else {
			System.out.println("Boucle pour aller à la fin");
			int nbTotal = this.page.getNbElementTotal();
			int nbParPage = this.page.getNbLigne();
			this.page.setNumeroPage((nbTotal%nbParPage) == 0 ? nbTotal/nbParPage : nbTotal/nbParPage +1);
		
		}
	}
}