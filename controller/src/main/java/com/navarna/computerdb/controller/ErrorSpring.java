package com.navarna.computerdb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorSpring {
    /**
     * Navigation POST de l'url /erreur.
     * @param httpRequest : httpRequest demander par l'utilisateur
     * @param reponse : httpReponse de la requête
     * @return model : le model contenant les attribut et l'adresse de la page
     *         jsp
     */
    @RequestMapping(value = "erreur", method = RequestMethod.GET)
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest, HttpServletResponse reponse,
            @RequestParam(value = "message", required = false) String message) {
        ModelAndView errorPage = new ModelAndView("errors");
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);
        switch (httpErrorCode) {
        case 400:
            errorMsg = "Error Code: 400. Bad Request";
            break;
        case 401:
            errorMsg = "Error Code: 401. Unauthorized";
            break;
        case 404:
            errorMsg = "Error Code: 404. Resource not found";
            break;
        case 405:
            errorMsg = "Error 405:  Access Denied";
            break;
        case 500:
            errorMsg = "Error Code: 500. Internal Server Error";
            break;
        default:
            errorMsg = "Error on page";
        }
        if (message != null) {
            errorMsg += message;
        }
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }

    /**
     * recupérer le code erreur de la requête.
     * @param httpRequest : requête du client
     * @return l'erreur qui s'est produite
     */
    private int getErrorCode(HttpServletRequest httpRequest) {
        try {
            return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
        } catch (ClassCastException | NullPointerException c) {
            return 0;
        }
    }

}
