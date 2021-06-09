package com.excilys.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.dto.ComputerDTO;
import com.excilys.exceptions.ExceptionIncoherenceDates;
import com.excilys.exceptions.FormatDateException;
import com.excilys.exceptions.NameComputerEmpty;
import com.excilys.exceptions.ValidationException;
import com.excilys.exceptions.IDCompanyInexistant;
import com.excilys.persistence.Dao;

@Component
public class Validator {
	
	@Autowired
	private Dao dao;
	

	public void validatorDTO (ComputerDTO computerDTO) throws ValidationException {
		
		
		if(computerDTO.getName().isBlank()) {
			throw new NameComputerEmpty("L'ordinateur n'a pas de nom indiqué");
		}
			
		String date_entry = computerDTO.getDateEntree();
		
		if(date_entry!=null) {
			if(!date_entry.isBlank()) {
				try {
					LocalDate test = LocalDate.parse(date_entry);
				}
				catch(DateTimeParseException e) {
					throw new FormatDateException("La Date d'entrée a un format invalide");
				}
			}
		}
		
		
		String date_out = computerDTO.getDateSortie();
		System.out.println(date_out);
		if(date_out!=null) {
			if(!date_out.isBlank()) {
				try {
					LocalDate test = LocalDate.parse(date_out);
				}
				catch(DateTimeParseException e) {
					throw new FormatDateException("La Date de sortie a un format invalide");
				}
			}
		}
		
		if((date_entry!=null)&&(date_out!=null)) {
			if((!date_entry.isBlank()) && (!date_out.isBlank())) {
				if(LocalDate.parse(date_out).isBefore(LocalDate.parse(date_entry)))
					throw new ExceptionIncoherenceDates("La date de sortie est avant la date d'entrée");
			}
		}
		
		
		String id = computerDTO.getCompany();
		
		if(id!=null) {
			if(!id.isBlank()) {
				int id_comp = Integer.parseInt(id);
				if(dao.getCompanyById(id_comp)==null) {
					throw new IDCompanyInexistant("L'id"+ id_comp + "n'existe pas");
				}
			}
		}
	}
}
