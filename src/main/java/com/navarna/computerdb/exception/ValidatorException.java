package com.navarna.computerdb.exception;

public class ValidatorException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur avec seulement un message.
     * @param message : message de l'erreur
     */
    public ValidatorException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructeur avec seulement la cause de l'erreur.
     * @param cause : erreur du programme
     */
    public ValidatorException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructeur avec 2 paramêtres : la message et la cause.
     * @param message : message de l'erreur
     * @param cause : erreur du programme
     */
    public ValidatorException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructeur à 4 éléments.
     * @param message : massage de l'erreur
     * @param cause : erreur du programe
     * @param enableSuppression : indique si l'exception est suppressé
     * @param writableStackTrace : indique si elle est écrite
     */
    public ValidatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

}
