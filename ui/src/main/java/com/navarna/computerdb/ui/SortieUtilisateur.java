package com.navarna.computerdb.ui;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;

public class SortieUtilisateur {

    /**
     * Affiche en console les détails d'un computer.
     * @param computer : computer à afficher
     */
    public static void lireDetailsComputer(Computer computer) {
        System.out.println("Détail computer : " + computer);
    }

    /**
     * Affiche la page de computer avec leurs détails.
     * @param page : page de Computer
     */
    public static void lireDetailsComputers(Page<Computer> page) {
        System.out.println("Detail computers");
        for (Computer computer : page.getPage()) {
            System.out.println(computer);
        }
    }

    /**
     * Affiche la page de computer avec leur id et nom.
     * @param page : page de Computer
     */
    public static void lireListComputers(Page<Computer> page) {
        System.out.println("Liste Computer : " + page.getNbPage() + " element : " + page.getNbElementPage());
        for (Computer computer : page.getPage()) {
            System.out.println("id : " + computer.getId() + " name : " + computer.getName());
        }
    }

    /**
     * Afficher la liste des companies.
     * @param page : page de Company
     */
    public static void lireListCompanies(Page<Company> page) {
        System.out.println("Liste Company : Page " + page.getNbPage() + " element : " + page.getNbElementPage());
        for (Company company : page.getPage()) {
            System.out.println("id : " + company.getId() + " name : " + company.getName());
        }
    }

    /**
     * Affiche si un changement dans la base de données à été effectué.
     * @param reponse : nombre de changement effectuer
     */
    public static void lireValidationChangement(boolean reponse) {
        if (reponse) {
            System.out.println("Changement effectué");
        } else {
            System.out.println("Changement non effectué");
        }
    }

    /**
     * Affiche un message indiquant que aucune donnée n'a été récupérée.
     */
    public static void lireAucuneDonnee() {
        System.out.println("Aucune donnée ne correspond à votre recherche");
    }

    /**
     * Affiche le message d'erreur du programme.
     * @param erreur : message d'erreur
     */
    public static void lireErreur(String erreur) {
        System.out.println("Une erreur s'est produite dans la couche suivante : " + erreur);
    }
}
