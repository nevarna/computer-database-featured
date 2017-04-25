package com.navarna.computerdb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerException.class);

    /**
     * Constructeur avec seulement un message.
     * @param message : message de l'erreur
     */
    public ControllerException(String message) {
        super(message);
        LOGGER.debug(message);
    }

    /**
     * Constructeur avec seulement la cause de l'erreur.
     * @param cause : erreur du programme
     */
    public ControllerException(Throwable cause) {
        super(cause);
        LOGGER.debug(cause.getMessage());
    }

    /**
     * Constructeur avec 2 paramêtres : la message et la cause.
     * @param message : message de l'erreur
     * @param cause : erreur du programme
     */
    public ControllerException(String message, Throwable cause) {
        super(message, cause);
        LOGGER.debug(message + "\n" + cause.getMessage());
    }

    /**
     * Constructeur à 4 éléments.
     * @param message : massage de l'erreur
     * @param cause : erreur du programe
     * @param enableSuppression : indique si l'exception est suppressé
     * @param writableStackTrace : indique si elle est écrite
     */
    public ControllerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        LOGGER.debug(message + "\n" + cause.getMessage());
    }

}
