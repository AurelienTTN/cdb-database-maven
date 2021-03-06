package com.excilys.dto;



public class ComputerDTO {
	private String id;
	private String name;
	private String company;
	private String dateEntree;
	private String dateSortie;
	
	public ComputerDTO(String id,String name,String dateEntree,String dateSortie,String company) {
		this.id = id;
		this.name=name;
		this.company=company;
		this.dateEntree=dateEntree;
		this.dateSortie=dateSortie;
	}
	
	public ComputerDTO(String name,String dateEntree,String dateSortie,String company) {
		this.name=name;
		this.company=company;
		this.dateEntree=dateEntree;
		this.dateSortie=dateSortie;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDateEntree() {
		return dateEntree;
	}

	public void setDateEntree(String dateEntree) {
		this.dateEntree = dateEntree;
	}

	public String getDateSortie() {
		return dateSortie;
	}

	public void setDateSortie(String dateSortie) {
		this.dateSortie = dateSortie;
	}
	
	@Override
	public String toString() {
		return this.id + " " + this.name + " " + this.company+" "+this.dateEntree+" "+this.dateSortie;
	}

}
