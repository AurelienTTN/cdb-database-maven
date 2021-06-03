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
		this.base.ajouterUnComputer(pc);
	}
	
	public void majComputer(Computer computer) {
		this.base.updateComputer(computer);
	}
	
	public void effacerComputer(int id) {
		this.base.deleteComputer(id);
	}

	public List<Computer> getListeComputerByName(String name,int index_deb,int nombre_max) {
		return this.base.getListeComputerByName(name,index_deb,nombre_max);

	}

	public int getNbElementListeSearch(String name) {
		return this.base.getNbElementListeSearch(name);
	}

	public List<Computer> getListeComputerOrderedByName(int deb, int nbElement) {
		return this.base.getListeComputerOrderedByName(deb,nbElement);
		
	}

	public List<Computer> getListeComputerOrderedByIntroduced(int deb, int nbElement) {
		return this.base.getListeComputerOrderedByIntroduced(deb,nbElement);
	}
	
	public List<Computer> getListeComputerOrderedByDiscontinued(int deb, int nbElement) {
		return this.base.getListeComputerOrderedByDiscontinued(deb,nbElement);
	}
	
	public List<Computer> getListeComputerOrderedByCompany(int deb, int nbElement) {
		return this.base.getListeComputerOrderedByCompany(deb,nbElement);
	}

	public void deleteCompanyById(int id) {
		this.base.deleteCompanyByID(id);
	}
	
	public List<Computer> getListeOrdered(int deb,int index_fin,String orderBy,String search){
		return this.base.getListComputerOrdered(deb, index_fin, orderBy, search);
	}
	
	
	
}
