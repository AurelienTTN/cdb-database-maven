package com.excilys.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import java.sql.*;

import com.excilys.model.Company;
import com.excilys.model.Computer;
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
	private ComputerRowMapper computerRowMapper;
	@Autowired 
	private CompanyRowMapper companyRowMapper;
	@Autowired
	private Mapper mappy;
	@Autowired
	private DataSource dataSource;
	
	
	private static Logger logger = LoggerFactory.getLogger("Dao");
	

	
	private static final String QUERY_COUNT = "SELECT COUNT(*) FROM computer";
	private static final String QUERY_LISTE_PAGE = "SELECT * FROM computer LEFT JOIN company on company.id = computer.company_id LIMIT :offset,:limit ;";
	private static final String QUERY_LISTE_COMPUTER = "SELECT * FROM computer LEFT JOIN company on company.id = computer.company_id;";
	private static final String QUERY_LISTE_COMPANY = "SELECT * FROM company;";
	private static final String QUERY_COMPANY_BY_ID = "SELECT * FROM company WHERE id=:id;";
	private static final String QUERY_COMPUTER_BY_ID = "SELECT * FROM computer LEFT JOIN company on company.id = computer.company_id WHERE computer.id=:id";
	private static final String QUERY_CREATE_COMPUTER = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES(:name,:introduced,:discontinued,:companyId);";
	private static final String QUERY_UPDATE_COMPUTER = "UPDATE computer "
			+ "SET name=:name,introduced=:introduced,discontinued=:discontinued,company_id=:companyId WHERE id=:id;";
	private static final String QUERY_DELETE_COMPUTER = "DELETE FROM computer WHERE id=:id";
	private static final String QUERY_SEARCH_COMPUTER_BY_NAME = "SELECT * FROM computer LEFT JOIN company on company.id = computer.company_id "
			+ "WHERE computer.name LIKE :name OR company.name LIKE :name LIMIT :limit,:offset ;";
	private static final String QUERY_SEARCH_FOR_COUNT = "SELECT * FROM computer LEFT JOIN company on company.id = computer.company_id "
			+ "WHERE computer.name LIKE :name OR company.name LIKE :name;";
	private static final String QUERY_ORDER_BY_NAME = "SELECT * FROM computer LEFT JOIN company on company.id = computer.company_id ORDER BY computer.name LIMIT :limit,:offset;";
	private static final String QUERY_ORDER_BY_INTRODUCED = "SELECT * FROM computer LEFT JOIN company on company.id = computer.company_id ORDER BY computer.introduced DESC LIMIT :limit,:offset;";
	private static final String QUERY_ORDER_BY_DISCONTINUED = "SELECT * FROM computer LEFT JOIN company on company.id = computer.company_id ORDER BY computer.discontinued DESC LIMIT :limit,:offset;";
	private static final String QUERY_ORDER_BY_COMPANY = "SELECT * FROM computer LEFT JOIN company on company.id = computer.company_id ORDER BY company.name DESC LIMIT :limit,:offset;";
	

	public int getNombreTotalOrdinateur() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		int result = jdbc.queryForObject(QUERY_COUNT, Integer.class);
		return result;
	}
	
		
	public List <Computer> listeSpecifiquesComputers(int debut,int nombre) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("offset", debut, Types.INTEGER);
		params.addValue("limit", nombre, Types.INTEGER);
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		List<Computer> computer = jdbcTemplate.query(QUERY_LISTE_PAGE, params,computerRowMapper);
		return computer;
	}	
	
	
	public List <Computer> listeComputer() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		return jdbc.query(QUERY_LISTE_COMPUTER, computerRowMapper);	
	}

	
	public List<Company> listeCompanies() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		return jdbc.query(QUERY_LISTE_COMPANY, companyRowMapper);	
	}
	
	public Company getCompanyById(int id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id, Types.INTEGER);
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		List<Company> company= jdbcTemplate.query(QUERY_COMPANY_BY_ID, params,companyRowMapper);
		if (company.size()==0)
			return null;
		return company.get(0);
	}
	
	
	public Computer oneComputer(int id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id, Types.INTEGER);
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		List<Computer> computer= jdbcTemplate.query(QUERY_COMPUTER_BY_ID, params,computerRowMapper);
		if(computer.size()==0)
			return null;
		return computer.get(0);
				
	}
	
	public void ajouterUnComputer(Computer computer) {
		
		ComputerDTO c = mappy.createComputerDTO(computer);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", c.getName(), Types.VARCHAR);
		params.addValue("introduced", c.getDateEntree(), Types.DATE);
		params.addValue("discontinued", c.getDateSortie(), Types.DATE);
		params.addValue("companyId", c.getCompany(), Types.INTEGER);

		new NamedParameterJdbcTemplate(dataSource).update(QUERY_CREATE_COMPUTER, params);
			
		
	}
	
	public void updateComputer(Computer computer) {

		ComputerDTO c = mappy.createComputerDTO(computer);

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", c.getName(), Types.VARCHAR);
		params.addValue("introduced", c.getDateEntree(), Types.DATE);
		params.addValue("discontinued", c.getDateSortie(), Types.DATE);
		params.addValue("companyId", c.getCompany(), Types.INTEGER);
		params.addValue("companyId", c.getCompany(), Types.INTEGER);
		params.addValue("id", c.getId(), Types.INTEGER);

		new NamedParameterJdbcTemplate(dataSource).update(QUERY_UPDATE_COMPUTER, params);
	}
	

	
	public void deleteComputer(int id) {
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id, Types.INTEGER);
		new NamedParameterJdbcTemplate(dataSource).update(QUERY_DELETE_COMPUTER, params);
	}

	public List<Computer> getListeComputerByName(String nameC,int indexDeb,int nombreElement) {
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name","%"+nameC+"%", Types.VARCHAR);
		params.addValue("limit", indexDeb);
		params.addValue("offset",nombreElement);
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		List<Computer> computer= jdbcTemplate.query(QUERY_SEARCH_COMPUTER_BY_NAME, params,computerRowMapper);
		if(computer.size()==0)
			return null;
		return computer;
	}

	public int getNbElementListeSearch(String nameC) {
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name","%"+nameC+"%", Types.VARCHAR);
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		List<Computer> computer= jdbcTemplate.query(QUERY_SEARCH_FOR_COUNT, params,computerRowMapper);
		return computer.size();
	}



	public List<Computer> getListeComputerOrderedByName(int deb, int nbElement) {
	
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("limit", deb, Types.INTEGER);
		params.addValue("offset", nbElement, Types.INTEGER);
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		List<Computer> computer = jdbcTemplate.query(QUERY_ORDER_BY_NAME, params,computerRowMapper);
		return computer;
	}

	public List<Computer> getListeComputerOrderedByIntroduced(int deb, int nbElement) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("limit", deb, Types.INTEGER);
		params.addValue("offset", nbElement, Types.INTEGER);
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		List<Computer> computer = jdbcTemplate.query(QUERY_ORDER_BY_INTRODUCED, params,computerRowMapper);
		return computer;
	}
	
	public List<Computer> getListeComputerOrderedByDiscontinued(int deb, int nbElement) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("limit", deb, Types.INTEGER);
		params.addValue("offset", nbElement, Types.INTEGER);
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		List<Computer> computer = jdbcTemplate.query(QUERY_ORDER_BY_DISCONTINUED, params,computerRowMapper);
		return computer;
	}
	
	public List<Computer> getListeComputerOrderedByCompany(int deb, int nbElement) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("limit", deb, Types.INTEGER);
		params.addValue("offset", nbElement, Types.INTEGER);
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		List<Computer> computer = jdbcTemplate.query(QUERY_ORDER_BY_COMPANY, params,computerRowMapper);
		return computer;
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

