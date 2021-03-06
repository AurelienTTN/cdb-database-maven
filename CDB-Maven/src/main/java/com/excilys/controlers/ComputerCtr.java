package com.excilys.controlers;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.exceptions.ExceptionListeComputerVide;
import com.excilys.mapper.Mapper;
import com.excilys.dto.ComputerDTO;
import com.excilys.exceptions.ExceptionComputerVide;
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.service.Service;
import com.excilys.ui.ChoixUtilisateur;
import com.excilys.ui.ComputerCLI;

public class ComputerCtr {
	

	private static ComputerCtr instance;
	private Service service;
	private ComputerCLI computerCLI;
	private ChoixUtilisateur choixUtilisateur;
	private static Logger logger = LoggerFactory.getLogger("ComputerCtr");
	private Mapper mappy;

	
	private ComputerCtr() {
		this.service = Service.getInstance();
		this.computerCLI = ComputerCLI.getInstance();
		this.choixUtilisateur = ChoixUtilisateur.getInstance();
		this.mappy = Mapper.getInstance();
		
	}
	
	public static ComputerCtr getInstance() {
	       if (instance == null) {
	            instance = new ComputerCtr();
	        }
	        return instance;
	    }
	
	public void afficherListeComputers(){
			List<Computer> computers= this.service.getListComputer();
			List<ComputerDTO> computersDTO = new ArrayList<>();
			for(Computer c: computers)
				computersDTO.add(this.mappy.createComputerDTO(c));
			this.computerCLI.afficherListeComputers(computersDTO);
	}
		
	
	public void afficherUnOrdinateur() {
		
		int id = choixUtilisateur.choixOrdinateur();
	try {
		Computer computer = this.service.getOneComputer(id);
		ComputerDTO computerDTO = this.mappy.createComputerDTO(computer);
		this.computerCLI.afficherComputer(computerDTO);
	}
	catch(ExceptionComputerVide e) {
		logger.info(e.toString());
	}
	
	}
	
	public void ajouterUnOrdinateur() {
		String name = choixUtilisateur.choixNom();
		String entree = choixUtilisateur.choixDateEntree();
		String out = choixUtilisateur.choixDateSortie();
		String id_company = choixUtilisateur.choixIDCompany();
		ComputerDTO computerDTO = new ComputerDTO(name,entree,out,id_company);
		System.out.println(computerDTO);
		this.service.ajouterComputer(computerDTO);
	}
	
	public void majOrdinateur() throws ExceptionComputerVide {
		
		int id = choixUtilisateur.choixOrdinateur();
		Computer computer = service.getOneComputer(id);
		Mapper mappy = Mapper.getInstance();
		ComputerDTO computerDTO = mappy.createComputerDTO(computer);
		System.out.println(computer);
		System.out.println(computerDTO);
		
		String name = choixUtilisateur.choixNom();
		if(!name.isBlank()) 
			computerDTO.setName(name);
	
		String date_entry = choixUtilisateur.choixDateEntree();
		if(!date_entry.isBlank()) 
			computerDTO.setDateEntree(date_entry);
	
		String date_out = choixUtilisateur.choixDateSortie();
		if(!date_out.isBlank())	
			computerDTO.setDateSortie(date_out);
		
		String id_comp = choixUtilisateur.choixIDCompany();
		if(!id_comp.isBlank()) {
			computerDTO.setCompany(id_comp);
		}
		
		Computer computerMaj = mappy.createComputer(computerDTO);
		service.majComputer(computerMaj);
		
		
		
	}
	
	public void effacerOrdinateur() {
		int id = choixUtilisateur.choixOrdinateur();
		service.effacerComputer(id);
	}
	
	public void afficherOrdinateurParPages() {
		int max_ordinateur = this.service.getNombreTotalComputer();
		Page page = new Page();
		page.setNbElementTotal(max_ordinateur);
		int choix = 1;
		List<Computer> computers= this.service.getElementPage(0,page.getNbLigne());
		
		List<ComputerDTO> computersDTO = new ArrayList<>();
		for(Computer c: computers)
			computersDTO.add(this.mappy.createComputerDTO(c));
		
		this.computerCLI.afficherListeComputers(computersDTO);
		
		while(choix!=0) {
			choix = choixUtilisateur.choixMenuPage();
			switch(choix) {
			case 1 :{
				if(page.getNumeroPage()!=1)
					page.setNumeroPage(page.getNumeroPage()-1);
					break;
			}
			case 2 :{
				if(page.getNumeroPage()!=(page.getNbElementTotal()/page.getNbLigne()+1)) 
					page.setNumeroPage(page.getNumeroPage()+1);
				break;
			}
			case 3 :{
				int num = choixUtilisateur.choixPageSpecifique();
				page.setNumeroPage(num);	
				break;
			}
			}
			if(choix!=0) {
				int index_debut = page.getNumeroPage()*page.getNbLigne()-page.getNbLigne()-1;
				computers= this.service.getElementPage(index_debut,page.getNbLigne());
				List<ComputerDTO> computersD = new ArrayList<>();
				for(Computer c: computers)
					computersD.add(this.mappy.createComputerDTO(c));
				this.computerCLI.afficherListeComputers(computersD);
			}
	}
	}

	public void deleteCompagnie() {
		int num = choixUtilisateur.choixCompagnie();
		service.deleteCompanyById(num);
		
	}
}
	

