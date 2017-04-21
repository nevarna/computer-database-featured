package com.navarna.computerdb.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    /**
     * lancement du programme : envoie du client sur la partie CLI.
     * @param args : Aucun
     */
    public static void main(String[] args) {
        LOGGER.debug("Début du programme");
        EntrerUtilisateur entrerUtilisateur = new EntrerUtilisateur();
        entrerUtilisateur.choixPrincipal();
        LOGGER.debug("fin du programme");
    }
}