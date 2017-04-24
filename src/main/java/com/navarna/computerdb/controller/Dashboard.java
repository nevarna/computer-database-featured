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

/**
 * Servlet implementation class Dashboard
 */
public class Dashboard extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ServiceComputerImpl servComputer = new ServiceComputerImpl();
    private int numPage = 0;
    private int nbElement = 20;
    private String name;
    private Page<ComputerDTO> pageComputer = null;
    private int totalElement = 0;

    public void changerPage(String page) {
        try {
            int numero = page == null ? -2 : Integer.parseInt(page);
            switch (numero) {
            case -2:
                numPage = 0;
                break;
            case -1:
                numPage = numero < 0 ? 0 : numPage - 1;
                break;
            case 0:
                numPage = numero > 5 ? 5 : numPage + 1;
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                numPage = numero - 1;
                break;
            default:
                throw new ControllerException("Le nombre parametre de page n'est pas correct");

            }
        } catch (NumberFormatException ne) {
            throw new ControllerException("l'argument parametre de page n'est pas un nombre", ne);
        }
    }

    public void changerNbElement(String nombreElement) {
        try {
            int numero = nombreElement == null ? 0 : Integer.parseInt(nombreElement);
            switch (numero) {
            case 0:
                break;
            case 10:
            case 50:
            case 100:
                nbElement = numero;
                break;
            default:
                throw new ControllerException("Le nombre parametre de page n'est pas correct");

            }
        } catch (NumberFormatException ne) {
            throw new ControllerException("l'argument parametre de page n'est pas un nombre", ne);
        }
    }

    public String transforme() {
        String retour = "";
        retour = name.replace("+", "%2B");
        retour = name.replace(" ", "+");
        retour = "?search=" + retour;
        return retour;
    }

    public void lireParametre(HttpServletRequest request) {
        String page = request.getParameter("page");
        changerPage(page);
        String nombreElement = request.getParameter("nbElement");
        changerNbElement(nombreElement);
        name = request.getParameter("search");
        if (name == "") {
            name = null;
        }
    }

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

    public void creationListe() {
        if (name == null) {
            pageComputer = TransformationToDTO.pageComputerToPageDTO(servComputer.liste(numPage, nbElement));
            totalElement = servComputer.countComputer();
        } else {
            pageComputer = TransformationToDTO.pageComputerToPageDTO(servComputer.show(name, numPage, nbElement));
            totalElement = servComputer.countComputerName(name);
        }
    }

    public void ecrireAttribute(HttpServletRequest request) {
        request.setAttribute("computers", pageComputer);
        if (name != null) {
            request.setAttribute("name", name);
            request.setAttribute("research", transforme());
        }
        request.setAttribute("totalElement", totalElement);
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher fichierJSP = this.getServletContext().getRequestDispatcher("/resources/views/dashboard.jsp");
        lireParametre(request);
        creationListe();
        ecrireAttribute(request);
        fichierJSP.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        lireParametrePost(request);
        doGet(request, response);
    }

}
