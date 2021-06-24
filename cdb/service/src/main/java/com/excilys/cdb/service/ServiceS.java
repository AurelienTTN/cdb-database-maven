package com.excilys.cdb.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.excilys.cdb.daos.CompanyJpaDAO;
import com.excilys.cdb.daos.ComputerJpaDAO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exceptions.ExceptionComputerVide;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;



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
