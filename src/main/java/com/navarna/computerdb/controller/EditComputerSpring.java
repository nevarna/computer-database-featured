package com.navarna.computerdb.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.navarna.computerdb.dto.CompanyDTO;
import com.navarna.computerdb.exception.ControllerException;
import com.navarna.computerdb.mapper.TransformationToDTO;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.service.ServiceCompanyImpl;
import com.navarna.computerdb.service.ServiceComputerImpl;
import com.navarna.computerdb.validator.ValidationEntrer;

@Controller
@RequestMapping("/editComputer")
public class EditComputerSpring {
    private static final Logger LOGGER = LoggerFactory.getLogger(EditComputerSpring.class);
    @Autowired
    private ServiceComputerImpl servComputer = new ServiceComputerImpl();
    @Autowired
    private ServiceCompanyImpl servCompany = new ServiceCompanyImpl();
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getRequest(
            @RequestParam(value = "id") int id
            ) {
        LOGGER.info("-------->doGet(id)");
        if(id > 0) {
            ModelAndView model = new ModelAndView("editComputer");
            ArrayList<CompanyDTO> informationCompany = TransformationToDTO
                    .arraylistCompanyToArraylistDTO(servCompany.listeComplete());
            ecrireAttribute(model, id, informationCompany);
            return model;
        }
        else {
            throw new ControllerException("Le nombre parametre de id n'est pas correct");   
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String postRequest(
            @RequestParam(value = "id") int id,
            @RequestParam(value = "name", defaultValue ="") String name,
            @RequestParam(value = "introduced", defaultValue ="") String introduced,
            @RequestParam(value = "discontinued", defaultValue ="") String discontinued,
            @RequestParam(value = "idCompany", defaultValue ="") String idCompany
            ) {
        Optional<Computer> computer = servComputer.findById(id);
        boolean reponse = false;
        if (computer.isPresent()) {
            remplaceAttributComputer(computer.get(), id, name, introduced, discontinued, idCompany);
            reponse = demandeUpdate(computer.get());
        }
        if(reponse) {
            return "redirect:/dashboard";   
       }
       else {
           return "redirect:/editComputer?id="+id;
       }
    }
    /**
     * Ecris les attributs dont a besoin le fichier JSP.
     * @param request : request reçu par le servlet.
     * @param id : l'id du computer à modifier
     * @param informationCompany : arrayList contenant la liste des companies
     */
    public void ecrireAttribute(ModelAndView model , long id, ArrayList<CompanyDTO> informationCompany) {
        LOGGER.info("-------->ecrireAttribute(request,id,informationCompany) args: undefined - " + id + " - undefined");
        model.addObject("listeCompany", informationCompany);
        model.addObject("id", id);
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
}
