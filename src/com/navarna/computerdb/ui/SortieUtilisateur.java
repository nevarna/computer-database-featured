package com.navarna.computerdb.ui;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;

public class SortieUtilisateur {
	
	public static void lireDetailsComputer (Computer computer) {
		System.out.println("Détail computer : " + computer );
	}
	
	public static void lireListComputers (Page<Computer> page) {
		System.out.println("Liste Computer : "+page.getNbPage() +" element : "+page.getNbElementPage());  
		for (Computer computer : page.getPage())
			System.out.println("id : "+computer.getId() + " name : " + computer.getName() );
		
	}
	
	public static void lireListCompanies (Page<Company> page) {
		System.out.println("Liste Company : Page "+page.getNbPage() +" element : "+page.getNbElementPage()) ; 
		for (Company company: page.getPage())
			System.out.println("id : "+company.getId() + " name : " + company.getName() );
	}
	
	public static void lireValidationChangement(int reponse) {
		if(reponse != 0) {
			System.out.println("Changement effectué");
		}
		else {
			System.out.println("Changement non effectué"); 
		}
	}
	
	public static void lireAucuneDonnee () {
		System.out.println("Aucune donnée ne correspond à votre recherche");
	}
	
	public static void lireErreur (String erreur) {
		System.out.println("Une erreur s'est produite dans la couche suivante : "+ erreur);
	}
}
