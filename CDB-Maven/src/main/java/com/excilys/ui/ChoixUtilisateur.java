package com.excilys.ui;

import java.time.LocalDate;
import java.util.Scanner;

public class ChoixUtilisateur {

	private static ChoixUtilisateur instance;
	
	private ChoixUtilisateur() {
		
	}
	
	public static ChoixUtilisateur getInstance() {
	       if (instance == null) {
	            instance = new ChoixUtilisateur();
	        }
	        return instance;
	    }
	
	public int choixMenu() {
		Scanner sc = new Scanner(System.in);
		return sc.nextInt();
		
	}
	
	public int choixOrdinateur() {
		System.out.println("Veuillez rentrer un numéro d'ordinateur");
		Scanner sc = new Scanner(System.in);
		return sc.nextInt();
	}
	
	public String choixNom() {
		System.out.println("Veuillez entre un nom d'ordinateur");
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
	}
	
	public String choixDateEntree() {
		Scanner scan_date = new Scanner(System.in);
		System.out.println("Entrez une date d'entré sous la forme yyyy-mm-dd");
		String date_entry = scan_date.nextLine();
		return date_entry;
	}
	
	public String choixDateSortie() {
		Scanner scan_date = new Scanner(System.in);
		System.out.println("Entrez une date de sortie sous la forme yyyy-mm-dd");
		return scan_date.nextLine();
	}
		
	public String choixIDCompany() {
		System.out.println("Veuillez rentrer un id d'entreprise pour cette ordinateur");
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
	}
	
	public int choixChampAChanger() {
		Scanner scan5 = new Scanner(System.in);
		System.out.println("Entrez la colonne a changer (2-name, 3-introduced, 4-discontinued, 5-company_id");
		int colonne = scan5.nextInt();
		return colonne;
	}
	
	public int choixMenuPage() {
		System.out.println("Page précedente [1] Page Suivante [2] Numéro de Page Spécifique [3] Quitter [0]");
		Scanner choix = new Scanner(System.in);
		return choix.nextInt();
	}
	
	public int choixPageSpecifique() {
		System.out.println("Veuillez entrer un numéro de page à afficher");
		Scanner choix = new Scanner(System.in);
		return choix.nextInt();
	}

	public int choixCompagnie() {
		System.out.println("Veuillez entrer l'ID d'une compagnie à supprimer");
		Scanner choix = new Scanner(System.in);
		return choix.nextInt();
	}
	
	
	
	
	
	
	
}
