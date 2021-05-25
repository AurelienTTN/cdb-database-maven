package com.excilys.controlers;
import java.util.List;

import com.excilys.model.Company;
import com.excilys.service.Service;
import com.excilys.ui.CompanyCLI;

public class CompanyCtr {
	

	private static CompanyCtr instance;
	private Service service;
	private CompanyCLI companyCLI;

	
	private CompanyCtr() {
		this.service = Service.getInstance();
		this.companyCLI = CompanyCLI.getInstance();
		
	}
	
	public static CompanyCtr getInstance() {
	       if (instance == null) {
	            instance = new CompanyCtr();
	        }
	        return instance;
	    }
	
	public void afficherListeCompagnies() {
		List<Company> companies = service.getListCompany();
		companyCLI.afficherListeCompany(companies);
	}

}
