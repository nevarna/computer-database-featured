package com.navarna.computerdb.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationEntrer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationEntrer.class);

    /**
     * Vérifie si la date est correcte.
     * @param date : tableau de string représentant une date decouper en date et
     *            heure.
     * @return boolean : reponse si oui ou non la date est correct
     */
    public static boolean verificationDate(String date) {
        LOGGER.info("-------->verificationDate(date) args: " + date);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);
        try {
            df.parse(date);
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    /**
     * Vérifie si la date est correcte.
     * @param date : tableau de string représentant une date decouper en date et
     *            heure.
     * @return boolean : reponse si oui ou non la date est correct
     */
    public static boolean verificationDateEnvers(String date) {
        LOGGER.info("-------->verificationDateEnvers(date) args: " + date);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        df.setLenient(false);
        try {
            df.parse(date);
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    /**
     * Verifie si la date en String est correct.
     * @param date : date en type String
     * @return boolean : true or false
     */
    public static boolean verificationFormatDate(String date) {
        LOGGER.info("-------->verificationFormatDate(date) args: " + date);
        String[] dateDecouper = date.split(" ");
        if ((dateDecouper.length > 1) || (dateDecouper.length < 0)) {
            return false;
        } else {
            return verificationDate(date) || verificationDateEnvers(date);
        }
    }

    /**
     * transforme la date en format LocalDate.
     * @param date : date en type String
     * @return LocalDate : une date correct correspndant à l'entrer
     */
    public static LocalDate dateController(String date) {
        LOGGER.info("-------->dateController(date) args: " + date);
        LocalDate dateCorrect = null;
        try {
            if (verificationFormatDate(date)) {
                if (verificationDate(date)) {
                    dateCorrect = LocalDate.parse(date);
                } else {
                    String[] dateSplit = date.split("-");
                    dateCorrect = LocalDate.of(Integer.parseInt(dateSplit[2]), Integer.parseInt(dateSplit[1]),
                            Integer.parseInt(dateSplit[0]));
                }
            }
        } catch (DateTimeParseException | NumberFormatException ne) {
            return dateCorrect;
        }
        return dateCorrect;
    }

    /**
     * Vérifie la logique entre les dates.
     * @param introduced : date de debut
     * @param discontinued : date de fin
     * @return boolean : si les 2 valeurs sont logique entre elles
     */
    public static boolean dateLogique(LocalDate introduced, LocalDate discontinued) {
        LOGGER.info("-------->dateLogique(introduced,discontinued) args: " + introduced + " - " + discontinued);
        if ((introduced == null) && (discontinued == null)) {
            return true;
        }
        if ((introduced == null) && (discontinued != null)) {
            return false;
        }
        if ((introduced != null) && (discontinued == null)) {
            return true;
        }
        if ((introduced == discontinued) || (introduced.equals(discontinued))) {
            return true;
        }
        return introduced.isBefore(discontinued);
    }

    /**
     * transforme un String en int.
     * @param nombre : nombre en ype String
     * @return int : le nombre en type int -> -1 si le nombre n'est pas correct
     */
    public static int stringEnIntPositif(String nombre) {
        LOGGER.info("-------->stringEnIntPositif(nombre) args: " + nombre);
        int retour = -1;
        try {
            retour = Integer.valueOf(nombre);
            if (retour < 0) {
                retour = -1;
            }
        } catch (NumberFormatException ne) {
            System.out.println("Ce n'est pas un nombre");
        }
        return retour;
    }

    /**
     * Vérifie si l'entrée représantant un computer est valide.
     * @param name : nom du computer
     * @param introduced : date de la mise en marche
     * @param discontinued : date de l'arret
     * @param idCompany : id de la company
     * @return boolean : true or false
     */
    public static boolean entrerValide(String name, LocalDate introduced, LocalDate discontinued, long idCompany) {
        LOGGER.info("-------->entrerValide(name,introduced,discontinued,idCompany) args: " + name + " - " + introduced
                + " - " + discontinued + " - " + idCompany);
        return (!name.equals("")) && (dateLogique(introduced, discontinued)) && (idCompany != -1);
    }

}
