package com.navarna.computerdb.persistence;

public class DAOException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructeur avec seulement un message.
     * @param message : message de l'erreur
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructeur avec seulement la cause de l'erreur.
     * @param cause : erreur du programme
     */
    public DAOException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructeur avec 2 paramêtres : la message et la cause.
     * @param message : message de l'erreur
     * @param cause : erreur du programme
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructeur à 4 éléments.
     * @param message : massage de l'erreur
     * @param cause : erreur du programe
     * @param enableSuppression : indique si l'exception est suppressé
     * @param writableStackTrace : indique si elle est écrite
     */
    public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
