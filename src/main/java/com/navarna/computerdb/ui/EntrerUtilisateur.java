package com.navarna.computerdb.ui;

import java.time.LocalDate;
import java.util.Scanner;

import com.navarna.computerdb.mapper.MapperException;
import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Company.CompanyBuilder;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Computer.ComputerBuilder;
import com.navarna.computerdb.persistence.DAOException;
import com.navarna.computerdb.validator.ValidationEntrer;

public class EntrerUtilisateur {

    public static final Scanner SC = new Scanner(System.in);

    /**
     

    /**
     * Demande à l'utilisateur d'entrer les informations d'un ordinateur.
     * @param idComputer : id du computer
     * @return Computer : un Computer avec les données insérer
     */
    public static Computer computerInformation(Long idComputer) {
        ComputerBuilder computerBuilder = null;
        String cInformation = "";
        LocalDate date;
        int idCompany;
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
        if (ValidationEntrer.verifeFormatTimestamp(cInformation)) {
            date = LocalDate.parse(cInformation);
            computerBuilder.setIntroduced(date);
        }
        System.out.println("\nchamps discontinued de l'ordinateur");
        cInformation = SC.nextLine();
        if (ValidationEntrer.verifeFormatTimestamp(cInformation)) {
            date = LocalDate.parse(cInformation);
            computerBuilder.setDiscontinued(date);
        }
        System.out.println("\nid de la company");
        cInformation = SC.nextLine();
        CompanyBuilder companyBuilder = new CompanyBuilder("aucuneImportance");
        if ((!cInformation.equals("")) && ((idCompany = ValidationEntrer.stringEnInt(cInformation)) != -1)) {
            companyBuilder.setId(new Long(idCompany));
        }
        Company company = companyBuilder.build();
        computerBuilder.setCompany(company);
        computerBuilder.setId(idComputer);
        Computer computer = computerBuilder.build();
        return computer;
    }

    /**
     *  Demande à l'utilisateur de choisir si il souhaite les détails d'un computer grace à un id ou grace à son nom.
     */
    public static void demandeShow() {
        System.out.println("\n1 : par id\n2 : par nom");
        int entree = ValidationEntrer.stringEnInt(SC.nextLine());
        String[] command = new String[2];
        switch (entree) {
        case 1:
            int id = demandeId();
            if (id != -1) {
                command[0] = "ShowId";
                command[1] = "" + id;
                ConstruireRequete.command(command);
            } else {
                System.out.println("Suppression de la demande");
            }
            break;
        case 2:
            String nom = demandeName();
            if ((nom != null) && (nom != "")) {
                command[0] = "ShowName";
                command[1] = "" + nom;
                ConstruireRequete.command(command);
            } else {
                System.out.println("Suppression de la demande");
            }
            break;
        default:
            System.out.println("Suppression de la demande");
            break;
        }

    }

    /**
     * Demande à l'utilisateur d'entrer un id.
     * @return int
     */
    public static int demandeId() {
        System.out.println("id de l'ordinateur : ");
        String entree = SC.nextLine();
        return ValidationEntrer.stringEnInt(entree);
    }

    /**
     * Demande à l'utilisateur d'entrer un nom.
     * @return String : le nom
     */
    public static String demandeName() {
        System.out.println("Nom de l'ordinateur : ");
        String entree = SC.nextLine();
        return entree;
    }

    /**
     * Demande à l'utilisateur le numero de la page et le nombre d'élément par page.
     * @param type : companies ou computer
     */
    public static void demandeChangeNbListe(String type) {
        System.out.println(
                "Voulez-vous changer le nombre d'element par liste? (Si oui inserer un nombre, sinon taper ENTRER");
        String stringNbElement = SC.nextLine();
        if (!stringNbElement.equals("")) {
            int nbElement = ValidationEntrer.stringEnInt(stringNbElement);
            if (nbElement > 0) {
                ConstruireRequete.demandeChangeNbElement(type, nbElement);
            }
        }
        System.out.println("Voulez-vous changer le numero de la page? (Si oui inserer un nombre, sinon taper ENTRER");
        String stringNbPage = SC.nextLine();
        if (!stringNbPage.equals("")) {
            int nbPage = ValidationEntrer.stringEnInt(stringNbPage);
            if (nbPage >= 1) {
                ConstruireRequete.demandeChangeNbPage(type, nbPage - 1);
            }
        }
    }

    /**
     * Permet à l'utilisateur de naviguer dans la liste.
     * @param command : commande de l'utilisateur
     */
    public static void retourList(String[] command) {
        if (command.length == 2) {
            boolean pasFini = true;
            demandeChangeNbListe(command[1]);
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

    /**
     * Boucle principal : propose des choix à l'utilisateur.
     */
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
                    entree = ValidationEntrer.stringEnInt(SC.nextLine());
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
                        demandeShow();
                        break;
                    case 4:
                        command[0] = "Insert";
                        command[1] = "computer";
                        ConstruireRequete.command(command);
                        break;
                    case 5:
                        id = demandeId();
                        if (id != -1) {
                            command[0] = "Update";
                            command[1] = "" + id;
                            ConstruireRequete.command(command);
                        } else {
                            System.out.println("Suppression de la demande");
                        }
                        break;
                    case 6:
                        id = demandeId();
                        if (id != -1) {
                            command[0] = "Delete";
                            command[1] = "" + id;
                            ConstruireRequete.command(command);
                        } else {
                            System.out.println("Suppression de la demande");
                        }
                        break;
                    case 7:
                        fini = true;
                        System.out.println("fermeture du programme");
                        break;
                    default:
                        System.out.println("Option inconnue");
                        break;
                    }
                } catch (DAOException | MapperException | CLIException e) {
                    SortieUtilisateur.lireErreur(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        SC.close();
    }
}