package com.excilys.beans;

import java.util.List;

import com.excilys.model.Computer;

public class BeanListeComputer {
	
	private List<Computer> computers;
	
	public BeanListeComputer() {
	}
	
	public void setListe(List<Computer> liste) {
		this.computers = liste;
	}
	
	public List<Computer> getListe(){
		return this.computers;
	}

}
