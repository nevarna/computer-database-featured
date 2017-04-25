package com.navarna.computerdb.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.navarna.computerdb.dto.ComputerDTO;
import com.navarna.computerdb.mapper.TransformationToDTO;
import com.navarna.computerdb.model.Page;
import com.navarna.computerdb.service.ServiceComputerImpl;
import com.navarna.computerdb.validator.ValidationNavigation;

/**
 * Servlet implementation class Dashboard
 */
public class Dashboard extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ServiceComputerImpl servComputer = new ServiceComputerImpl();
    private int numPage = 0;
    private int nbElement = 20;
    private String name;
    private String typeSearch;
    private Page<ComputerDTO> pageComputer = null;
    private int totalElement = 0;

    /**
     * Demande la validation de l'argument et set numPage.
     * @param page : numero de page
     */
    public void changerPage(String page) {
        if(ValidationNavigation.verificationPage(page, totalElement / nbElement)) {
            numPage = Integer.parseInt(page) -1;
        }
        else {
            numPage = 0;
        }
    }

    /**
     * Demande la validation de l'argument et set nbElement.
     * @param nombreElement : nombre d'élément par page
     */
    public void changerNbElement(String nombreElement) {
        if(ValidationNavigation.verificationNbElement(nombreElement)) {
            nbElement = Integer.parseInt(nombreElement);
        }
    }

    /**
     * Vérifie l'existence des attribut de recherche et les vérifient si ils
     * existent.
     */
    public void argumentsSearch() {
        if (name == "") {
            name = null;
            typeSearch = null;
        }
        if (typeSearch != null) {
            ValidationNavigation.verificationTypeSearch(typeSearch);
        }
    }

    /**
     * Transforme les valeurs name et typeSearch en un seul String de forme get.
     * @return String : partie de l'url avec la recherche
     */
    public String transformeSearchEnFormeGet() {
        String retour = "";
        retour = name.replace("+", "%2B");
        retour = name.replace(" ", "+");
        retour = "?search=" + retour + "&type=" + typeSearch;
        return retour;
    }

    /**
     * Lit les paramêtre reçu en de manière GET.
     * @param request : request reçu par le servlet
     */
    public void lireParametreGet(HttpServletRequest request) {
        String page = request.getParameter("page");
        changerPage(page);
        String nombreElement = request.getParameter("nbElement");
        changerNbElement(nombreElement);
        name = request.getParameter("search");
        typeSearch = request.getParameter("type");
        argumentsSearch();
    }

    /**
     * Lis les paramêtre reçu de manière POST.
     * @param request : request reçu par le servlet
     */
    public void lireParametrePost(HttpServletRequest request) {
        String[] tableauIdDelete = request.getParameter("selection").split(",");
        try {
            for (String numeroDelete : tableauIdDelete) {
                int idDelete = Integer.parseInt(numeroDelete);
                servComputer.delete(idDelete);
            }
        } catch (NumberFormatException | NullPointerException ne) {
            throw new ControllerException(" Erreur delete mode ", ne);
        }
    }

    /**
     * Créer la liste à afficher dans pageComputer.
     */
    public void creationListe() {
        if (name == null) {
            pageComputer = TransformationToDTO.pageComputerToPageDTO(servComputer.liste(numPage, nbElement));
            totalElement = servComputer.countComputer();
        } else {
            if (typeSearch.equals("Computer")) {
                pageComputer = TransformationToDTO
                        .pageComputerToPageDTO(servComputer.showName(name, numPage, nbElement));
                totalElement = servComputer.countComputerName(name);
            } else {
                pageComputer = TransformationToDTO
                        .pageComputerToPageDTO(servComputer.showCompany(name, numPage, nbElement));
                totalElement = servComputer.countComputerNameCompany(name);
            }
        }
    }

    /**
     * Ecrit les attributs donnt a besoin le fichier JSP.
     * @param request : request reçu par le servlet
     */
    public void ecrireAttribute(HttpServletRequest request) {
        request.setAttribute("computers", pageComputer);
        request.setAttribute("pageCurrente", numPage+1);
        if (name != null) {
            request.setAttribute("name", name);
            request.setAttribute("research", transformeSearchEnFormeGet());
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
        lireParametreGet(request);
        creationListe();
        ecrireAttribute(request);
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
        lireParametrePost(request);
        doGet(request, response);
    }

}
