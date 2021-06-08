package com.excilys.persistence;

import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.ServiceS;
import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.*;

@Repository
public class Dao {
	// Properties
	private String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db";
	private String name = "admincdb";
	private String passwd = "qwerty1234";
	
	private String urlTest  = "jdbc:h2:mem:test-db";
	private String nameTest = "sa";
	private String passwordTest="";
	
	private Connection con = null;
	@Autowired
	private Mapper mappy;
	
	
	private static Logger logger = LoggerFactory.getLogger("Dao");
	

	private static final String AJOUT_ONE_COMPUTER = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES(?,?,?,?);";
	private static final String UPDATE_COMPUTER = "UPDATE computer "
			+ "SET name=?,introduced=?,discontinued=?,company_id=? WHERE id=?;";
	private static final String SEARCH_COMPUTER_BY_NAME = "SELECT * FROM computer LEFT JOIN company on company.id = computer.company_id "
			+ "WHERE computer.name LIKE ? OR company.name LIKE ? LIMIT ?,? ;";
	private static final String SEARCH_BY_NAME_FOR_COUNT = "SELECT * FROM computer LEFT JOIN company on company.id = computer.company_id "
			+ "WHERE computer.name LIKE ? OR company.name LIKE ? ;";
	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id=?";
	private static final String DELETE_COMPUTER_COMPANY_ID = "DELETE FROM computer WHERE company_id=?;";
	private static final String DELETE_COMPANY = "DELETE FROM company WHERE id=?;";
	
	
	public void connection() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		    this.con = DriverManager.getConnection( this.url, this.name, this.passwd );
		  
		} catch ( SQLException e ) {
			logger.error("Connection à la base impossible",e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	     
	}
	
	public void connectionForTest() {
			
			/* */
			try {
				 Class.forName("org.h2.Driver");
			    this.con = DriverManager.getConnection( this.urlTest, this.nameTest, this.passwordTest );
			  
			} catch ( SQLException e ) {
				logger.error("Connection à la base impossible",e);
			} catch (ClassNotFoundException e) {
				logger.error("Connexion impossible", e);
			}
			     
		}
	
	
	
	public int getNombreTotalOrdinateur() {
		String query = "SELECT COUNT(*) as numero FROM computer;";
		ResultSet results = null;
		int count=0;
		try(Connection con = DataJDBCConnection.getConnection())
		{
			Statement stmt = con.createStatement();
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
		
		try(Connection con = DataJDBCConnection.getConnection()){
			Statement stmt = con.createStatement();
			results = stmt.executeQuery(query);
			computers = this.mappy.dataToListComputer(results);
		}catch(Exception e) {
			logger.error("Problème lié à la requête SQL",e);
		}
			
		return computers;
	}
	
	
	public List <Computer> listeComputer() {
		String query = "SELECT * FROM computer LEFT JOIN company on company.id = computer.company_id;";
		ResultSet results=null;
		List<Computer> computers = new ArrayList<Computer>();
		try (Connection con = DataJDBCConnection.getConnection()){
			Statement stmt = con.createStatement();
			results = stmt.executeQuery(query);
			computers = this.mappy.dataToListComputer(results);
		}catch(Exception e) {
			logger.error("Problème lié à la requête SQL",e);
		}
			
		return computers;
	}

	
	public List<Company> listeCompanies() {
		String query = "SELECT * FROM company;";
		ResultSet results=null;
		List<Company> companies = new ArrayList<Company>();
		
		try(Connection con = DataJDBCConnection.getConnection()){
			Statement stmt = con.createStatement();
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
		
		try(Connection con = DataJDBCConnection.getConnection()){
			Statement stmt = con.createStatement();
			results = stmt.executeQuery(query);
			results.next();
			company = this.mappy.dataToCompany(results);
		}catch(Exception e) {
			logger.error("Problème lié à la requête SQL",e);
		}
			
		return company;
	}
	
	
	public Computer oneComputer(int id) {
		String query = "SELECT * FROM computer LEFT JOIN company on company.id = computer.company_id WHERE computer.id="+id+";";
		ResultSet results=null;
		Computer computer = null;

		try(Connection con = DataJDBCConnection.getConnection()){
			Statement stmt = con.createStatement();
			results = stmt.executeQuery(query);
			results.next();
			computer = this.mappy.dataToComputer(results);
		}
		catch(Exception e) {
			logger.error("Problème lié à la requête SQL",e);
		}
		return computer;
				
	}
	
	public void ajouterUnComputer(Computer c) {
		
		ComputerDTO computerDTO = mappy.createComputerDTO(c);
		
		String name = c.getName();
		LocalDate date_entree_pc = c.getDateEntree();
		LocalDate date_sortie_pc=c.getDateSortie();
		Company company = c.getCompany();
		try(Connection con = DataJDBCConnection.getConnection()){
			PreparedStatement ps = con.prepareStatement(AJOUT_ONE_COMPUTER);
			
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
				ps.setInt(4, company.getId());
			else
				ps.setNull(4,Types.NULL);
			
			ps.executeUpdate();
			
		}
		catch(Exception e) {
			logger.error("Problème lié à la requête SQL",e);
		}
	}
	
	
	
	public void updateComputer(Computer c) {
		int id = c.getId();
		String name = c.getName();
		LocalDate date_entree_pc = c.getDateEntree();
		LocalDate date_sortie_pc=c.getDateSortie();
		Company company = c.getCompany();
		try(Connection con = DataJDBCConnection.getConnection()){
			PreparedStatement ps = con.prepareStatement(UPDATE_COMPUTER);
			
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
				ps.setInt(4, company.getId());
			else
				ps.setNull(4,Types.NULL);
			
			ps.setInt(5,id) ;
			
			
			ps.executeUpdate();
			
		}
		catch(Exception e) {
			logger.error("Problème lié à la requête SQL",e);
		}
	}
	
	
	public void deleteComputer(int id) {
		
		try(Connection con = DataJDBCConnection.getConnection()){
			PreparedStatement ps = con.prepareStatement(DELETE_COMPUTER);
			ps.setInt(1,id);
			ps.executeUpdate();
		}
		catch(Exception e){
			logger.error("Problème lié à la requête SQL",e);
		}
		 
	}

	public List<Computer> getListeComputerByName(String nameC,int indexDeb,int nombreElement) {
		
		
		ResultSet results=null;
		List<Computer> computers = new ArrayList<Computer>();
		
		try (Connection con = DataJDBCConnection.getConnection()){
			PreparedStatement ps = con.prepareStatement(SEARCH_COMPUTER_BY_NAME);
			ps.setString(1,"%"+nameC+"%");
			ps.setString(2,"%"+nameC+"%");
			ps.setInt(3,indexDeb);
			ps.setInt(4, nombreElement);
			
			results = ps.executeQuery();
			computers = this.mappy.dataToListComputer(results);
		}catch(Exception e) {
			logger.error("Problème lié à la requête SQL",e);
		}
			
		return computers;
	}

	public int getNbElementListeSearch(String nameC) {
		
		ResultSet results=null;
		List<Computer> computers = new ArrayList<Computer>();
		
		try (Connection con = DataJDBCConnection.getConnection()){
			PreparedStatement ps = con.prepareStatement(SEARCH_BY_NAME_FOR_COUNT);
			ps.setString(1,"%"+nameC+"%");
			ps.setString(2,"%"+nameC+"%");
			results = ps.executeQuery();
			computers = this.mappy.dataToListComputer(results);
		}catch(Exception e) {
			logger.error("Problème lié à la requête SQL",e);
		}
			
		return computers.size();
	}

	public List<Computer> getListeComputerOrderedByName(int deb, int nbElement) {
		String query = "SELECT * FROM computer LEFT JOIN company on company.id = computer.company_id ORDER BY computer.name LIMIT "+deb+","+nbElement+";";
		ResultSet results=null;
		List<Computer> computers = new ArrayList<Computer>();
		try (Connection con = DataJDBCConnection.getConnection()){
			Statement stmt = con.createStatement();
			results = stmt.executeQuery(query);
			computers = this.mappy.dataToListComputer(results);
			System.out.println(query);
		}catch(Exception e) {
			logger.error("Problème lié à la requête SQL",e);
		}
			
		return computers;
	}

	public List<Computer> getListeComputerOrderedByIntroduced(int deb, int nbElement) {
		String query = "SELECT * FROM computer LEFT JOIN company on company.id = computer.company_id ORDER BY computer.introduced DESC LIMIT "+deb+","+nbElement+";";
		ResultSet results=null;
		List<Computer> computers = new ArrayList<Computer>();
		try (Connection con = DataJDBCConnection.getConnection()){
			Statement stmt = con.createStatement();
			results = stmt.executeQuery(query);
			computers = this.mappy.dataToListComputer(results);
		}catch(Exception e) {
			logger.error("Problème lié à la requête SQL",e);
		}
			
		return computers;
	}
	
	public List<Computer> getListeComputerOrderedByDiscontinued(int deb, int nbElement) {
		String query = "SELECT * FROM computer LEFT JOIN company on company.id = computer.company_id ORDER BY computer.discontinued DESC LIMIT "+deb+","+nbElement+";";
		ResultSet results=null;
		List<Computer> computers = new ArrayList<Computer>();
		try (Connection con = DataJDBCConnection.getConnection()){
			Statement stmt = con.createStatement();
			results = stmt.executeQuery(query);
			computers = this.mappy.dataToListComputer(results);
		}catch(Exception e) {
			logger.error("Problème lié à la requête SQL",e);
		}
			
		return computers;
	}
	
	public List<Computer> getListeComputerOrderedByCompany(int deb, int nbElement) {
		String query = "SELECT * FROM computer LEFT JOIN company on company.id = computer.company_id ORDER BY company.name LIMIT "+deb+","+nbElement+";";
		ResultSet results=null;
		List<Computer> computers = new ArrayList<Computer>();
		try (Connection con = DataJDBCConnection.getConnection()){
			Statement stmt = con.createStatement();
			results = stmt.executeQuery(query);
			computers = this.mappy.dataToListComputer(results);
		}catch(Exception e) {
			logger.error("Problème lié à la requête SQL",e);
		}
			
		return computers;
	}
	
	public List<Computer> getListComputerOrdered(int deb , int nbElement,String orderBy, String search){
		String query = "SELECT * FROM computer LEFT JOIN company on company.id = computer.company_id "
				+ "WHERE computer.name LIKE ? OR company.name LIKE ? ORDER BY ? LIMIT ?,? ;";
		ResultSet results=null;
		List<Computer> computers = new ArrayList<Computer>();
		try (Connection con = DataJDBCConnection.getConnection()){
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1,"%"+search+"%");
			stmt.setString(2,"%"+search+"%");
			stmt.setString(3, "computer."+orderBy);
			stmt.setInt(4, deb);
			stmt.setInt(5, nbElement);
			System.out.println(stmt);
			results = stmt.executeQuery();
			computers = this.mappy.dataToListComputer(results);
		}catch(Exception e) {
			logger.error("Problème lié à la requête SQL",e);
		}
			
		System.out.println("taille computer ="+computers.size());
		return computers;
	}
	
	

	public void deleteCompanyByID(int id) {
		
		
		try {
			Connection con = DataJDBCConnection.getConnection();
			PreparedStatement deleteComputer = con.prepareStatement(DELETE_COMPUTER_COMPANY_ID);
			PreparedStatement deleteCompany = con.prepareStatement(DELETE_COMPANY);
			
			con.setAutoCommit(false);
			deleteComputer.setInt(1,id);
			deleteCompany.setInt(1, id);
			
			deleteComputer.executeUpdate();
			deleteCompany.executeUpdate();
			
			con.commit();
			
		}catch(SQLException e) {
			logger.error("Problème lié à la requête SQL",e);
			try {
		          con.rollback();
		        } catch (SQLException excep) {
		        }
		}
			

	}		
		
}

