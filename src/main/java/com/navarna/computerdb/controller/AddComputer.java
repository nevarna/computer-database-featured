package com.navarna.computerdb.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.navarna.computerdb.dto.CompanyDTO;
import com.navarna.computerdb.mapper.TransformationToDTO;
import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.service.ServiceCompanyImpl;
import com.navarna.computerdb.service.ServiceComputerImpl;
import com.navarna.computerdb.validator.ValidationEntrer;

/**
 * @author excilys
 *
 */
public class AddComputer extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddComputer.class);
    private static final long serialVersionUID = 1L;
    private static final int NOMBRE_PARAMETRE = 4;
    @Autowired
    private ServiceComputerImpl servComputer = new ServiceComputerImpl();
    @Autowired
    private ServiceCompanyImpl servCompany = new ServiceCompanyImpl();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        LOGGER.info("-------->init(config)");
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
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
    private boolean demandeInsert(String name, String introduced, String discontinued, String idCompany) {
        LOGGER.info("-------->demandeInsert(name,introducedndiscontinued,idCompany) args:" + name + " - " + introduced
                + " - " + discontinued + " - " + idCompany);
        int id = ValidationEntrer.stringEnIntPositif(idCompany);
        LocalDate computerIntroduced = ValidationEntrer.dateController(introduced);
        LocalDate computerDiscontinued = ValidationEntrer.dateController(discontinued);
        if (ValidationEntrer.entrerValide(name, computerIntroduced, computerDiscontinued, id)) {
            Company company = new Company.CompanyBuilder("").setId(new Long(id)).build();
            Computer computer = new Computer.ComputerBuilder(name).setIntroduced(computerIntroduced)
                    .setDiscontinued(computerDiscontinued).setCompany(company).setId(0L).build();
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
        LOGGER.info("-------->lireParametre(request)");
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
        LOGGER.info("-------->ecrireAttribute(request)");
        ArrayList<CompanyDTO> informationCompany = TransformationToDTO
                .arraylistCompanyToArraylistDTO(servCompany.listeComplete());
        request.setAttribute("listeCompany", informationCompany);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("-------->doGet(request,reponse)");
        RequestDispatcher fichierJSP = this.getServletContext()
                .getRequestDispatcher("/resources/views/addComputer.jsp");
        ecrireAttribute(request);
        fichierJSP.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("-------->doPost(request,reponse)");
        String[] parametres = lireParametre(request);
        boolean reponse = demandeInsert(parametres[0], parametres[1], parametres[2], parametres[3]);
        request.setAttribute("reponse", reponse);
        doGet(request, response);
    }

}