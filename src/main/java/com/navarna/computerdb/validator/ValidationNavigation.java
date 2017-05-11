package com.navarna.computerdb.validator;

import java.text.Normalizer;

import com.navarna.computerdb.exception.ValidatorException;

public class ValidationNavigation {

    /**
     * Verifie l'indice de la page.
     * @param page : page demandé
     * @return boolean : true or false
     */
    public static boolean verificationPage(String page) {
        try {
            int numero = page == null ? -2 : Integer.parseInt(page);
            return numero > 0;
        } catch (NumberFormatException ne) {
            throw new ValidatorException("l'argument parametre de page n'est pas un nombre", ne);
        }
    }

    /**
     * Vérifie la valeur de nombreElement demandé.
     * @param nombreElement : nombre d'élément demandé
     * @return boolean : true or false
     */
    public static boolean verificationNbElement(String nombreElement) {
        if (nombreElement == null) {
            return false;
        }
        switch (nombreElement) {
        case "10":
        case "50":
        case "100":
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
        name = Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
        name = name.replaceAll("<[^>]*>|\\d|\\W|_", " ");
        return name;
    }

    /**
     * Verifie la valeur de typeSearch.
     * @param typeSearch : type de la recherche
     * @return boolean : true or false
     */
    public static boolean verificationTypeSearch(String typeSearch) {
        if (typeSearch == null) {
            return false;
        }
        return ((typeSearch.equals("computer.name")) || (typeSearch.equals("company.name")) || (typeSearch.equals("discontinued"))
                || (typeSearch.equals("introduced")));
    }

    /**
     * Verification de la valeur de name et typeSearch.
     * @param name : nom entrer en recherche
     * @param typeSearch : type de la recherche
     * @return boolean : true or false
     */
    public static boolean verificationSearch(String name, String typeSearch) {
        if (name == null) {
            return false;
        } else {
            if (name.equals("")) {
                return false;
            }
            return verificationTypeSearch(typeSearch);
        }
    }

    /**
     * Verifie la valeur de order.
     * @param order : si on a besoin de order
     * @return boolean : true or false
     */
    public static boolean verificationOrder(String order) {
        if (order == null) {
            return false;
        }
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
