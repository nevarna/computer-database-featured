package com.navarna.computerdb.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Scanner;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Company.CompanyBuilder;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Computer.ComputerBuilder;

public class EntrerUtilisateur {

	public static final Scanner SC = new Scanner(System.in);

	public static boolean verifeFormatTimestamp(String timestamp) {
		boolean retour = false;
		String[] decoup = timestamp.split(" ");
		if (decoup.length > 2)
			return retour;
		if (decoup.length > 0) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.setLenient(false);
			try {
				df.parse(decoup[0]);
			} catch (ParseException pe) {
				System.out.println("Date incorrect");
				return retour;
			}
			if (decoup.length == 2) {
				String[] temps = decoup[1].split(":");
				if (temps.length != 3)
					return retour;
				else {
					int heure = ConstruireRequete.stringEnInt(temps[0]);
					if ((heure < 0) || (heure > 23))
						return retour;
					int minute = ConstruireRequete.stringEnInt(temps[1]);
					if ((minute < 0) || (minute > 59))
						return retour;
					int seconde = ConstruireRequete.stringEnInt(temps[2]);
					if ((seconde < 0) || (seconde > 59))
						return retour;
					retour = true;
				}
			} else {
				retour = true;
			}
		} else {
			return retour;
		}
		return retour;
	}

	public static Computer computerInformation(Long idComputer) {
		ComputerBuilder computerBuilder = null;
		String cInformation = "";
		LocalDate date ; 
		int idCompany ; 
		System.out.println(
				"--------- INFORMATION COMPUTER ---------\nEntrer La valeur des champs, ne rien entrer equivaut à mettre la valeur par défaut \n");
		System.out.println("\nNom de l'ordinateur (obligatoire) : ");
		cInformation = SC.nextLine();
		if (cInformation.equals("")) {
			throw new CLIException("Le champs nom n'est pas rempli");
		}
		computerBuilder = new ComputerBuilder(cInformation);
		System.out.println("\nchamps introduced de l'ordinateur");
		cInformation = SC.nextLine();
		if (verifeFormatTimestamp(cInformation)) {
			date = LocalDate.parse(cInformation);
			computerBuilder.setIntroduced(date);
		}
		System.out.println("\nchamps discontinued de l'ordinateur");
		cInformation = SC.nextLine();
		if (verifeFormatTimestamp(cInformation)) {
			date = LocalDate.parse(cInformation);
			computerBuilder.setDiscontinued(date);
		}
		System.out.println("\nid de la company");
		cInformation = SC.nextLine();
		CompanyBuilder companyBuilder = new CompanyBuilder("aucuneImportance");
		if ((!cInformation.equals("")) && ((idCompany = ConstruireRequete.stringEnInt(cInformation)) != -1)) {
			companyBuilder.setId(new Long(idCompany));
		}
		Company company = companyBuilder.build();
		computerBuilder.setCompany(company);
		computerBuilder.setId(idComputer);
		Computer computer = computerBuilder.build();
		return computer;
	}

	public static int demandeId(Scanner SC) {
		System.out.println("id de l'ordinateur : ");
		String entree = SC.nextLine();
		return ConstruireRequete.stringEnInt(entree);
	}

	public static void retourList(String[] command) {
		if (command.length == 2) {
			boolean pasFini = true ; 
			ConstruireRequete.command(command);
			while (pasFini) {
				System.out.println("Souhaitez-vous continuez la liste ?\noui\nnon(Par defaut non)");
				String entree = SC.nextLine();
				if (entree.equals("oui")) {
					ConstruireRequete.demandePageSuivante(command[1]);
				} else {
					pasFini = false;
				}
			}
			ConstruireRequete.resetList(command[1]);
		}
	}

	public static void choixPrincipal() {
		int entree = 0;
		boolean fini = false;

		while (!fini) {
			System.out.println(
					"Que souhaitez-vous faire? (Entrer un chiffre)\n1 - Liste des ordinateurs\n2 - Liste des compagnies\n3 - Détails d'un ordinateur"
							+ "\n4 - Enregistrer un ordinateur dans la base de donnée\n5 - Modifier un ordinateur dans la base de donnée\n6 - Supprimer un ordinateur de la base de donnée"
							+ "\n7 Quitter le programme");
			if (SC.hasNext()) {
				try {
					entree = ConstruireRequete.stringEnInt(SC.nextLine());
					String[] command = new String[2];
					int id = -1;
					switch (entree) {
					case 1:
						command[0] = "List";
						command[1] = "computers";
						retourList(command);
						break;
					case 2:
						command[0] = "List";
						command[1] = "companies";
						retourList(command);
						break;
					case 3:
						id = demandeId(SC);
						if (id != -1) {
							command[0] = "Show";
							command[1] = "" + id;
							ConstruireRequete.command(command);
						} else
							System.out.println("Suppression de la demande");
						break;
					case 4:
						command[0] = "Insert";
						command[1] = "computer";
						ConstruireRequete.command(command);
						break;
					case 5:
						id = demandeId(SC);
						if (id != -1) {
							command[0] = "Update";
							command[1] = "" + id;
							ConstruireRequete.command(command);
						} else
							System.out.println("Suppression de la demande");
						break;
					case 6:
						id = demandeId(SC);
						if (id != -1) {
							command[0] = "Delete";
							command[1] = "" + id;
							ConstruireRequete.command(command);
						} else
							System.out.println("Suppression de la demande");
						break;
					case 7:
						fini = true;
						System.out.println("fermeture du programme");
						break;
					default:
						System.out.println("Option inconnue");
						break;
					}
				} catch (CLIException ci) {
					SortieUtilisateur.lireErreur(ci.getMessage());
				}
			}
		}
		SC.close();
	}
}
