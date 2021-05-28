package com.excilys.service;


import java.time.LocalDate;
import java.util.List;

import com.excilys.dto.ComputerDTO;
import com.excilys.exceptions.ExceptionComputerVide;
import com.excilys.exceptions.ExceptionListeComputerVide;

import com.excilys.mapper.Mapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.persistence.Dao;


public final class Service {
	
	    private static Service instance;
	    private Dao base;
	    private Mapper mappy;
	    

	    private Service() {

	        try {
	            Thread.sleep(1000);
	        } catch (InterruptedException ex) {
	            ex.printStackTrace();
	        }
	        
	       
					
			this.base = com.excilys.persistence.Dao.getInstance();
			this.base.connection();  
			this.mappy= Mapper.getInstance();
	    }

	    public static Service getInstance() {
	        if (instance == null) {
	            instance = new Service();
	        }
	        return instance;
	    }
	    
	    
	
	public List <Computer> getListComputer(){
		return this.base.listeComputer();	
	}
	
	
	public List <Company> getListCompany(){
		return this.base.listeCompanies();
			
	}
	
	
	public Computer getOneComputer(int id) throws ExceptionComputerVide {
		Computer computer = this.base.oneComputer(id);
		if(computer == null)
			throw new ExceptionComputerVide("Aucun ordinateur n'est retourné");
		return computer;		
	} 
	
	public int getNombreTotalComputer() {
		return this.base.getNombreTotalOrdinateur();
	}
	
	public List <Computer> getElementPage(int index_debut,int nb_element) {
		return this.base.listeSpecifiquesComputers(index_debut,nb_element);
	}
	
	
	public Computer creerComputer(ComputerDTO computerDTO) {
		return this.mappy.createComputer(computerDTO);
	}
	
	
	public void ajouterComputer(ComputerDTO computerDTO) {
		Computer pc = creerComputer(computerDTO);
		if(pc==null) {
			System.out.println("Votre PC ne respecte pas toutes les conditions nécessaires à sa création");
		}
		this.base.ajouterUnComputer(pc);
	}
	
	public void changerInfoComputer(int id, int colonne, Object value) {
		this.base.updateComputer(id, colonne, value);
	}
	
	public void effacerComputer(int id) {
		this.base.deleteComputer(id);
	}
	
	
	
}
