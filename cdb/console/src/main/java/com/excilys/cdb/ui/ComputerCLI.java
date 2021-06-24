package com.excilys.ui;

import java.util.List;

import org.springframework.stereotype.Component;

import com.excilys.dto.ComputerDTO;
import com.excilys.model.Computer;

@Component
public class ComputerCLI {
	
	
	private ComputerCLI() {
		
	}
	
	public void afficherComputer(ComputerDTO computer) {
		System.out.println(computer);
	}
	
	public void afficherListeComputers(List<ComputerDTO> computers) {
		for(ComputerDTO c : computers) {
			this.afficherComputer(c);
		}
	}
	
	
	
	
}
