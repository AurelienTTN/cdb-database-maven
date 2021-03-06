package com.excilys.mapperTest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.Mapper;
import com.excilys.model.Computer;

import junit.framework.TestCase;

public class MapperTest extends TestCase {
	
	@Test
	public final void testCreateComputerfromDTO() {
		
		//test sur les dates
		
		Mapper mappy = com.excilys.mapper.Mapper.getInstance();
		ComputerDTO computerDTO = new ComputerDTO("Jerome","2020-11-03","2019-11-02","23");
		Computer computer = mappy.createComputer(computerDTO);
		if(computer!=null) {
			fail("La fonction create computer ne retourne pas un pc instancié à null quand les dates sont fausses");
		}
		
		computerDTO = new ComputerDTO("","2019-03-02","2020-02-03","");
		computer = mappy.createComputer(computerDTO);
		if(computer!=null) {
			fail("La fonction create computer ne retourne pas un pc instancié à null quand le nom n'est pas bon");
		}
		
		computerDTO = new ComputerDTO(" ","2019-03-02","2020-02-03","");
		computer = mappy.createComputer(computerDTO);
		if(computer!=null) {
			fail("La fonction create computer ne retourne pas un pc instancié à null quand le nom n'est pas bon");
		}
	}
	
	@Test 
	public final void testCreateFromResultSet() throws SQLException {
		ResultSet mockResultSet = mock(ResultSet.class);
		when(mockResultSet.next()).thenReturn(true).thenReturn(false);
		when(mockResultSet.getInt(1)).thenReturn(1);
		when(mockResultSet.getString(2)).thenReturn("DEL");
		when(mockResultSet.getDate(4)).thenReturn(null);
		when(mockResultSet.getString(5)).thenReturn(null);
		when(mockResultSet.getInt(5)).thenReturn(0);
		when(mockResultSet.getString(7)).thenReturn(null);
		Computer computer = Mapper.getInstance().dataToComputer(mockResultSet);
		assertEquals("DEL",computer.getName());
		System.out.println(computer);
	}
	
	@Test 
	public final void testCreateEmptyListFromEmptyResultSet() throws SQLException {
		ResultSet mockResultSet = mock(ResultSet.class);
		when(mockResultSet.next()).thenReturn(false);
		List<Computer> computers = Mapper.getInstance().dataToListComputer(mockResultSet);
		assertEquals(0,computers.size());
	}
	
	
	
	
	
	

}
