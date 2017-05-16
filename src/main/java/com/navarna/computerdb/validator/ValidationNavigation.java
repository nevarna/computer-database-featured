package com.navarna.computerdb.validator;

import java.text.Normalizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ValidationNavigation {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationNavigation.class);

    /**
     * Verifie l'indice de la page.
     * @param page : page demandé
     * @return boolean : true or false
     */
    public static boolean verificationPage(int page) {
        LOGGER.info("-------->verificationPage(page) args: " + page);
        return page > 0;
    }

    /**
     * Vérifie la valeur de nombreElement demandé.
     * @param nombreElement : nombre d'élément demandé
     * @return boolean : true or false
     */
    public static boolean verificationNbElement(int nombreElement) {
        LOGGER.info("-------->verificationNbElement(nombreElement) args: " + nombreElement);
        switch (nombreElement) {
        case 10:
        case 50:
        case 100:
            return true;
        default:
            return false;
        }
    }

    /**
     * Transforme un string et retourne ce string sans caractere special.
     * @param name : string recu en arguments
     * @return String : name sans caractere special ni accent
     */
    public static String enleverCaractereInterdit(String name) {
        LOGGER.info("-------->enleverCaractereInterdit(name) args: " + name);
        name = Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
        return name;
    }

    /**
     * Verifie la valeur de typeSearch.
     * @param typeSearch : type de la recherche
     * @return boolean : true or false
     */
    public static boolean verificationTypeSearch(String typeSearch) {
        LOGGER.info("-------->verificationTypeSearch(typeSearch) args: " + typeSearch);
        return ((typeSearch.equals("computer.name")) || (typeSearch.equals("company.name"))
                || (typeSearch.equals("discontinued")) || (typeSearch.equals("introduced")));
    }

    /**
     * Verification de la valeur de name et typeSearch.
     * @param name : nom entrer en recherche
     * @param typeSearch : type de la recherche
     * @return boolean : true or false
     */
    public static boolean verificationSearch(String name, String typeSearch) {
        LOGGER.info("-------->verificationSearch(name,typeSearch) args: " + name + " - " + typeSearch);
        if (name.equals("")) {
            return false;
        }
        return verificationTypeSearch(typeSearch);

    }

    /**
     * Verifie la valeur de order.
     * @param order : si on a besoin de order
     * @return boolean : true or false
     */
    public static boolean verificationOrder(String order) {
        switch (order) {
        case "none":
            return false;
        case "ASC":
            return true;
        case "DESC":
            return true;
        default:
            return false;
        }
    }
}
