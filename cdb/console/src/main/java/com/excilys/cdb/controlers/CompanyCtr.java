package com.excilys.controlers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.excilys.model.Company;
import com.excilys.service.ServiceS;
import com.excilys.ui.CompanyCLI;

@Controller
public class CompanyCtr {
	

	@Autowired
	private ServiceS service;
	@Autowired
	private CompanyCLI companyCLI;


	
	public void afficherListeCompagnies() {
		List<Company> companies = service.getListCompany();
		companyCLI.afficherListeCompany(companies);
	}

}
