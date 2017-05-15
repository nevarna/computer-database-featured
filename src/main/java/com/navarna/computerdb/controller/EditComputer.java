package com.navarna.computerdb.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.navarna.computerdb.dto.CompanyDTO;
import com.navarna.computerdb.exception.ControllerException;
import com.navarna.computerdb.mapper.TransformationToDTO;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.service.ServiceCompanyImpl;
import com.navarna.computerdb.service.ServiceComputerImpl;
import com.navarna.computerdb.validator.ValidationEntrer;

/**
 * Servlet implementation class EditComputer.
 */
@Controller
public class EditComputer extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(EditComputer.class);
    private static final long serialVersionUID = 1L;
    private static final int NOMBRE_PARAMETRE = 5;
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
     * set id avec l id passée en arguments get.
     * @param idLien : id reçu en get
     * @return long : id du computer
     */
    public long setIdComputer(String idLien) {
        LOGGER.info("-------->setIdComputer(idLien) args: " + idLien);
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
     * remplace les attributs du computer de la base de données par les
     * nouveaux.
     * @param computer : computer avec les valeurs dans la base de données
     * @param id : id du computer
     * @param name : nom du computer
     * @param introduced : date de mise en marche
     * @param discontinued : date d'arret
     * @param idCompany : numero de la company
     */
    public void remplaceAttributComputer(Computer computer, long id, String name, String introduced,
            String discontinued, String idCompany) {
        LOGGER.info("-------->remplaceAttributComputer(computer,id,name,introduced,discontinued,idCompany) args: "
                + computer + " - " + id + " - " + name + " - " + introduced + "-" + discontinued + "-" + idCompany);
        int idComp = ValidationEntrer.stringEnIntPositif(idCompany);
        LocalDate computerIntroduced = ValidationEntrer.dateController(introduced);
        LocalDate computerDiscontinued = ValidationEntrer.dateController(discontinued);
        if (!name.equals("")) {
            computer.setName(name);
        }
        if (computerIntroduced != null) {
            computer.setIntroduced(computerIntroduced);
        }
        if (computerDiscontinued != null) {
            computer.setDiscontinued(computerDiscontinued);
        }
        if (idComp != -1) {
            computer.getCompany().setId(idComp);
        }
    }

    /**
     * Verifie les arguments de l'utilisateur et si elles sont correct, fait
     * l'update.
     * @param computer : computer à modifier
     * @return boolean : si oui ou non la base de données à été modifié
     */
    public boolean demandeUpdate(Computer computer) {
        LOGGER.info("-------->demandeUpdate(computer) args: " + computer);
        if (ValidationEntrer.dateLogique(computer.getIntroduced(), computer.getDiscontinued())) {
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
        LOGGER.info("-------->recupereIdEnGet(request)");
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
        LOGGER.info("-------->lireParametrePost(request)");
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
        LOGGER.info("-------->ecrireAttribute(request,id,informationCompany) args: undefined - " + id + " - undefined");
        request.setAttribute("listeCompany", informationCompany);
        request.setAttribute("id", id);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher fichierJSP = this.getServletContext()
                .getRequestDispatcher("/resources/views/editComputer.jsp");
        LOGGER.info("-------->doGet(request,reponse)");
        long id = recupererIdEnGet(request);
        ArrayList<CompanyDTO> informationCompany = TransformationToDTO
                .arraylistCompanyToArraylistDTO(servCompany.listeComplete());
        ecrireAttribute(request, id, informationCompany);
        fichierJSP.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("-------->doGet(request,reponse)");
        String[] parametres = lireParametrePost(request);
        long id = setIdComputer(parametres[0]);
        Optional<Computer> computer = servComputer.findById(id);
        boolean reponse = false;
        if (computer.isPresent()) {
            remplaceAttributComputer(computer.get(), id, parametres[1], parametres[2], parametres[3], parametres[4]);
            reponse = demandeUpdate(computer.get());
        }
        if (reponse) {
            response.sendRedirect("Dashboard");
        } else {
            RequestDispatcher fichierJSP = this.getServletContext()
                    .getRequestDispatcher("/resources/views/editComputer.jsp");
            ArrayList<CompanyDTO> informationCompany = TransformationToDTO
                    .arraylistCompanyToArraylistDTO(servCompany.listeComplete());
            request.setAttribute("reponse", reponse);
            ecrireAttribute(request, id, informationCompany);
            fichierJSP.forward(request, response);
        }
    }

}
