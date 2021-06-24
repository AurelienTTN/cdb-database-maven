package com.excilys.cdb.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="computer")
@Table(name="computer")
public class Computer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;
	private LocalDate introduced;
	private LocalDate discontinued;
	
	public Computer() {
			
		}
	
	public Computer(int id,String name,LocalDate dateEntree, LocalDate dateSortie,Company company) {
		
		this.id=id;
		this.name = name;
		this.company = company;
		this.introduced=dateEntree;
		this.discontinued=dateSortie;
	

	}
	
	//remplacer par un builder
	
	
	
	public Computer(String name,LocalDate dateEntree, LocalDate dateSortie,Company company) {
		
		this.name = name;
		this.company = company;
		this.introduced=dateEntree;
		this.discontinued=dateSortie;
	
	}
	
	
	
	@Override
	public String toString() {
		return this.id + " " + this.name+" "+this.introduced+" "+this.discontinued+" "+this.company;
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
		return introduced;
	}

	public void setDateEntree(LocalDate dateEntree) {
		this.introduced = dateEntree;
	}

	public LocalDate getDateSortie() {
		return discontinued;
	}

	public void setDateSortie(LocalDate dateSortie) {
		this.discontinued = dateSortie;
	}
	
	
	
	
}
