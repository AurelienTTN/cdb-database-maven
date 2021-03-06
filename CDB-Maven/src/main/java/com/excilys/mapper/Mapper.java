package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.ComputerDTO;
import com.excilys.exceptions.ValidationException;
import com.excilys.model.*;
import com.excilys.persistence.Dao;
import com.excilys.service.Service;
import com.excilys.ui.CompanyCLI;
import com.excilys.validator.Validator;

public class Mapper {
	
	private static Logger logger = LoggerFactory.getLogger("Mapper");
	private static Mapper instance;
	private Validator validator;
	
	public Mapper() {
		this.validator = Validator.getInstance();
	}
	
	public static Mapper getInstance() {
        if (instance == null) {
            instance = new Mapper();
        }
        return instance;
    }

	
	public List<Computer> dataToListComputer(ResultSet data) throws SQLException{
		
		List<Computer> computers = new ArrayList<Computer>();
		
		while(data.next()) {
			computers.add(this.dataToComputer(data));
		}
		return computers;
	}
	
	
	public Computer dataToComputer(ResultSet results) throws SQLException{
		int id1;
		String name;
		LocalDate date=null;
		LocalDate date2=null;
		Company company=null;
		
		id1 = results.getInt(1);
		name = results.getString(2);
		if (results.getDate(3) != null) {
			date = results.getDate(3).toLocalDate();
		}
		if (results.getDate(4) != null) {
			date2 = results.getDate(4).toLocalDate();
		}
		if(results.getString(5)!=null) {
			company = new Company(results.getInt(5),results.getString(7));
		}
		Computer computer = new Computer(id1,name,date,date2,company);
		return computer;
	}	
	
	
	public List<Company> dataToListCompany(ResultSet data) throws SQLException{
		
		List<Company> Companies = new ArrayList<Company>();
		int i=0;
		while(data.next()) {
			Companies.add(dataToCompany(data));
			
		}
		return Companies;	
	}
	
	public Company dataToCompany(ResultSet data) throws SQLException{
		int id = data.getInt(1);
		String name = data.getString(2);
		return new Company(id,name);
	}
	
	public ComputerDTO createComputerDTO(Computer computer) {
		String id = null;
		String name = null;
		String date_entry = null;
		String date_out = null;
		String company = null;
		
		if(computer.getId()!=0)
			id = Integer.toString(computer.getId());
		
		if(computer.getName()!=null)
			name = computer.getName();
		
		if(computer.getDateEntree()!=null)
			date_entry = computer.getDateEntree().toString();
		
		if(computer.getDateSortie()!=null)
			date_out = computer.getDateSortie().toString();
		
		if(computer.getCompany()!=null)
			company = Integer.toString(computer.getCompany().getId());
		
		return new ComputerDTO(id,name,date_entry,date_out,company);
	}
			
		
	
	public Computer createComputer(ComputerDTO computerDTO) {
	
		try {
			validator.validatorDTO(computerDTO);
		}
		catch(ValidationException e) {
			logger.error("Impossible de cr??er un pc : " + e.toString());
		}
		
		Computer computer = new Computer();
		
		if(computerDTO.getId()!=null) {
			computer.setId(Integer.parseInt(computerDTO.getId()));
		}
		
		computer.setName(computerDTO.getName());
		
		if((computerDTO.getDateEntree()!=null)&&(!computerDTO.getDateEntree().isBlank()))
			computer.setDateEntree(LocalDate.parse(computerDTO.getDateEntree()));
		else 
			computer.setDateEntree(null);
		
		if((computerDTO.getDateSortie()!=null)&&(!computerDTO.getDateSortie().isBlank()))
			computer.setDateSortie(LocalDate.parse(computerDTO.getDateSortie()));
		else 
			computer.setDateSortie(null);
		
		if((computerDTO.getCompany()!=null)&&(!computerDTO.getCompany().isBlank()))
			computer.setCompany(Dao.getInstance().getCompanyById(Integer.parseInt(computerDTO.getCompany())));
		else
			computer.setCompany(null);
		
		System.out.println(computer);
		
		return computer;	
	}
		

}