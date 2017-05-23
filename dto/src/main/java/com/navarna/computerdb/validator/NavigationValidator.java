package com.navarna.computerdb.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.navarna.computerdb.dto.NavigationDashboardDTO;

public class NavigationValidator implements ConstraintValidator<VerificationNavigation, NavigationDashboardDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NavigationValidator.class);

    @Override
    public void initialize(VerificationNavigation arg0) {
    }

    @Override
    public boolean isValid(NavigationDashboardDTO navigation, ConstraintValidatorContext context) {
        LOGGER.info("-------->isValide(navigation, validatorContext) args: " + navigation);
        if(AttributVide(navigation)) {
            return false;
        }
        return verificationNbElement(navigation.getNbElement()) && verificationSearch(navigation.getSearch())
                && verificationType(navigation.getType()) && verificationOrder(navigation.getOrder());
    }

    /**
     * Indique si les attribut en string son vide.
     * @param navigation
     * @return boolean : true ou false
     */
    public static boolean AttributVide(NavigationDashboardDTO navigation) {
        return ((navigation.getOrder()==null) || (navigation.getSearch()==null)||(navigation.getType()==null));
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
     * Verifie la valeur de typeSearch.
     * @param typeSearch : type de la recherche
     * @return boolean : true or false
     */
    public static boolean verificationType(String type) {
        LOGGER.info("-------->verificationTypeSearch(typeSearch) args: " + type);
        return ((type.equals("id")) ||(type.equals("name")) || (type.equals("company.name")) || (type.equals("discontinued"))
                || (type.equals("introduced")));
    }

    /**
     * Verification de la valeur de name et typeSearch.
     * @param name : nom entrer en recherche
     * @return boolean : true or false
     */
    public static boolean verificationSearch(String name) {
        LOGGER.info("-------->verificationSearch(name,typeSearch) args: " + name);
        if (name.equals("")) {
            return true;
        }
        return name.equals(name.replaceAll("[^(\\d\\s\\w\\.\\-)]|_", " "));

    }

    /**
     * Verifie la valeur de order.
     * @param order : si on a besoin de order
     * @return boolean : true or false
     */
    public static boolean verificationOrder(String order) {
        return ((order.equals("ASC")) || (order.equals("DESC")));
    }
}
