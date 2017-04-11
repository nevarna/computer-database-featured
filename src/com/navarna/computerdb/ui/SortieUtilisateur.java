package com.navarna.computerdb.ui;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.service.Page;

public class SortieUtilisateur {
	
	public static void lireDetailsComputer (Computer computer) {
		System.out.println("Détail computer : ");
		System.out.println("id : "+computer.getId() + " name : " + computer.getName() + " introduced : " + computer.getIntroduced() + " discontinued : "+ computer.getDiscontinued() + " Company-id "+ computer.getCompany_id());
	}
	
	public static void lireListComputers (Page<Computer> page) {
		System.out.println("Liste Computer : "+page.getNbPage() +" element : "+page.getNbElementPage());  
		for (Computer computer : page.getPage())
			System.out.println("id : "+computer.getId() + " name : " + computer.getName() );
		
	}
	
	public static void lireListCompany (Page<Company> page) {
		System.out.println("Liste Company : Page "+page.getNbPage() +" element : "+page.getNbElementPage()) ; 
		for (Company company: page.getPage())
			System.out.println("id : "+company.getId() + " name : " + company.getName() );
	}
	
	public static void validationChangement(boolean reponse) {
		if(reponse)
			System.out.println("Changement effectué");
		else
			System.out.println("Changement non effectué"); 
	}
	
	public static void AucuneDonnee () {
		System.out.println("Aucune donnée ne correspond à votre recherche");
	}
}
