package com.navarna.computerdb.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.navarna.computerdb.persistence.ConnectionSpringConfig;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    /**
     * lancement du programme : envoie du client sur la partie CLI.
     * @param args : Aucun
     */
    public static void main(String[] args) {
        LOGGER.info("Début du programme");
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
                ConnectionSpringConfig.class);
        EntrerUtilisateur entrerUtilisateur = (EntrerUtilisateur) ctx.getBean(EntrerUtilisateur.class);
        entrerUtilisateur.choixPrincipal();
        ctx.close();
        LOGGER.info("fin du programme");
    }
}