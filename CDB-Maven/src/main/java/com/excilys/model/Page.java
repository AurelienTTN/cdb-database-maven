package com.excilys.model;


public class Page {
	
	private int nbLigne = 10 ;
	private int nbElementTotal;
	private int numeroPage = 1;
	
	public Page() {
		
	}
	

	public int getNbLigne() {
		return nbLigne;
	}

	public void setNbLigne(int nbLigne) {
		this.nbLigne = nbLigne;
	}

	public int getNbElementTotal() {
		return nbElementTotal;
	}

	public void setNbElementTotal(int nbElementTotal) {
		this.nbElementTotal = nbElementTotal;
	}

	public int getNumeroPage() {
		return numeroPage;
	}

	public void setNumeroPage(int numeroPage) {
		this.numeroPage = numeroPage;
	}
	
	 
	
	
	
	
	
	
}
