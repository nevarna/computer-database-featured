package com.navarna.computerdb.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.navarna.computerdb.dto.CompanyDTO;
import com.navarna.computerdb.mapper.TransformationToDTO;
import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.service.ServiceCompanyImpl;
import com.navarna.computerdb.service.ServiceComputerImpl;
import com.navarna.computerdb.validator.ValidationEntrer;

public class AddComputer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int NOMBRE_PARAMETRE = 4;
    private ServiceComputerImpl servComputer = new ServiceComputerImpl();
    private ServiceCompanyImpl servCompany = new ServiceCompanyImpl();

    /**
     * Demande validation des entrées de l'utilisateur et demande au service
     * d'insérer un computer.
     * @param name : nom du computer
     * @param introduced : date de mise en marche
     * @param discontinued : date de mise en arret.
     * @param idCompany : id de la company du computer
     * @return int : nombre de ligne modifié par la requête
     */
    private boolean demandeInsert(String name, String introduced, String discontinued, String idCompany) {
        // A enlever plus tard
        introduced = "2000-05-02";
        discontinued = "2013-04-27";
        if (ValidationEntrer.entrerValide(name, introduced, discontinued, idCompany)) {
            LocalDate pIntroduced = LocalDate.parse(introduced);
            LocalDate pDiscontinued = LocalDate.parse(discontinued);
            int id = ValidationEntrer.stringEnIntPositif(idCompany);
            Company company = new Company.CompanyBuilder("null").setId(new Long(id)).build();
            Computer computer = new Computer.ComputerBuilder(name).setIntroduced(pIntroduced)
                    .setDiscontinued(pDiscontinued).setCompany(company).setId(0L).build();
            System.out.println("computer inserer : "+ computer);
            return servComputer.insert(computer);
        }
        return false;
    }

    /**
     * lit les paramêtre post de la requête et demande une insertion.
     * @param request : request reçu par le servlet
     * @return String[] : un tableau contenant les indices de manière ordonnées
     *         0 : nom du computer 1 : date de mise en marche 2 : date d'arret 3
     *         : id de la company
     */
    private String[] lireParametre(HttpServletRequest request) {
        String[] parametres = new String[NOMBRE_PARAMETRE];
        parametres[0] = request.getParameter("name");
        parametres[1] = request.getParameter("introduced");
        parametres[2] = request.getParameter("discontinued");
        parametres[3] = request.getParameter("idCompany");
        return parametres;
    }

    /**
     * écrit les attributs dont a besoin le fichier jsp.
     * @param request : request reçu par le servlet
     */
    public void ecrireAttribute(HttpServletRequest request) {
        ArrayList<CompanyDTO> informationCompany = TransformationToDTO
                .ArrayListCompanyToArrayListDTO(servCompany.listeComplete());
        request.setAttribute("listeCompany", informationCompany);
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher fichierJSP = this.getServletContext()
                .getRequestDispatcher("/resources/views/addComputer.jsp");
        ecrireAttribute(request);
        fichierJSP.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] parametres = lireParametre(request);
        boolean reponse = demandeInsert(parametres[0], parametres[1], parametres[2], parametres[3]);
        request.setAttribute("reponse", reponse);
        doGet(request, response);
    }

}