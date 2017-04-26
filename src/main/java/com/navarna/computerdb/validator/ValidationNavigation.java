package com.navarna.computerdb.validator;

public class ValidationNavigation {

    /**
     * Verifie l'indice de la page.
     * @param page : page demandé
     * @param maxPage : numeroMaxDePage
     * @return boolean : true or false
     */
    public static boolean verificationPage(String page) {
        try {
            int numero = page == null ? -2 : Integer.parseInt(page);
            if (numero > 0) {
                return true;
            } else {
                return false;
            }
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

    public static boolean verificationTypeSearch(String typeSearch) {
        if ((!typeSearch.equals("Computer")) && (!typeSearch.equals("Company"))) {
            return false;
        }
        return true;
    }
    
    public static boolean verificationSearch(String name,String typeSearch) {
        if(name == null) {
            return false;
        }
        else {
            if(name.equals("")) {
                return false;
            }
            return verificationTypeSearch(typeSearch);
        }
    }
}
