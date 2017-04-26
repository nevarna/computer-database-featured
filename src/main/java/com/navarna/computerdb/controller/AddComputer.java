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
import com.navarna.computerdb.model.Company.CompanyBuilder;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Computer.ComputerBuilder;
import com.navarna.computerdb.model.Page;
import com.navarna.computerdb.service.ServiceCompanyImpl;
import com.navarna.computerdb.service.ServiceComputerImpl;
import com.navarna.computerdb.validator.ValidationEntrer;

public class AddComputer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ServiceComputerImpl servComputer = new ServiceComputerImpl();
    private ServiceCompanyImpl servCompany = new ServiceCompanyImpl();

    /**
     * crée une liste de l'emsemble des companies.
     * @return ArrayList<CompanyDTO> : ensemble des companies de la base de
     *         données
     */
    protected ArrayList<CompanyDTO> initialisationListeCompany() {
        ArrayList<CompanyDTO> informationCompany = new ArrayList<CompanyDTO>();
        boolean fini = false;
        int nbElement = 100;
        int numPage = 0;
        while (!fini) {
            Page<CompanyDTO> page = TransformationToDTO.pageCompanyToPageDTO(servCompany.liste(numPage, nbElement));
            numPage++;
            if (page.estVide()) {
                fini = true;
            } else {
                for (CompanyDTO company : page.getPage()) {
                    informationCompany.add(company);
                }
            }
        }
        return informationCompany;
    }

    /**
     * Demande validation des entrées de l'utilisateur et demande au service
     * d'insérer un computer.
     * @param name : nom du computer
     * @param introduced : date de mise en marche
     * @param discontinued : date de mise en arret.
     * @param idCompany : id de la company du computer
     * @return int : nombre de ligne modifié par la requête
     */
    public int demandeInsert(String name, String introduced, String discontinued, String idCompany) {
        if (ValidationEntrer.entrerValide(name, introduced, discontinued, idCompany)) {
            LocalDate pIntroduced = LocalDate.parse(introduced);
            LocalDate pDiscontinued = LocalDate.parse(discontinued);
            int id = ValidationEntrer.stringEnIntPositif(idCompany);
            Company company = new CompanyBuilder("null").setId(new Long(id)).build();
            Computer computer = new ComputerBuilder(name).setIntroduced(pIntroduced).setDiscontinued(pDiscontinued)
                    .setCompany(company).setId(0L).build();
            return servComputer.insert(computer);
        }
        return 0;
    }

    /**
     * lit les paramêtre post de la requête et demande une insertion.
     * @param request : request reçu par le servlet
     */
    public void UtilisationParametre(HttpServletRequest request) {
        String name = request.getParameter("name");
        String introduced = request.getParameter("introduced");
        String discontinued = request.getParameter("discontinued");
        String idCompany = request.getParameter("idCompany");
        int reponse = demandeInsert(name, introduced, discontinued, idCompany);
        request.setAttribute("reponse", reponse);
    }

    /**
     * écrit les attributs dont a besoin le fichier jsp.
     * @param request : request reçu par le servlet
     */
    public void ecrireAttribute(HttpServletRequest request) {
        ArrayList<CompanyDTO> informationCompany = initialisationListeCompany();
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
        UtilisationParametre(request);
        doGet(request, response);
    }

}