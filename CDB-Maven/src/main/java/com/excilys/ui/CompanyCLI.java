package com.excilys.ui;

import java.util.List;

import org.springframework.stereotype.Component;

import com.excilys.model.Company;

@Component
public class CompanyCLI {
	
private static CompanyCLI instance;
	
	private CompanyCLI() {
		
	}
	
	
	public static CompanyCLI getInstance() {
        if (instance == null) {
            instance = new CompanyCLI();
        }
        return instance;
    }
	public void afficherCompany(Company computer) {
		System.out.println(computer);
	}
	
	public void afficherListeCompany(List<Company> companies) {
		for(Company c : companies) {
			this.afficherCompany(c);
		}
	}
}
