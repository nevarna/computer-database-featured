package com.navarna.computerdb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.navarna.computerdb.dto.ComputerDTO;
import com.navarna.computerdb.exception.ControllerException;
import com.navarna.computerdb.mapper.TransformationToDTO;
import com.navarna.computerdb.model.Page;
import com.navarna.computerdb.service.ServiceComputerImpl;
import com.navarna.computerdb.validator.ValidationNavigation;

@Controller
@RequestMapping("/dashboard")
public class DashboardSpring {

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardSpring.class);
    @Autowired
    private ServiceComputerImpl servComputer;

    /**
     * Navigation GET de l'url /dashboard
     * @param numPage : numero de la page
     * @param nbElement : nombre d'élément par page
     * @param name : nom de recherche
     * @param order : ordre de tri
     * @param typeSearch : type de recherche/ tri
     * @return model : le model contenant les attribut et l'adresse de la page
     *         jsp
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getRequest(@RequestParam(value = "page", defaultValue = "0") Integer numPage,
            @RequestParam(value = "nbElement", defaultValue = "10") Integer nbElement,
            @RequestParam(value = "search", defaultValue = "") String name,
            @RequestParam(value = "order", defaultValue = "ASC") String order,
            @RequestParam(value = "type", defaultValue = "computer.id") String typeSearch) {
        LOGGER.info("-------->getRequest(numPage,nbElement,name,order,typeSearch) args: " + numPage + " - " + nbElement
                + " - " + name + " - " + order + " - " + typeSearch);
        if (!ValidationNavigation.verificationPage(numPage)) {
            numPage = 0;
        }
        if (!ValidationNavigation.verificationNbElement(nbElement)) {
            nbElement = 10;
        }
        if (ValidationNavigation.verificationSearch(name, typeSearch)) {
            name = ValidationNavigation.enleverCaractereInterdit(name);
        } else {
            name = "";
        }
        if (!ValidationNavigation.verificationOrder(order)) {
            order = "ASC";
        }
        if (!ValidationNavigation.verificationTypeSearch(typeSearch)) {
            typeSearch = "computer.id";
        }
        Page<ComputerDTO> pageComputer = creationListe(name, typeSearch, order, numPage, nbElement);
        int totalElement = compterElement(name, typeSearch);
        ModelAndView model = new ModelAndView("dashboard");
        ecrireAttribute(model, pageComputer, numPage, name, typeSearch, totalElement, nbElement);
        return model;
    }

    /**
     * Navigation POST de l'url /dashboard
     * @param selection : indice de computer à supprimer
     * @return model : le model contenant les attribut et l'adresse de la page
     *         jsp
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView postRequest(@RequestParam(value = "selection", defaultValue = "") String selection) {
        LOGGER.info("-------->postRequest(selection) args: " + selection);
        String[] tableauIdDelete = lireParametrePost(selection);
        demandeSuppression(tableauIdDelete);
        return getRequest(10, 10, "", "ASC", "computer.id");
    }

    /**
     * Lis les paramêtre reçu de manière POST et les utilise.
     * @param request : request reçu par le servlet
     * @return String[] : tableau contenant les indices à supprimer.
     */
    private String[] lireParametrePost(String selection) {
        LOGGER.info("-------->lireParametrePost(request) args: " + selection);
        return selection.split(",");
    }

    /**
     * Demande de suppression par l'utilisateur.
     * @param tableauIdDelete : tableau contenant les indices des computers à
     *            supprimer
     */
    private void demandeSuppression(String[] tableauIdDelete) {
        LOGGER.info("-------->demande suppresion(tableauIdDelete)");
        try {
            int taille = tableauIdDelete.length;
            if (taille != 0) {
                if (taille == 1) {
                    long idSupprimer = Long.parseLong(tableauIdDelete[0]);
                    servComputer.delete(idSupprimer);
                } else {
                    long[] tableauIdDeleteLong = new long[taille];
                    for (int i = 0; i < taille; i++) {
                        tableauIdDeleteLong[i] = Long.parseLong(tableauIdDelete[i]);
                    }
                    servComputer.deleteMultiple(tableauIdDeleteLong);
                }
            }
        } catch (NumberFormatException | NullPointerException ne) {
            throw new ControllerException(" Erreur delete mode ", ne);
        }
    }

    /**
     * Créer la liste à afficher dans pageComputer.
     * @param name : nom ecris en arguments
     * @param typeSearch : si on cherche une companies ou un computer
     * @param order : type de l'ordre ASC ou DESC
     * @param numPage : numero de la page
     * @param nbElement : nombre d'élément par page
     * @return Page<ComputerDTO> : page contenant la liste à afficher
     */
    private Page<ComputerDTO> creationListe(String name, String typeSearch, String order, int numPage, int nbElement) {
        LOGGER.info("-------->creationListe(name,typeSearch,order,numPage,nbElement) args : " + name + " - "
                + typeSearch + " - " + order + " - " + numPage + " - " + nbElement);
        Page<ComputerDTO> pageComputer = null;
        if (!ValidationNavigation.verificationSearch(name, typeSearch)) {
            pageComputer = TransformationToDTO
                    .pageComputerToPageDTO(servComputer.liste(numPage, nbElement, typeSearch, order));
        } else {
            if (typeSearch.equals("computer.name")) {
                pageComputer = TransformationToDTO
                        .pageComputerToPageDTO(servComputer.findByName(name, numPage, nbElement, typeSearch, order));
            } else {
                pageComputer = TransformationToDTO
                        .pageComputerToPageDTO(servComputer.findByCompany(name, numPage, nbElement, typeSearch, order));
            }
        }
        return pageComputer;
    }

    /**
     * Compte le nombre d'élément de la liste complete.
     * @param name : nom entrer dans la recherche
     * @param typeSearch : si on cherche une company ou un computeur
     * @return int : compteur d'élément.
     */
    private int compterElement(String name, String typeSearch) {
        LOGGER.info("-------->compterElement(name, typeSearch) args : " + name + " - " + typeSearch);
        int totalElement = 0;
        if (!ValidationNavigation.verificationSearch(name, typeSearch)) {
            totalElement = servComputer.count();
        } else {
            if (typeSearch.equals("Computer")) {
                totalElement = servComputer.countWithName(name);
            } else {
                totalElement = servComputer.countWithNameCompany(name);
            }
        }
        return totalElement;
    }

    /**
     * Ecrit les attributs dont a besoin le fichier JSP.
     * @param model : model à retourner
     * @param pageComputer : page contenant la liste de computer
     * @param numPage : numero de page
     * @param name : nom de la recherche
     * @param typeSearch : type de la recherche
     * @param totalElement : nombre total d'élément de la recherche
     * @param nbElement : nombre d'element par page
     */
    public void ecrireAttribute(ModelAndView model, Page<ComputerDTO> pageComputer, int numPage, String name,
            String typeSearch, int totalElement, int nbElement) {
        LOGGER.info(
                "-------->ecrireAttribut(model,pageComputer,numPage,name,typeSearch,totalElement,nbElement) args : undefined - undefined - "
                        + numPage + " -" + name + " - " + typeSearch + " - " + totalElement + " - " + nbElement);
        model.addObject("computers", pageComputer);
        model.addObject("pageCurrente", numPage + 1);
        model.addObject("nbElement", nbElement);
        if (ValidationNavigation.verificationSearch(name, typeSearch)) {
            model.addObject("name", name);
            model.addObject("research", typeSearch);
        }
        model.addObject("totalElement", totalElement);
        model.addObject("maxPage", totalElement / nbElement);
    }
}
