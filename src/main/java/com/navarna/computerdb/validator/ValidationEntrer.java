package com.navarna.computerdb.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ValidationEntrer {
    /*
    * Verifie si la date en String est correct.
    * @param timestamp : timeStamp en type String
    * @return boolean : true or false
    */
   public static boolean verifeFormatTimestamp(String timestamp) {
       boolean retour = false;
       String[] decoup = timestamp.split(" ");
       if (decoup.length > 2) {
           return retour;
       }
       if (decoup.length > 0) {
           SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
           df.setLenient(false);
           try {
               df.parse(decoup[0]);
           } catch (ParseException pe) {
               System.out.println("Date incorrect");
               return retour;
           }
           if (decoup.length == 2) {
               String[] temps = decoup[1].split(":");
               if (temps.length != 3) {
                   return retour;
               } else {
                   int heure = stringEnInt(temps[0]);
                   if ((heure < 0) || (heure > 23)) {
                       return retour;
                   }
                   int minute = stringEnInt(temps[1]);
                   if ((minute < 0) || (minute > 59)) {
                       return retour;
                   }
                   int seconde = stringEnInt(temps[2]);
                   if ((seconde < 0) || (seconde > 59)) {
                       return retour;
                   }
                   retour = true;
               }
           } else {
               retour = true;
           }
       } else {
           return retour;
       }
       return retour;
   } 
   
   /**
    * transforme un String en int.
    * @param nombre : nombre en ype String
    * @return int : le nombre en type int
    */
   public static int stringEnInt(String nombre) {
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
   
   public static int EntrerValide (String name , String introduced , String discontinued , String idCompany) {
       if(name == null) {
           return -1;
       }
       if(!verifeFormatTimestamp(introduced)) {
           return -1;
       }
       if(!verifeFormatTimestamp(discontinued)) {
           return -1 ;
       }
       return stringEnInt(idCompany);
   }

}
