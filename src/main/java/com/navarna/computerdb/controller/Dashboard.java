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

    /**
     * Demande la validation de l'argument et set numPage.
     * @param page : numero de page recu en parametre
     * @return int : numero de page 
     */
    public int changerPage(String page) {
        if(ValidationNavigation.verificationPage(page)) {
            return Integer.parseInt(page) -1;
        }
        else {
            return 0;
        }
    }

    /**
     * Demande la validation de l'argument et set nbElement.
     * @param nombreElement : nombre d'élément par page reçu en paramêtre
     * @return int : nombre d'élément par page
     */
    public int changerNbElement(String nombreElement) {
        if(ValidationNavigation.verificationNbElement(nombreElement)) {
            return Integer.parseInt(nombreElement);
        }
        return 10;
    }

    /**
     * Lit les paramêtre reçu en de manière GET et les utilisent.
     * @param request : request reçu par le servlet
     */
    public void UtilisationParametreGet(HttpServletRequest request) {
        String page = request.getParameter("page");
        int numPage = changerPage(page);
        String nombreElement = request.getParameter("nbElement");
        int nbElement = changerNbElement(nombreElement);
        String name = request.getParameter("search");
        String typeSearch = request.getParameter("type");
        int totalElement = compterElement(name, typeSearch);
        Page<ComputerDTO> pageComputer = creationListe(name,typeSearch, numPage, nbElement);
        ecrireAttribute(request, pageComputer, numPage, name, typeSearch, totalElement, nbElement);
    }

    /**
     * Lis les paramêtre reçu de manière POST et les utilise.
     * @param request : request reçu par le servlet
     */
    public void UtilisationParametrePost(HttpServletRequest request) {
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
     * @param name : nom ecris en arguments 
     * @param typeSearch : si on cherche une companies ou un computer
     * @param numPage : numero de la page
     * @param nbElement : nombre d'élément par page
     * @return Page<ComputerDTO> : page contenant la liste à afficher 
     */
    public Page<ComputerDTO> creationListe(String name,String typeSearch, int numPage, int nbElement) {
        Page<ComputerDTO> pageComputer = null; 
        if (!ValidationNavigation.verificationSearch(name, typeSearch)) {
            pageComputer = TransformationToDTO.pageComputerToPageDTO(servComputer.liste(numPage, nbElement));
        } else {
            if (typeSearch.equals("Computer")) {
                pageComputer = TransformationToDTO
                        .pageComputerToPageDTO(servComputer.showName(name, numPage, nbElement));
            } else {
                pageComputer = TransformationToDTO
                        .pageComputerToPageDTO(servComputer.showCompany(name, numPage, nbElement));
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
    public int compterElement (String name,String typeSearch) {
        int totalElement = 0 ;
        if (!ValidationNavigation.verificationSearch(name, typeSearch)) {
            totalElement = servComputer.countComputer();
        } else {
            if (typeSearch.equals("Computer")) {
                totalElement = servComputer.countComputerName(name);
            } else {
                totalElement = servComputer.countComputerNameCompany(name);
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
    public void ecrireAttribute(HttpServletRequest request,Page<ComputerDTO> pageComputer, int numPage, String name
            , String typeSearch, int totalElement, int nbElement) {
        request.setAttribute("computers", pageComputer);
        request.setAttribute("pageCurrente", numPage+1);
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
        UtilisationParametreGet(request);
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
        UtilisationParametrePost(request);
        doGet(request, response);
    }

}
