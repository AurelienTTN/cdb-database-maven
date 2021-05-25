package com.excilys.ui;

import java.util.List;

import com.excilys.model.Computer;

public class ComputerCLI {
	
	private static ComputerCLI instance;
	
	private ComputerCLI() {
		
	}
	
	
	public static ComputerCLI getInstance() {
        if (instance == null) {
            instance = new ComputerCLI();
        }
        return instance;
    }
	public void afficherComputer(Computer computer) {
		System.out.println(computer);
	}
	
	public void afficherListeComputers(List<Computer> computers) {
		for(Computer c : computers) {
			this.afficherComputer(c);
		}
	}
	
	
	
	
}
