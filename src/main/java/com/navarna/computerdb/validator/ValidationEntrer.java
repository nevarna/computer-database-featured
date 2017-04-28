package com.navarna.computerdb.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ValidationEntrer {

    /**
     * Vérifie que l'heure soit correct.
     * @param temps : un string contenant l'heure
     * @return boolean : si oui ou non l'
     */
    public static boolean verificationHeure(String temps) {
        String[] tempsDecouper = temps.split(":");
        if (tempsDecouper.length != 3) {
            return false;
        }
        int heure = stringEnIntPositif(tempsDecouper[0]);
        int minute = stringEnIntPositif(tempsDecouper[1]);
        int seconde = stringEnIntPositif(tempsDecouper[2]);
        if ((heure < 0) || (heure > 23) || (minute < 0) || (minute > 59) || (seconde < 0) || (seconde > 59)) {
            return false;
        }
        return true;
    }

    /**
     * Vérifie si la date est correcte.
     * @param dateCouper : tableau de string représentant une date decouper en
     *            date et heure.
     * @return boolean : reponse si oui ou non la date est correct
     */
    public static boolean verificationDate(String[] dateCouper) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);
        try {
            df.parse(dateCouper[0]);
        } catch (ParseException pe) {
            System.out.println("Date incorrect : " + dateCouper[0]);
            return false;
        }
        if (dateCouper.length == 2) {
            return verificationHeure(dateCouper[1]);
        }
        return true;
    }

    /*
     * Verifie si la date en String est correct.
     * 
     * @param date : date en type String
     * 
     * @return boolean : true or false
     */
    public static boolean verificationFormatDate(String date) {
        String[] dateDecouper = date.split(" ");
        if ((dateDecouper.length > 2) || (dateDecouper.length < 0)) {
            return false;
        } else {
            return verificationDate(dateDecouper);
        }
    }

    /**
     * transforme un String en int.
     * 
     * @param nombre : nombre en ype String
     * @return int : le nombre en type int -> -1 si le nombre n'est pas correct
     */
    public static int stringEnIntPositif(String nombre) {
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
    public static boolean entrerValide(String name, String introduced, String discontinued, String idCompany) {
        if (name == null) {
            return false;
        }
        if (!verificationFormatDate(introduced)) {
            return false;
        }
        if (!verificationFormatDate(discontinued)) {
            return false;
        }
        if (stringEnIntPositif(idCompany) == -1) {
            return false;
        }
        return true;
    }

}
