package com.excilys.model;

import java.time.LocalDate;

public class Computer {
	
	private int id;
	private String name;
	private Company company;
	private LocalDate dateEntree;
	private LocalDate dateSortie;
	
	public Computer() {
			
		}
	
	public Computer(int id,String name,LocalDate dateEntree, LocalDate dateSortie,Company company) {
		
		this.id=id;
		this.name = name;
		this.company = company;
		this.dateEntree=dateEntree;
		this.dateSortie=dateSortie;
	

	}
	
	//remplacer par un builder
	
	
	
	public Computer(String name,LocalDate dateEntree, LocalDate dateSortie,Company company) {
		
		this.name = name;
		this.company = company;
		this.dateEntree=dateEntree;
		this.dateSortie=dateSortie;
	
	}
	
	
	
	@Override
	public String toString() {
		return this.id + " " + this.name+" "+this.dateEntree+" "+this.dateSortie+" "+this.company;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public LocalDate getDateEntree() {
		return dateEntree;
	}

	public void setDateEntree(LocalDate dateEntree) {
		this.dateEntree = dateEntree;
	}

	public LocalDate getDateSortie() {
		return dateSortie;
	}

	public void setDateSortie(LocalDate dateSortie) {
		this.dateSortie = dateSortie;
	}
	
	
	
	
}
