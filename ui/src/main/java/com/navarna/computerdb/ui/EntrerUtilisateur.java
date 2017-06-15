package com.navarna.computerdb.ui;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.navarna.computerdb.dto.ComputerDTO;
import com.navarna.computerdb.exception.CLIException;
import com.navarna.computerdb.validator.ValidationEntrer;

public class EntrerUtilisateur {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntrerUtilisateur.class);
    public final Scanner sc = new Scanner(System.in);
    private ConstruireRequete construireRequete = new ConstruireRequete(this);

    /**
     * Demande à l'utilisateur d'entrer les informations d'un ordinateur.
     * @param idComputer : id du computer
     * @return Computer : un Computer avec les données insérer
     */
    public ComputerDTO computerInformation(Long idComputer) {
        LOGGER.info("-------->computerInformation(idComputer) args: " + idComputer);
        ComputerDTO computerDTO = null;
        String cInformation = "";
        int idCompany;
        System.out.println(
                "--------- INFORMATION COMPUTER ---------\nEntrer La valeur des champs, ne rien entrer equivaut à mettre la valeur par défaut \n");
        System.out.println("\nNom de l'ordinateur (obligatoire) : ");
        cInformation = sc.nextLine();
        if (cInformation.equals("")) {
            throw new CLIException("Le champs nom n'est pas rempli");
        }
        computerDTO = new ComputerDTO();
        computerDTO.setName(cInformation);
        System.out.println("\nchamps introduced de l'ordinateur");
        cInformation = sc.nextLine();
        if (ValidationEntrer.verificationFormatDate(cInformation)) {
            computerDTO.setIntroduced(cInformation);
        }
        System.out.println("\nchamps discontinued de l'ordinateur");
        cInformation = sc.nextLine();
        if (ValidationEntrer.verificationFormatDate(cInformation)) {
            computerDTO.setDiscontinued(cInformation);
        }
        System.out.println("\nid de la company");
        cInformation = sc.nextLine();
        if ((!cInformation.equals("")) && ((idCompany = ValidationEntrer.stringEnIntPositif(cInformation)) != -1)) {
            computerDTO.setIdCompany(idCompany);
        }
        computerDTO.setId(idComputer);
        return computerDTO;
    }

    /**
     * Demande à l'utilisateur de choisir si il souhaite les détails d'un
     * computer grace à un id ou grace à son nom.
     */
    public void demandeShow() {
        LOGGER.info("-------->demandeShow()");
        System.out.println("\n1 : par id\n2 : par nom");
        int entree = ValidationEntrer.stringEnIntPositif(sc.nextLine());
        String[] command = new String[2];
        switch (entree) {
        case 1:
            int id = demandeId();
            if (id != -1) {
                command[0] = "ShowId";
                command[1] = "" + id;
                construireRequete.command(command);
            } else {
                System.out.println("Suppression de la demande");
            }
            break;
        case 2:
            String nom = demandeName();
            if ((nom != null) && (nom != "")) {
                command[0] = "ShowName";
                command[1] = "" + nom;
                construireRequete.command(command);
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
    public int demandeId() {
        LOGGER.info("-------->demandeId()");
        System.out.println("id de l'ordinateur : ");
        String entree = sc.nextLine();
        return ValidationEntrer.stringEnIntPositif(entree);
    }

    /**
     * Demande à l'utilisateur d'entrer un nom.
     * @return String : le nom
     */
    public String demandeName() {
        LOGGER.info("-------->demandeName()");
        System.out.println("Nom de l'ordinateur : ");
        String entree = sc.nextLine();
        return entree;
    }

    /**
     * Demande à l'utilisateur le numero de la page et le nombre d'élément par
     * page.
     * @param type : companies ou computer
     */
    public void demandeChangeNbListe(String type) {
        LOGGER.info("-------->demandeChangeNbListe(type)" + type);
        System.out.println(
                "Voulez-vous changer le nombre d'element par liste? (Si oui inserer un nombre, sinon taper ENTRER");
        String stringNbElement = sc.nextLine();
        if (!stringNbElement.equals("")) {
            int nbElement = ValidationEntrer.stringEnIntPositif(stringNbElement);
            if (nbElement > 0) {
                construireRequete.demandeChangeNbElement(nbElement);
            }
        }
        System.out.println("Voulez-vous changer le numero de la page? (Si oui inserer un nombre, sinon taper ENTRER");
        String stringNbPage = sc.nextLine();
        if (!stringNbPage.equals("")) {
            int nbPage = ValidationEntrer.stringEnIntPositif(stringNbPage);
            if (nbPage >= 1) {
                construireRequete.demandeChangeNbPage(nbPage - 1);
            }
        }
    }

    /**
     * Permet à l'utilisateur de naviguer dans la liste.
     * @param command : commande de l'utilisateur
     */
    public void retourList(String[] command) {
        LOGGER.info("-------->retourList(command)");
        if (command.length == 2) {
            boolean pasFini = true;
            demandeChangeNbListe(command[1]);
            construireRequete.command(command);
            while (pasFini) {
                System.out.println("Souhaitez-vous continuez la liste ?\noui\nnon(Par defaut non)");
                String entree = sc.nextLine();
                if (entree.equals("oui")) {
                    construireRequete.demandePageSuivante(command[1]);
                } else {
                    pasFini = false;
                }
            }
            construireRequete.resetList(command[1]);
        }
    }

    /**
     * Boucle principal : propose des choix à l'utilisateur.
     */
    public void choixPrincipal() {
        LOGGER.info("-------->choixPrincipal()");
        int entree = 0;
        boolean fini = false;

        while (!fini) {
            System.out.println("Que souhaitez-vous faire? (Entrer un chiffre)\n" + "1 - Liste des ordinateurs\n"
                    + "2 - Liste des compagnies\n" + "3 - Détails d'un ordinateur\n"
                    + "4 - Enregistrer un ordinateur dans la base de donnée\n"
                    + "5 - Modifier un ordinateur dans la base de donnée\n"
                    + "6 - Supprimer un ordinateur de la base de donnée\n" + "7 - supprimer une company\n"
                    + "8 - Quitter le programme");
            if (sc.hasNext()) {
                try {
                    entree = ValidationEntrer.stringEnIntPositif(sc.nextLine());
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
                        construireRequete.command(command);
                        break;
                    case 5:
                        id = demandeId();
                        if (id != -1) {
                            command[0] = "Update";
                            command[1] = "" + id;
                            construireRequete.command(command);
                        } else {
                            System.out.println("Suppression de la demande");
                        }
                        break;
                    case 6:
                        id = demandeId();
                        if (id != -1) {
                            command[0] = "Delete";
                            command[1] = "" + id;
                            construireRequete.command(command);
                        } else {
                            System.out.println("Suppression de la demande");
                        }
                        break;
                    case 7:
                        id = demandeId();
                        if (id != -1) {
                            command[0] = "DeleteCompany";
                            command[1] = "" + id;
                            construireRequete.command(command);
                        }
                        break;
                    case 8:
                        fini = true;
                        System.out.println("fermeture du programme");
                        break;
                    default:
                        System.out.println("Option inconnue");
                        break;
                    }
                } catch (Exception e) {
                    SortieUtilisateur.lireErreur(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        sc.close();
    }
}
