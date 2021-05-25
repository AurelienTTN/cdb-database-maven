package com.excilys.model;

public class Company {
	
	private int id;
	private String name;
	
	public Company(int id,String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.id + " " + this.name;
	}
	
	//getter
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}

}
