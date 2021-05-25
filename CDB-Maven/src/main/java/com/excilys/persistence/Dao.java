package com.excilys.persistence;

import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.Service;
import com.excilys.mapper.*;

public class Dao {
	// Properties
	private String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db";
	private String name = "admincdb";
	private String passwd = "qwerty1234";
	private Connection con = null;
	private Mapper mappy;
	private static Dao instance;
	
	
	private static Logger logger = LoggerFactory.getLogger("Dao");
	
	// Query
	private static final String AJOUT_ONE_COMPUTER = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES(?,?,?,?);";
	private static final String UPDATE_COMPUTER_NAME = "UPDATE computer SET name=? WHERE id=?";
	private static final String UPDATE_COMPUTER_INTRODUCED = "UPDATE computer SET introduced=? WHERE id=?";
	private static final String UPDATE_COMPUTER_DISCONTINUED = "UPDATE computer SET discontinued=? WHERE id=?";
	private static final String UPDATE_COMPUTER_COMPANY_ID = "UPDATE computer SET company_id=? WHERE id=?";
	
	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id=?";

	
	
	// Constructor
	private Dao() {
		this.mappy=new Mapper();
		
	}
	
	public static Dao getInstance() {
        if (instance == null) {
            instance = new Dao();
        }
        return instance;
    }

	
	public void connection() {
		
		/* On essaye de se connecter à notre base de donnée grâce aux informations passées au construteur*/
		try {
			Class.forName("com.mysql.jdbc.Driver");
		    this.con = DriverManager.getConnection( this.url, this.name, this.passwd );
		  
		} catch ( SQLException e ) {
			logger.error("Connection à la base impossible",e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		     
	}
	
	public int getNombreTotalOrdinateur() {
		String query = "SELECT COUNT(*) as numero FROM computer;";
		ResultSet results = null;
		int count=0;
		try {
			Statement stmt = this.con.createStatement();
			results = stmt.executeQuery(query);
			results.next();
			count = results.getInt("numero");
		}catch(Exception e) {
			logger.error("Problème lié à la requête SQL",e);
		}

		return count;
	}
	
	
		
	public List <Computer> listeSpecifiquesComputers(int debut,int nombre) {
		String query = "SELECT * FROM computer LEFT JOIN company on company.id = computer.company_id LIMIT "+debut+","+nombre+";";
		ResultSet results=null;
		List<Computer> computers = new ArrayList<Computer>();
		
		try {
			Statement stmt = this.con.createStatement();
			results = stmt.executeQuery(query);
			computers = this.mappy.dataToListComputer(results);
		}catch(Exception e) {
			logger.error("Problème lié à la requête SQL",e);
		}
			
		return computers;
	}
	
	//Renvoie la liste des PC
	
	public List <Computer> listeComputer() {
		String query = "SELECT * FROM computer LEFT JOIN company on company.id = computer.company_id;";
		ResultSet results=null;
		List<Computer> computers = new ArrayList<Computer>();
		try {
			Statement stmt = this.con.createStatement();
			results = stmt.executeQuery(query);
			computers = this.mappy.dataToListComputer(results);
		}catch(Exception e) {
			logger.error("Problème lié à la requête SQL",e);
		}
			
		return computers;
	}
	
	//Renvoie la liste des compagnies
	
	public List<Company> listeCompanies() {
		String query = "SELECT * FROM company;";
		ResultSet results=null;
		List<Company> companies = new ArrayList<Company>();
		
		try {
			Statement stmt = this.con.createStatement();
			results = stmt.executeQuery(query);
			companies = this.mappy.dataToListCompany(results);
		}catch(Exception e) {
			logger.error("Problème lié à la requête SQL",e);
		}
			
		return companies;
	}
	
	public Company getCompanyById(int id) {
		String query = "SELECT * FROM company WHERE id="+id+";";
		ResultSet results=null;
		Company company=null;;
		
		try {
			Statement stmt = this.con.createStatement();
			results = stmt.executeQuery(query);
			results.next();
			company = this.mappy.dataToCompany(results);
		}catch(Exception e) {
			logger.error("Problème lié à la requête SQL",e);
		}
			
		return company;
	}
	
	//renvoie un seul pc en fonction de son ID
	
	public Computer oneComputer(int id) {
		String query = "SELECT * FROM computer WHERE id="+id+";";
		ResultSet results=null;
		Computer computer = null;

		try {
			Statement stmt = this.con.createStatement();
			results = stmt.executeQuery(query);
			computer = this.mappy.dataToComputer(results);
		}
		catch(Exception e) {
			logger.error("Problème lié à la requête SQL",e);
		}
		return computer;
				
	}
	
	//Ajoute un pc en fonction des données rentrées par l'utilisateur
	public void ajouterUnComputer(Computer c) {
		
		String name = c.getName();
		LocalDate date_entree_pc = c.getDateEntree();
		LocalDate date_sortie_pc=c.getDateSortie();
		Company company = c.getCompany();
		try {
			PreparedStatement ps = this.con.prepareStatement(AJOUT_ONE_COMPUTER);
			
			ps.setString(1,name);
			
			if(date_entree_pc!=null)
				ps.setDate(2, Date.valueOf(date_entree_pc));
			else
				ps.setDate(2, null);
			
			
			if(date_sortie_pc!=null)
				ps.setDate(3, Date.valueOf(date_sortie_pc));
			else
				ps.setDate(3, null);
			
			if(company!=null)
				ps.setString(4, company.getName());
			else
				ps.setString(4,null);
			
			ps.executeUpdate();
			
		}
		catch(Exception e) {
			logger.error("Problème lié à la requête SQL",e);
		}
	}
	
	
	// Mets à jour le champ spécifié par l'utilisateur
	
	public void updateComputer(int id, int colonne, Object value) {
		
		
		switch(colonne) {
		
		// Si l'utilisateur veut changer le nom
		case 2:{
			try {
				PreparedStatement ps = this.con.prepareStatement(UPDATE_COMPUTER_NAME);
				ps.setString(1, (String)value);
				ps.setInt(2,id);
				ps.executeUpdate();
				
			}
			catch(Exception e){
				logger.error("Problème lié à la requête SQL",e);
			}
			break;
		}
		case 3:{
			try {
				PreparedStatement ps = this.con.prepareStatement(UPDATE_COMPUTER_INTRODUCED);
				ps.setDate(1, Date.valueOf((LocalDate)value));
				ps.setInt(2,id);
				ps.executeUpdate();
				
			}
			catch(Exception e){
				logger.error("Problème lié à la requête SQL",e);
			}
			break;
		}
		case 4:{
			try {
				PreparedStatement ps = this.con.prepareStatement(UPDATE_COMPUTER_DISCONTINUED);
				ps.setDate(1, Date.valueOf((LocalDate)value));
				ps.setInt(2,id);
				ps.executeUpdate();
				
			}
			catch(Exception e){
				logger.error("Problème lié à la requête SQL",e);
			}
			break;
		}
		case 5:{
			try {
				PreparedStatement ps = this.con.prepareStatement(UPDATE_COMPUTER_COMPANY_ID);
				ps.setInt(1, (Integer)value);
				ps.setInt(2,id);
				ps.executeUpdate();
				
			}
			catch(Exception e){
				logger.error("Problème lié à la requête SQL",e);
			}
			break;
		}	
		}
	}
	
	// efface un pc à l'ID correspondand
	public void deleteComputer(int id) {
		
		try {
			PreparedStatement ps = this.con.prepareStatement(DELETE_COMPUTER);
			ps.setInt(1,id);
			ps.executeUpdate();
		}
		catch(Exception e){
			logger.error("Problème lié à la requête SQL",e);
		}
		 
	}
		
			
		
}
