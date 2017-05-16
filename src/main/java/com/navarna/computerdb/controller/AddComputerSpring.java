package com.navarna.computerdb.controller;

import java.time.LocalDate;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.navarna.computerdb.dto.CompanyDTO;
import com.navarna.computerdb.mapper.TransformationToDTO;
import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.service.ServiceCompanyImpl;
import com.navarna.computerdb.service.ServiceComputerImpl;
import com.navarna.computerdb.validator.ValidationEntrer;

@Controller
@RequestMapping("/addComputer")
public class AddComputerSpring {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddComputerSpring.class);
    @Autowired
    private ServiceComputerImpl servComputer = new ServiceComputerImpl();
    @Autowired
    private ServiceCompanyImpl servCompany = new ServiceCompanyImpl();

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getRequest() {
        ModelAndView model = new ModelAndView("addComputer");
        ecrireAttribute(model);
        return model;
    }
    @RequestMapping(method = RequestMethod.POST)
    public String postRequest(
            @RequestParam(value = "name", defaultValue ="") String name,
            @RequestParam(value = "introduced", defaultValue ="") String introduced,
            @RequestParam(value = "discontinued", defaultValue ="") String discontinued,
            @RequestParam(value = "idCompany", defaultValue ="") String idCompany
            ) {
        boolean reponse = demandeInsert(name,introduced, discontinued, idCompany);
        if(reponse) {
             return "redirect:/dashboard";   
        }
        else {
            return "redirect:/addComputer";
        }
    }

    /**
     * écrit les attributs dont a besoin le fichier jsp.
     * @param request : request reçu par le servlet
     */
    public void ecrireAttribute(ModelAndView model) {
        LOGGER.info("-------->ecrireAttribute(request)");
        ArrayList<CompanyDTO> informationCompany = TransformationToDTO
                .arraylistCompanyToArraylistDTO(servCompany.listeComplete());
        model.addObject("listeCompany", informationCompany);
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
}
