package com.navarna.computerdb.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.navarna.computerdb.ui.EntrerUtilisateur;
import com.navarna.computerdb.ui.SortieUtilisateur;
import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.persistence.DaoCompany;
import com.navarna.computerdb.persistence.DaoComputer;
public class ConstruireRequete {

	public ConstruireRequete() {
		// TODO Auto-generated constructor stub
	}
	
	public static boolean pageSuivante (String type) {
		if(type.equals("computers"))
			return DaoComputer.getInstance().suivantList();
		else if (type.equals("companies"))
			return DaoCompany.getInstance().suivantList();
		else 
			return false ;
	}
	
	public static void resetList (String type) {
		if(type.equals("computers"))
			DaoComputer.getInstance().resetList();
		else if (type.equals("companies"))
			DaoCompany.getInstance().resetList();
	}
	
	public static int StringEnInt (String integer) {
		int retour = -1 ; 
		try {
			retour = Integer.valueOf(integer);
			if(retour < 0)
				retour = -1 ; 
		}
		catch (NumberFormatException ne) {
			System.out.println("ERREUR : le second arguments n'est pas un int") ; 
		}
		return retour ;
	}
	
	public static boolean command (String [] command) {
		boolean retour = false ; 
		if(command.length < 2)
			return retour ;
		else {
			switch(command[0]) {
			case "List" :
				if(command[1].equals("computers"))
					retour = DaoComputer.getInstance().list() ; 
				if(command[1].equals("companies"))
					retour = DaoCompany.getInstance().list() ; 
				break ; 
			case "Show" :
				int ids = StringEnInt(command[1]);
				if(ids != -1)
					retour = DaoComputer.getInstance().show(ids);
				break ; 
			case "Update" :
				int idu = StringEnInt(command[1]);
				if(idu != -1) {
					retour = DaoComputer.getInstance().update(idu, EntrerUtilisateur.updateInformation()); 
				}
				break ; 
			case "Create" : 
				if(command[1].equals("computer"))
					retour = DaoComputer.getInstance().insert(EntrerUtilisateur.createInformation()); 
				break ; 
			case "Delete" : 
				int idd = StringEnInt(command[1]);
				if(idd != -1) {
					retour = DaoComputer.getInstance().delete(idd);
				} 
				break ; 
			}
			return retour; 
		}
	}
	
	public static boolean switchResult (ResultSet result , int mode) {
		boolean retour = false ; 
		if((mode < 0)||(mode > 4))
			return retour; 
		switch(mode) {
		case 0 : 
			retour = extraireNameCompany(result) ; 
			break ; 
		case 1 : 
			retour = extraireNameComputer(result);
			;
			break ; 
		case 2 : 
			retour = extraireDetail(result);
			break ; 
		case 3 :
			SortieUtilisateur.validationChangement(retour);
			break ; 
		case 4 : 
			retour = true ;
			SortieUtilisateur.validationChangement(retour);
			break ; 
		}	
		return retour ; 
	}
	
	public static boolean extraireNameComputer(ResultSet result) {
		boolean retour = false; 
		try {
			Page<Computer> page = new Page<Computer>(DaoComputer.getInstance().getPage(), DaoComputer.getInstance().getNbElement()); 
			int compteur = 0 ; 
			while(result.next()) {
				compteur ++ ; 
				 int id = result.getInt("id");
				 String name = result.getString("name");
				 page.addElement(new Computer(id,name));
			}
			if(compteur != 0) {
				SortieUtilisateur.lireListComputers(page);
				retour = true ;	
			}
			else 
				SortieUtilisateur.AucuneDonnee();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		return retour ;
	}
	
	public static boolean extraireNameCompany(ResultSet result) {
		boolean retour = false; 
		try {
			Page<Company> page = new Page<Company>(DaoCompany.getInstance().getPage(),DaoCompany.getInstance().getNbElement()); 
			int compteur = 0 ; 
			while(result.next()) {
				compteur ++;
				int id = result.getInt("id");
				String name = result.getString("name");
				page.addElement(new Company(id,name));
			}
			if(compteur != 0) {
				SortieUtilisateur.lireListCompany(page);
				retour = true ;
			}
			else 
				SortieUtilisateur.AucuneDonnee();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		return retour ;
	}
	
	public static boolean extraireDetail(ResultSet result) {
		boolean retour = false; 
		try {
			int num = 0 ; 
			while(result.next()) {
				num++ ; 
				int id = result.getInt("id");
				String name = result.getString("name");
				Timestamp introduced = result.getTimestamp("introduced");
				Timestamp discontinued =  result.getTimestamp("discontinued");
				int companyId  = result.getInt("company_id");
				Computer computer = new Computer(id,name,introduced,discontinued,companyId);
				SortieUtilisateur.lireDetailsComputer(computer);
			}
			if(num == 0)
				SortieUtilisateur.AucuneDonnee();
			retour = true ;
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		return retour ;
	}

}
