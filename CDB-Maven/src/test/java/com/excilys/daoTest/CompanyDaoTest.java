package com.excilys.daoTest;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.jdbc.ScriptRunner;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.persistence.Dao;

class CompanyDaoTest {
	
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
	void testListeCompagnie() {
		List<Company> companies = daoTest.listeCompanies();
		assertEquals(42,companies.size());
	}
	
	@Test
	void testGetCompanyByID() {
		Company companie = daoTest.getCompanyById(34);
		assertEquals("OMRON",companie.getName());
	}
	
	
	
	

}
