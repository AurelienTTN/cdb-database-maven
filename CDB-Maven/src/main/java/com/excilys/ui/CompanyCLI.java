package com.excilys.ui;

import java.util.List;

import org.springframework.stereotype.Component;

import com.excilys.model.Company;

@Component
public class CompanyCLI {
	

	public void afficherCompany(Company computer) {
		System.out.println(computer);
	}
	
	public void afficherListeCompany(List<Company> companies) {
		for(Company c : companies) {
			this.afficherCompany(c);
		}
	}
}
