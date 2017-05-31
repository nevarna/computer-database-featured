package com.navarna.computerdb.controller;

import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.navarna.computerdb.dto.CompanyDTO;
import com.navarna.computerdb.dto.ComputerDTO;
import com.navarna.computerdb.mapper.TransformationToDTO;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.service.ServiceCompany;
import com.navarna.computerdb.service.ServiceComputer;

@Controller
@RequestMapping("/addComputer")
public class AddComputerSpring {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddComputerSpring.class);
    @Autowired
    private ServiceComputer servComputer;
    @Autowired
    private ServiceCompany servCompany ;

    /**
     * Navigation GET de l'url /addComputer.
     * @return model : le model contenant les attribut et l'adresse de la page
     *         jsp
     */
    @GetMapping
    public ModelAndView getRequest() {
        LOGGER.info("-------->getRequest()");
        ModelAndView model = new ModelAndView("addComputer");
        ecrireAttribute(model);
        return model;
    }

    /**
     * Navigation POST de l'url /addComputer.
     * @param computerDto : ComputerDTO a ajouter
     * @param result : ensemble des erreurs de validation de computerDTO
     * @return model : le model contenant les attribut et l'adresse de la page
     *         jsp
     */
    @PostMapping
    public String postRequest(@Valid @ModelAttribute("ComputerDTO") ComputerDTO computerDto, BindingResult result) {
        LOGGER.info("-------->postRequest(computerDto,result) args: " + computerDto + " - " + result);
        if (!result.hasErrors()) {
            boolean reponse = demandeInsert(computerDto);
            if (reponse) {
                return "redirect:/dashboard";
            }
        }
        return "redirect:/addComputer";
    }

    /**
     * écrit les attributs dont a besoin le fichier jsp.
     * @param model : model à retourner
     */
    public void ecrireAttribute(ModelAndView model) {
        LOGGER.info("-------->ecrireAttribute(model)");
        ArrayList<CompanyDTO> informationCompany = TransformationToDTO
                .arraylistCompanyToArraylistDTO(servCompany.listeComplete());
        ComputerDTO computerDto = new ComputerDTO();
        model.addObject("listeCompany", informationCompany);
        model.addObject("computerDTO", computerDto);
    }

    /**
     * Demande validation des entrées de l'utilisateur et demande au service
     * d'insérer un computer.
     * @param computerdto : computer
     * @return boolean : si l'insert à été effectuer
     */
    private boolean demandeInsert(ComputerDTO computerdto) {
        LOGGER.info("-------->demandeInsert(computerdto) args:" + computerdto);
        Optional<Computer> computer = TransformationToDTO.dtoToComputer(computerdto);
        if (computer.isPresent()) {
            return servComputer.insert(computer.get());
        }
        return false;
    }
}
