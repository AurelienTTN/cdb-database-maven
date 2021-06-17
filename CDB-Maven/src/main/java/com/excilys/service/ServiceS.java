package com.excilys.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.excilys.dto.ComputerDTO;
import com.excilys.exceptions.ExceptionComputerVide;


import com.excilys.mapper.Mapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.persistence.CompanyJpaDAO;
import com.excilys.persistence.ComputerJpaDAO;
import com.excilys.persistence.Dao;


@Service
public final class ServiceS {
	
		@Autowired
	    private CompanyJpaDAO companyDAO;
		@Autowired
		private ComputerJpaDAO computerDAO;
		@Autowired
	    private Mapper mappy;
	    


	   
	public List <Computer> getListComputer(){ 
		return this.computerDAO.findAll();	
	}
	
	public List <Company> getListCompany(){
		return this.companyDAO.findAll();		
	}
	
	public Computer getOneComputer(int id) throws ExceptionComputerVide {
		Computer computer = this.computerDAO.findById(id);
		if(computer == null)
			throw new ExceptionComputerVide("Aucun ordinateur n'est retourné");
		return computer;		
	} 
	
	public int getNombreTotalComputer() {  
		return (int)this.computerDAO.count();
	}
	
	public List <Computer> getElementPage(Pageable pageable) { 
		return this.computerDAO.findAll(pageable).getContent();
	}
	
	
	public Computer creerComputer(ComputerDTO computerDTO) { 
		return this.mappy.createComputer(computerDTO);
	}
	
	public void ajouterComputer(ComputerDTO computerDTO) { 
		Computer pc = creerComputer(computerDTO);
		this.computerDAO.save(pc);
	}
	
	
	public void majComputer(Computer computer) { 
		this.computerDAO.save(computer);
	}
	
	public void effacerComputer(int id) {
		this.computerDAO.deleteById(id);
	}
	
	
	public List<Computer> getListeComputerByNameOrderBy(String name,Pageable pageable) {
		return this.computerDAO.findByNameLike(name,pageable);
		}

	public int getNbElementListeSearch(String name) {
		return (int)this.computerDAO.countByNameLike(name);
	}

/*
	public void deleteCompanyById(int id) {
		this.base.deleteCompanyByID(id);
	}
*/
	
	
	
}
