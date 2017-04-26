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
import com.navarna.computerdb.model.Page;
import com.navarna.computerdb.model.Company.CompanyBuilder;
import com.navarna.computerdb.model.Computer.ComputerBuilder;
import com.navarna.computerdb.service.ServiceCompanyImpl;
import com.navarna.computerdb.service.ServiceComputerImpl;
import com.navarna.computerdb.validator.ValidationEntrer;

/**
 * Servlet implementation class EditComputer
 */
public class EditComputer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ServiceComputerImpl servComputer = new ServiceComputerImpl();
    private ServiceCompanyImpl servCompany = new ServiceCompanyImpl();

    /**
     * set id avec l id passée en arguments get
     * @param idLien : id reçu en get
     */
    public long setIdComputer(String idLien) {
        try {
            long numero = idLien == null ? -1 : Long.parseLong(idLien);
            if (numero < 1) {
                throw new ControllerException("Le nombre parametre de page n'est pas correct");
            } else {
                return numero;
            }
        } catch (NumberFormatException ne) {
            throw new ControllerException("l'argument parametre de page n'est pas un nombre", ne);
        }
    }

    /**
     * Créer une liste contenant toutes les companies.
     * @return ArrayList<CompanyDTO> : liste de toutes les companies
     */
    public ArrayList<CompanyDTO> initialisationListeCompany() {
        ArrayList<CompanyDTO> informationCompany = new ArrayList<CompanyDTO>();
        boolean fini = false;
        int numPage = 0;
        int nbElement = 100;
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
     * Verifie les arguments de l'utilisateur et si elles sont correct, fait
     * l'update.
     * @param id : id du computer
     * @param name : nom du computer
     * @param introduced : date de mise en marche
     * @param discontinued : date d'arret
     * @param idCompany : numero de la company
     * @return int : nombre de ligne modifié par la requête
     */
    public int demandeUpdate(long id, String name, String introduced, String discontinued, String idCompany) {
        if (ValidationEntrer.entrerValide(name, introduced, discontinued, idCompany)) {
            LocalDate pIntroduced = LocalDate.parse(introduced);
            LocalDate pDiscontinued = LocalDate.parse(discontinued);
            int CompanyId = ValidationEntrer.stringEnIntPositif(idCompany);
            Company company = new CompanyBuilder("null").setId(new Long(CompanyId)).build();
            Computer computer = new ComputerBuilder(name).setId(id).setIntroduced(pIntroduced)
                    .setDiscontinued(pDiscontinued).setCompany(company).build();
           return servComputer.update(computer);   
        }
        return 0;
    }

    /**
     * Lit les paramêtre reçu de façon GET.
     * @param request : request reçu par le servlet
     * @return long : id du computer
     */
    public long UtilisationParametreGet(HttpServletRequest request) {
        String idLien = request.getParameter("id");
        return setIdComputer(idLien);
    }

    /**
     * Lit les paramêtre reçu de façon POST.
     * @param request : request recu par le servlet
     */
    public void UtilisationParametrePost(HttpServletRequest request) {
        String idLien = request.getParameter("id");
        long id = setIdComputer(idLien);
        String name = request.getParameter("name");
        String introduced = request.getParameter("introduced");
        String discontinued = request.getParameter("discontinued");
        String idCompany = request.getParameter("idCompany");
        int reponse = demandeUpdate(id,name, introduced, discontinued, idCompany);
        request.setAttribute("reponse", reponse);
    }

    /**
     * Ecris les attributs dont a besoin le fichier JSP.
     * @param request : request reçu par le servlet.
     * @param id : l'id du computer à modifier
     */
    public void ecrireAttribute(HttpServletRequest request, long id) {
        ArrayList<CompanyDTO> informationCompany = initialisationListeCompany();
        request.setAttribute("listeCompany", informationCompany);
        request.setAttribute("id", id);
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher fichierJSP = this.getServletContext()
                .getRequestDispatcher("/resources/views/editComputer.jsp");
        long id = UtilisationParametreGet(request);
        ecrireAttribute(request, id);
        fichierJSP.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UtilisationParametrePost(request);
        doGet(request, response);
    }

}
