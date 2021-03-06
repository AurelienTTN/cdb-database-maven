package com.excilys.daoTest;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.jdbc.ScriptRunner;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.persistence.Dao;

class ComputerDaoTest {
	
	private static Dao daoTest;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		daoTest = Dao.getInstance();
		daoTest.connectionForTest();
	}


	@BeforeEach
	void setUp() throws Exception {
		ScriptRunner sr = new ScriptRunner(daoTest.getConnection());
		Reader reader = new BufferedReader(new FileReader("/home/excilys/Bureau/Maven/CDB-Maven/src/test/resources/test-db.sql"));
		sr.runScript(reader);
	}

	

	@Test
	void testListeComputer() {
		List<Computer> computers = daoTest.listeComputer();
		assertEquals(30,computers.size());
	}
	
	@Test
	void testGetComputerByID() {
		Computer computer = daoTest.oneComputer(29);
		assertEquals("PowerBook",computer.getName());
		assertEquals(LocalDate.of(1991,01,01),computer.getDateEntree());
		assertEquals(LocalDate.of(2006,01,01),computer.getDateSortie());
		assertEquals(1,computer.getCompany().getId());
	}
	
	@Test 
	void testRetourneNbtotalOrdinateur() {
		int totalComputer = daoTest.getNombreTotalOrdinateur();
		assertEquals(30,totalComputer);
	}
	
	@Test
	void testListeSpecifiqueComputer() {
		List <Computer> computers = daoTest.listeSpecifiquesComputers(0, 5);
		assertEquals(5,computers.size());
		assertEquals("MacBook Pro 15.4 inch",computers.get(0).getName());
		assertEquals("CM-5",computers.get(4).getName());
		
	}
	
	@Test
	void testAjouterComputer() {
		Computer computer = new Computer("lenovo AS",LocalDate.of(2000,01,01),LocalDate.of(2001,02,02),new Company(3,"Apple"));
		daoTest.ajouterUnComputer(computer);
		List<Computer> computers = daoTest.listeComputer();
		assertEquals(31,computers.size());
		assertEquals("lenovo AS",computers.get(computers.size()-1).getName());	
	}
	
	@Test 
	void testDeleteComputer() {
		daoTest.deleteComputer(1);
		assertEquals(null,daoTest.oneComputer(1));
	}
	

}