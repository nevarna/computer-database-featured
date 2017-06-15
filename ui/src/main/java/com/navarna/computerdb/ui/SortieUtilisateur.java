package com.navarna.computerdb.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.navarna.computerdb.dto.CompanyDTO;
import com.navarna.computerdb.dto.ComputerDTO;
import com.navarna.computerdb.dto.PageCompanyDTO;
import com.navarna.computerdb.dto.PageComputerDTO;

public class SortieUtilisateur {
    private static final Logger LOGGER = LoggerFactory.getLogger(SortieUtilisateur.class);

    /**
     * Affiche en console les détails d'un computer.
     * @param computer : computer à afficher
     */
    public static void lireDetailsComputer(ComputerDTO computer) {
        LOGGER.info("-------->lireDetailsComputer(computer) args: " + computer);
        System.out.println("Détail computer : " + computer);
    }

    /**
     * Affiche la page de computer avec leurs détails.
     * @param page : page de Computer
     */
    public static void lireDetailsComputers(PageComputerDTO page) {
        LOGGER.info("-------->lireDetailsComputers(page) args: " + page);
        System.out.println("Detail computers");
        for (ComputerDTO computer : page.getPage()) {
            System.out.println(computer);
        }
    }

    /**
     * Affiche la page de computer avec leur id et nom.
     * @param page : page de Computer
     */
    public static void lireListComputers(PageComputerDTO page) {
        LOGGER.info("-------->lireListComputers(page) args: " + page);
        System.out.println("Liste Computer : " + page.getNbPage() + " element : " + page.getNbElementPage());
        for (ComputerDTO computer : page.getPage()) {
            System.out.println("id : " + computer.getId() + " name : " + computer.getName());
        }
    }

    /**
     * Afficher la liste des companies.
     * @param page : page de Company
     */
    public static void lireListCompanies(PageCompanyDTO page) {
        LOGGER.info("-------->lireListCompanies(page) args: " + page);
        System.out.println("Liste Company : Page " + page.getNbPage() + " element : " + page.getNbElementPage());
        for (CompanyDTO company : page.getPage()) {
            System.out.println("id : " + company.getId() + " name : " + company.getName());
        }
    }

    /**
     * Affiche si un changement dans la base de données à été effectué.
     * @param reponse : nombre de changement effectuer
     */
    public static void lireValidationChangement(boolean reponse) {
        LOGGER.info("-------->lireValidationChangement(reponse) args: " + reponse);
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
        LOGGER.info("-------->lireAucuneDonnee()");
        System.out.println("Aucune donnée ne correspond à votre recherche");
    }

    /**
     * Affiche le message d'erreur du programme.
     * @param erreur : message d'erreur
     */
    public static void lireErreur(String erreur) {
        LOGGER.info("-------->lireErreur(erreur)");
        System.out.println("Une erreur s'est produite dans la couche suivante : " + erreur);
    }
}
