package com.navarna.computerdb.ui;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;
import com.navarna.computerdb.service.ServiceCompany;
import com.navarna.computerdb.service.ServiceCompanyImpl;
import com.navarna.computerdb.service.ServiceComputer;
import com.navarna.computerdb.service.ServiceComputerImpl;
public class ConstruireRequete {
private static ServiceComputer servComputerImpl = new ServiceComputerImpl();
private static ServiceCompany servCompanyImpl = new ServiceCompanyImpl() ;
	
	public static void demandePageSuivante (String type) {
		if(type.equals("computers")) {
			Page<Computer> page = servComputerImpl.listeSuivante();
			if(page != null) {
				SortieUtilisateur.lireListComputers(page);
			}
			else {
				SortieUtilisateur.lireAucuneDonnee();
			}
		}
		else if (type.equals("companies")) {
			Page<Company> page = servCompanyImpl.listeSuivante();
			if(page != null) {
				SortieUtilisateur.lireListCompanies(page);
			}
			else {
				SortieUtilisateur.lireAucuneDonnee();
			}
		}
		else {
			throw new CLIException ("fonction demandePageSuivante arguments incorect");
		}
	}
	
	public static void resetList (String type) {
		if(type.equals("computers")) {
			servComputerImpl.resetPage();
		}
		else if (type.equals("companies")) {
			servCompanyImpl.resetPage(); 
		}
		else {
			throw new CLIException ("fonction resetList arguments incorect");
		}
	}
	
	public static int stringEnInt (String nombre) {
		int retour = -1 ; 
		try {
			retour = Integer.valueOf(nombre);
			if(retour < 0)
				retour = -1 ; 
		}
		catch (NumberFormatException ne) {
			System.out.println("Ce n'est pas un nombre");
		}
		return retour ;
	}
	
	public static void demandeListe (String type) {
		if(type.equals("computers")) {
			Page<Computer> page = servComputerImpl.liste();
			if(page != null) {
				SortieUtilisateur.lireListComputers(page);
			}
			else {
				SortieUtilisateur.lireAucuneDonnee();
			}
		}
		else if(type.equals("companies")) {
			Page<Company> page = servCompanyImpl.listeSuivante();
			if(page != null) {
				SortieUtilisateur.lireListCompanies(page);
			}
			else {
				SortieUtilisateur.lireAucuneDonnee();
			}
		}
		else {
			throw new CLIException ("fonction demandeListe arguments incorect");
		}
	}
	
	public static void demandeShowId (String id) {
		int ids = stringEnInt(id);
		if(ids != -1) {
			Computer computer= servComputerImpl.show(ids);
			SortieUtilisateur.lireDetailsComputer(computer);
		}
		else {
			throw new CLIException ("fonction demandeShowId arguments incorect");
		}
	}
	
	public static void demandeShowName (String name) {
		Page<Computer> page = servComputerImpl.show(name);
		if(page != null) {
			SortieUtilisateur.lireDetailsComputers(page);
		}
		else {
			SortieUtilisateur.lireAucuneDonnee();
		}
	}
	
	public static void demandeUpdate (String id) {
		int ids = stringEnInt(id);
		if(ids != -1) {
			Computer computer = EntrerUtilisateur.computerInformation(new Long(ids)) ; 
			SortieUtilisateur.lireValidationChangement(servComputerImpl.update(computer));
		}
		else {
			throw new CLIException ("fonction demandeUpdate arguments incorect");
		}
	}
	
	public static void demandeInsert (String type) {
		if(type.equals("computer")) {
			Computer computer = EntrerUtilisateur.computerInformation(new Long(0)) ;
			SortieUtilisateur.lireValidationChangement(servComputerImpl.insert(computer)) ;
		}
		else {
			throw new CLIException ("fonction demandeInsert arguments incorect");
		}
	}
	
	public static void demandeDelete (String id) {
		int ids = stringEnInt(id);
		if(ids != -1) {
			SortieUtilisateur.lireValidationChangement(servComputerImpl.delete(ids));
		}
		else {
			throw new CLIException ("fonction demandeUpdate arguments incorect");
		}
	}
	
	public static void command (String [] command) {
		if(command.length < 2)
			throw new CLIException ("fonction command nombre d'arguments incorect");
		else {
			switch(command[0]) {
			case "List" :
				demandeListe(command[1]);
				break ; 
			case "ShowId" :
				demandeShowId(command[1]);
				break ;
			case "ShowName" :
				demandeShowName(command[1]);
				break ;
			case "Update" :
				demandeUpdate(command[1]);
				break ; 
			case "Insert" : 
				demandeInsert(command[1]);
				break ; 
			case "Delete" : 
				demandeDelete(command[1]);
				break ; 
			} 
		}
	}
}
