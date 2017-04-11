package com.navarna.computerdb.ui;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.navarna.computerdb.service.ConstruireRequete;
public class EntrerUtilisateur {
	
	public static final Scanner SC  = new Scanner(System.in);

	public EntrerUtilisateur() {
		// TODO Auto-generated constructor stub
	}
	
	public static boolean verifeFormatTimestamp (String timestamp) {
		boolean retour = false ;
		String [] decoup = timestamp.split(" ");
		if(decoup.length > 2)
			return retour ; 
		if(decoup.length > 0) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.setLenient(false);
			try {
				df.parse(decoup[0]);
			}
			catch (ParseException pe) {
				System.out.println("Date incorrect");
				return retour ; 
			}
			if(decoup.length == 2) {
				String []temps = decoup[1].split(":");
				if(temps.length != 3)
					return retour; 
				else {
					int heure = ConstruireRequete.StringEnInt(temps[0]);
					if((heure < 0)||(heure >23))
							return retour ; 
					int minute = ConstruireRequete.StringEnInt(temps[1]);
					if((minute < 0)||(minute >59))
							return retour ;
					int seconde = ConstruireRequete.StringEnInt(temps[2]);
					if((seconde < 0)||(seconde >59))
							return retour ;
					retour = true; 
				}
			}
			else {
				retour = true ;
			}
		}
		else {
			return retour ;
		}
		return retour ; 
	}
	
	public static String updateInformation () {
		String information = "" ;
		System.out.println("--------- INFORMATION UPDATE ---------\nEntrer des modifications pour les champs à modifier\nSi vous ne souhaitez pas modifier le champ entrez '/noMod' ");
		System.out.println("\nNom de l'ordinateur : ") ; 
		String cInformation ="'"+ SC.nextLine()+"'" ; 
		if(! cInformation.equals("'/noMod'")) {
			information += "name = " ;
			if( cInformation.equals("''")) {
				 cInformation = "NULL" ; 
			}
			information +=  cInformation ; 
		}
		System.out.println("\nchamps introduced de l'ordinateur (format aaaa-mm-jj hh:mm:ss ou  aaaa-mm-jj)");
		cInformation = SC.nextLine() ;
		if(! cInformation.equals("/noMod")) {
			if(verifeFormatTimestamp(cInformation)) {
				cInformation ="'"+ cInformation+"'" ;
				if(information != "" ) information +=" , " ; 
				information += "introduced = " ;
				if( cInformation.equals("''")) {
					 cInformation = "NULL" ; 
				}
				information +=  cInformation ; 
			}
		}
		System.out.println("\nchamps discontinued de l'ordinateur(format aaaa-mm-jj hh:mm:ss ou  aaaa-mm-jj)");
		cInformation = SC.nextLine() ; 
		if(! cInformation.equals("/noMod")) {
			if(verifeFormatTimestamp(cInformation)) {
				cInformation ="'"+ cInformation+"'" ; 
				if(information != "" ) information +=" , " ; 
				information += "discontinued = " ;
				if( cInformation.equals("''")) {
					 cInformation = "NULL" ; 
				}
				information +=  cInformation ; 
			}
		}
		System.out.println("id de la company");
		cInformation =SC.nextLine() ; 
		if(! cInformation.equals("/noMod")) {
			if(information != "" ) information +=" , " ; 
			information += "company_id = " ;
			if( cInformation.equals("")||(ConstruireRequete.StringEnInt(cInformation)== -1)) {
				 cInformation = "NULL" ; 
			}
			information +=  cInformation ; 
		}
		return information ;
	}
	
	public static String createInformation () {
		String information = "NULL, " ;
		String cInformation = "''";
		System.out.println("--------- INFORMATION CREATE ---------\nEntrer La valeur des champs, ne rien entrer equivaut à mettre la valeur par défaut \n");
		System.out.println("\nNom de l'ordinateur : ") ; 
		cInformation ="'"+ SC.nextLine()+"'" ; 
		if( cInformation.equals("''")) {
			cInformation = "NULL" ; 
		}
		information +=  cInformation ; 
		System.out.println("\nchamps introduced de l'ordinateur");
		cInformation =SC.nextLine() ;
		if(verifeFormatTimestamp(cInformation)) {
			cInformation ="'"+ cInformation+"'" ; 
		}
		else {
			cInformation = "''"; 
		}
		information +=" , " ; 
		if( cInformation.equals("''")) {
			cInformation = "NULL" ; 
		}
		information +=  cInformation ; 
		System.out.println("\nchamps discontinued de l'ordinateur");
		cInformation =SC.nextLine() ;
		if(verifeFormatTimestamp(cInformation)) {
			cInformation ="'"+ cInformation+"'" ; 
		}
		else {
			cInformation = "''"; 
		}
		information +=" , " ; 
		if( cInformation.equals("''")) {
			cInformation = "NULL" ; 	
		}
		information +=  cInformation ; 
		System.out.println("id de la company");
		cInformation =SC.nextLine() ; 
		information +=" , " ; 
		if( cInformation.equals("")||(ConstruireRequete.StringEnInt(cInformation)== -1)) {
			 cInformation = "NULL" ; 
		}
		information +=  cInformation ; 
		return information ;
	}
	
	public static int demandeId(Scanner SC) {
		System.out.println("id de l'ordinateur : ");
		String entree = SC.nextLine();
		return ConstruireRequete.StringEnInt(entree);
	}
	
	public static void retourList (String [] command) {
		if(command.length == 2) {
		boolean pasFini =ConstruireRequete.command(command);  
			while(pasFini) {
				System.out.println("Souhaitez-vous continuez la liste ?\noui\nnon(Par defaut non)");
				String entree = SC.nextLine();
				if(entree.equals("oui")) {
					pasFini = ConstruireRequete.pageSuivante(command[1]); 	
				}
				else {
					pasFini = false ; 
				}
			}
		ConstruireRequete.resetList(command[1]);
		}
	}

	public static void choixPrincipal() {
		int entree = 0 ; 
		boolean fini = false ; 
		
		while(!fini) {
			System.out.println("Que souhaitez-vous faire? (Entrer un chiffre)\n1 - Liste des ordinateurs\n2 - Liste des compagnies\n3 - Détails d'un ordinateur"+
					"\n4 - Enregistrer un ordinateur dans la base de donnée\n5 - Modifier un ordinateur dans la base de donnée\n6 - Supprimer un ordinateur de la base de donnée"
					+"\n7 Quitter le programme");
			if(SC.hasNext()) {
				entree = ConstruireRequete.StringEnInt(SC.nextLine()); 
				String [] command  = new String[2] ; 
				int id = -1 ; 
				switch(entree) {
				case 1 : 
					command[0] = "List";
					command[1] = "computers";
					retourList(command);
					break ;
				case 2 : 
					command[0] = "List";
					command[1] = "companies";
					retourList(command);
					break ;
				case 3 : 
					id = demandeId(SC); 
					if(id != -1) {
						command[0] = "Show";
						command[1] = ""+id;
						ConstruireRequete.command(command) ; 
					}
					else
						System.out.println("Suppression de la demande");
					break ; 
				case 4 : 
					command[0] = "Create";
					command[1] = "computer";
					ConstruireRequete.command(command) ; 
					break ;
				case 5 : 
					id = demandeId(SC); 
					if(id != -1) {
						command[0] = "Update";
						command[1] = ""+id;
						ConstruireRequete.command(command) ; 
					}
					else
						System.out.println("Suppression de la demande");
					break ; 
				case 6 :
					id = demandeId(SC); 
					if(id != -1) {
						command[0] = "Delete";
						command[1] = ""+id;
						ConstruireRequete.command(command) ; 
					}
					else
						System.out.println("Suppression de la demande");
					break ;
				case 7 :
					fini = true ;
					System.out.println("fermeture du programme");
					break ; 
				default : 
					System.out.println("Option inconnue");
					break ; 
				}
			}
		}
		SC.close();
	}
}
