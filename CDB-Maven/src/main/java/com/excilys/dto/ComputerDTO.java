package com.excilys.dto;



public class ComputerDTO {
	private String name;
	private String company;
	private String dateEntree;
	private String dateSortie;
	
	public ComputerDTO(String name,String dateEntree,String dateSortie,String company) {
		this.name=name;
		this.company=company;
		this.dateEntree=dateEntree;
		this.dateSortie=dateSortie;
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

}
