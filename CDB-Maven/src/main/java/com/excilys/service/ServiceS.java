package com.excilys.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.dto.ComputerDTO;
import com.excilys.exceptions.ExceptionComputerVide;


import com.excilys.mapper.Mapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.persistence.Dao;


@Service
public final class ServiceS {
	
		@Autowired
	    private Dao base;
		@Autowired
	    private Mapper mappy;
	    


	   
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
	
	
	
	
}
