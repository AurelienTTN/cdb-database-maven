package com.excilys.controlers;


import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.exceptions.ExceptionListeComputerVide;
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

	
	private ComputerCtr() {
		this.service = Service.getInstance();
		this.computerCLI = ComputerCLI.getInstance();
		this.choixUtilisateur = ChoixUtilisateur.getInstance();
		
	}
	
	public static ComputerCtr getInstance() {
	       if (instance == null) {
	            instance = new ComputerCtr();
	        }
	        return instance;
	    }
	
	public void afficherListeComputers(){
			List<Computer> computers= this.service.getListComputer();
			this.computerCLI.afficherListeComputers(computers);
	}
		
	
	public void afficherUnOrdinateur() {
		
		int id = choixUtilisateur.choixOrdinateur();
	try {
		Computer computer = this.service.getOneComputer(id);
		this.computerCLI.afficherComputer(computer);
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
	
	public void majOrdinateur() {
		int id = choixUtilisateur.choixOrdinateur();
		
		int colonne = choixUtilisateur.choixChampAChanger();
		switch(colonne){
			case 2:{
				String name = choixUtilisateur.choixNom();
				service.changerInfoComputer(id, colonne, name);
				break;
			}
			case 3:{
				LocalDate date_entry = choixUtilisateur.choixDateEntree();
				service.changerInfoComputer(id, colonne, date_entry);
				break;
			}
			case 4:{
				LocalDate date_out = choixUtilisateur.choixDateSortie();
				service.changerInfoComputer(id, colonne, date_out);
				break;
			}
			case 5:{
				int id_comp = choixUtilisateur.choixIDCompany();
				service.changerInfoComputer(id, colonne, id_comp);
				break;
			}
		}	
	}
	
	public void effacerOrdinateur() {
		int id = choixUtilisateur.choixOrdinateur();
		service.effacerComputer(id);
	}
	
	public void afficherOrdinateurParPages() {
		int max_ordinateur = this.service.getNombreTotalComputer();
		Page page = new Page(max_ordinateur);
		int choix = 1;
		List<Computer> computers= this.service.getElementPage(1,page.getNBElementParPage());
		this.computerCLI.afficherListeComputers(computers);
		
		while(choix!=0) {
			choix = choixUtilisateur.choixMenuPage();
			switch(choix) {
			case 1 :{
				if(page.getNumeroPage()!=1)
					page.getPagePrecedente();
					break;
			}
			case 2 :{
				if(page.getNumeroPage()!=(page.getMaxElement()/page.getNBElementParPage()+1)) 
					page.getPageSuivante();
				break;
			}
			case 3 :{
				int num = choixUtilisateur.choixPageSpecifique();
				page.setNumPage(num);	
				break;
			}
			}
			if(choix!=0) {
				int index_debut = page.getNumeroPage()*page.getNBElementParPage()-page.getNBElementParPage()-1;
				computers= this.service.getElementPage(index_debut,page.getNBElementParPage());
				this.computerCLI.afficherListeComputers(computers);
			}
	}
	}
}
	

