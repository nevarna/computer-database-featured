package com.navarna.computerdb.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.navarna.computerdb.dto.ComputerDTO;
import com.navarna.computerdb.exception.ControllerException;
import com.navarna.computerdb.mapper.TransformationToDTO;
import com.navarna.computerdb.model.Page;
import com.navarna.computerdb.service.ServiceComputerImpl;
import com.navarna.computerdb.validator.ValidationNavigation;

/**
 * Servlet implementation class Dashboard
 */
public class Dashboard extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int NOMBRE_PARAMETRE = 5;
    private ServiceComputerImpl servComputer = new ServiceComputerImpl();

    /**
     * Lit les paramêtre reçu en de manière GET et les utilisent.
     * @param request : request reçu par le servlet
     * @return String[] : un tableau contenant les indices de manière ordonnées
     *         0 : numero de page 1 : nombre d'élément par page 2 : nom entrer
     *         en arguments du search 3 : type de la recherche computer ou
     *         company
     */
    private String[] lireParametreGet(HttpServletRequest request) {
        String[] parametres = new String[NOMBRE_PARAMETRE];
        parametres[0] = request.getParameter("page");
        parametres[1] = request.getParameter("nbElement");
        parametres[2] = request.getParameter("search");
        parametres[3] = request.getParameter("type");
        parametres[4] = request.getParameter("order");
        return parametres;
    }

    /**
     * Lis les paramêtre reçu de manière POST et les utilise.
     * @param request : request reçu par le servlet
     * @return String[] : tableau contenant les indices à supprimer.
     */
    private String[] lireParametrePost(HttpServletRequest request) {
        String selection = request.getParameter("selection");
        if (selection != null) {
            return selection.split(",");
        }
        return new String[0];
    }

    /**
     * Demande de suppression par l'utilisateur.
     * @param tableauIdDelete : tableau contenant les indices des computers à
     *            supprimer
     */
    private void demandeSuppression(String[] tableauIdDelete) {
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
     * @param numPage : numero de la page
     * @param nbElement : nombre d'élément par page
     * @return Page<ComputerDTO> : page contenant la liste à afficher
     */
    private Page<ComputerDTO> creationListe(String name, String typeSearch, String order, int numPage, int nbElement) {
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
     * @param request: request reçu par le servlet
     * @param pageComputer : page contenant la liste de computer
     * @param numPage : numero de page
     * @param name : nom de la recherche
     * @param typeSearch : type de la recherche
     * @param totalElement : nombre total d'élément de la recherche
     * @param nbElement : nombre d'element par page
     */
    public void ecrireAttribute(HttpServletRequest request, Page<ComputerDTO> pageComputer, int numPage, String name,
            String typeSearch, int totalElement, int nbElement) {
        request.setAttribute("computers", pageComputer);
        request.setAttribute("pageCurrente", numPage + 1);
        request.setAttribute("nbElement", nbElement);
        if (ValidationNavigation.verificationSearch(name, typeSearch)) {
            request.setAttribute("name", name);
            request.setAttribute("research", typeSearch);
        }
        request.setAttribute("totalElement", totalElement);
        request.setAttribute("maxPage", totalElement / nbElement);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher fichierJSP = this.getServletContext().getRequestDispatcher("/resources/views/dashboard.jsp");
        String[] parametres = lireParametreGet(request);
        int numPage = 0;
        if (ValidationNavigation.verificationPage(parametres[0])) {
            numPage = Integer.parseInt(parametres[0]);
        }
        int nbElement = 10;
        if (ValidationNavigation.verificationNbElement(parametres[1])) {
            nbElement = Integer.parseInt(parametres[1]);
        }
        String name = null;
        if (ValidationNavigation.verificationSearch(parametres[2], parametres[3])) {
            name = ValidationNavigation.EnleverCaractereInterdit(parametres[2]);
        }
        String order = "ASC";
        if (ValidationNavigation.verificationOrder(parametres[4])) {
            order = parametres[4];
        }
        String typeSearch = "computer.id";
        if (ValidationNavigation.verificationTypeSearch(parametres[3])) {
            typeSearch = parametres[3];
        }
        Page<ComputerDTO> pageComputer = creationListe(name, typeSearch, order, numPage, nbElement);
        int totalElement = compterElement(name, typeSearch);
        ecrireAttribute(request, pageComputer, numPage, name, typeSearch, totalElement, nbElement);
        fichierJSP.forward(request, response);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] tableauIdDelete = lireParametrePost(request);
        demandeSuppression(tableauIdDelete);
        doGet(request, response);
    }
}
