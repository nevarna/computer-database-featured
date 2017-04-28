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
import com.navarna.computerdb.exception.ControllerException;
import com.navarna.computerdb.mapper.TransformationToDTO;
import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.service.ServiceCompanyImpl;
import com.navarna.computerdb.service.ServiceComputerImpl;
import com.navarna.computerdb.validator.ValidationEntrer;

/**
 * Servlet implementation class EditComputer
 */
public class EditComputer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int NOMBRE_PARAMETRE = 5;
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
     * Verifie les arguments de l'utilisateur et si elles sont correct, fait
     * l'update.
     * @param id : id du computer
     * @param name : nom du computer
     * @param introduced : date de mise en marche
     * @param discontinued : date d'arret
     * @param idCompany : numero de la company
     * @return boolean : si oui ou non la base de données à été modifié
     */
    public boolean demandeUpdate(long id, String name, String introduced, String discontinued, String idCompany) {
        System.out.println("dans edit");
        // A enlever plus tard
        introduced = "1994-05-06";
        discontinued = "2017-04-27";

        if (ValidationEntrer.entrerValide(name, introduced, discontinued, idCompany)) {
            LocalDate pIntroduced = LocalDate.parse(introduced);
            LocalDate pDiscontinued = LocalDate.parse(discontinued);
            int CompanyId = ValidationEntrer.stringEnIntPositif(idCompany);
            Company company = new Company.CompanyBuilder("null").setId(new Long(CompanyId)).build();
            Computer computer = new Computer.ComputerBuilder(name).setId(id).setIntroduced(pIntroduced)
                    .setDiscontinued(pDiscontinued).setCompany(company).build();
            System.out.println("Computer changement : "+ computer);
            return servComputer.update(computer);
        }
        return false;
    }

    /**
     * Lit les paramêtre reçu de façon GET.
     * @param request : request reçu par le servlet
     * @return long : id du computer
     */
    public long recupererIdEnGet(HttpServletRequest request) {
        String idLien = request.getParameter("id");
        return setIdComputer(idLien);
    }

    /**
     * Lit les paramêtre reçu de façon POST.
     * @param request : request recu par le servlet
     * @return String[] : un tableau contenant les indices de manière ordonnées
     *         0 : id du computer 1 : nom du computer 2 : date de mise en marche
     *         3 : date d'arret 4 : id de la company
     */
    public String[] lireParametrePost(HttpServletRequest request) {
        String[] parametres = new String[NOMBRE_PARAMETRE];
        parametres[0] = request.getParameter("id");
        parametres[1] = request.getParameter("name");
        parametres[2] = request.getParameter("introduced");
        parametres[3] = request.getParameter("discontinued");
        parametres[4] = request.getParameter("idCompany");
        return parametres;
    }

    /**
     * Ecris les attributs dont a besoin le fichier JSP.
     * @param request : request reçu par le servlet.
     * @param id : l'id du computer à modifier
     * @param informationCompany : arrayList contenant la liste des companies
     */
    public void ecrireAttribute(HttpServletRequest request, long id, ArrayList<CompanyDTO> informationCompany) {
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
        long id = recupererIdEnGet(request);
        ArrayList<CompanyDTO> informationCompany = TransformationToDTO
                .ArrayListCompanyToArrayListDTO(servCompany.listeComplete());
        ecrireAttribute(request, id, informationCompany);
        fichierJSP.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] parametres = lireParametrePost(request);
        long id = setIdComputer(parametres[0]);
        boolean reponse = demandeUpdate(id, parametres[1], parametres[2], parametres[3], parametres[4]);
        if (reponse) {
            response.sendRedirect("Dashboard");
        } else {
            RequestDispatcher fichierJSP = this.getServletContext()
                    .getRequestDispatcher("/resources/views/editComputer.jsp");
            ArrayList<CompanyDTO> informationCompany = TransformationToDTO
                    .ArrayListCompanyToArrayListDTO(servCompany.listeComplete());
            request.setAttribute("reponse", reponse);
            ecrireAttribute(request, id, informationCompany);
            fichierJSP.forward(request, response);
        }
    }

}
