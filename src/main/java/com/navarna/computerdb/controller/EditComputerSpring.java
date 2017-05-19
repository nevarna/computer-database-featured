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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.navarna.computerdb.dto.CompanyDTO;
import com.navarna.computerdb.dto.ComputerDTO;
import com.navarna.computerdb.exception.ControllerException;
import com.navarna.computerdb.mapper.TransformationToDTO;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.service.ServiceCompanyImpl;
import com.navarna.computerdb.service.ServiceComputerImpl;

@Controller
@RequestMapping("/editComputer")
public class EditComputerSpring {
    private static final Logger LOGGER = LoggerFactory.getLogger(EditComputerSpring.class);
    @Autowired
    private ServiceComputerImpl servComputer = new ServiceComputerImpl();
    @Autowired
    private ServiceCompanyImpl servCompany = new ServiceCompanyImpl();

    /**
     * Navigation GET de l'url /editComputer
     * @param id : id du computer à modifier
     * @return model : le model contenant les attribut et l'adresse de la page
     *         jsp
     */
    @GetMapping
    public ModelAndView getRequest(@RequestParam(value = "id") int id) {
        LOGGER.info("-------->getRequest(id) args: " + id);
        if (id > 0) {
            Optional<Computer> computer = servComputer.findById(id);
            if (computer.isPresent()) {
                Optional<ComputerDTO> computerdto = TransformationToDTO.computerToDTO(computer.get());
                if (computerdto.isPresent()) {
                    ModelAndView model = new ModelAndView("editComputer");
                    ArrayList<CompanyDTO> informationCompany = TransformationToDTO
                            .arraylistCompanyToArraylistDTO(servCompany.listeComplete());
                    ecrireAttribute(model, informationCompany, computerdto.get());
                    return model;
                }
            }
        }
        throw new ControllerException("Le nombre parametre de id n'est pas correct");
    }

    /**
     * Navigation POST de l'url /editComputer
     * @param computerDto : ComputerDTO a modifier
     * @param result : ensemble des erreurs de validation de computerDTO
     * @return model : le model contenant les attribut et l'adresse de la page
     *         jsp
     */
    @PostMapping
    public String postRequest(@Valid @ModelAttribute("ComputerDTO") ComputerDTO computerDto, BindingResult result) {
        if (!result.hasErrors()) {
            boolean reponse = demandeUpdate(computerDto);
            if (reponse) {
                return "redirect:/dashboard";
            }
        }
        return "redirect:/editComputer?id=" + computerDto.getId();
    }

    /**
     * Ecris les attributs dont a besoin le fichier JSP.
     * @param model : model a retourner
     * @param informationCompany : arrayList contenant la liste des companies
     * @param computerdto : computerDTO à modifier
     */
    public void ecrireAttribute(ModelAndView model, ArrayList<CompanyDTO> informationCompany, ComputerDTO computerdto) {
        LOGGER.info("-------->ecrireAttribute(model,informationCompany,computerdto) args: undefined - undefined - "
                + computerdto);
        model.addObject("listeCompany", informationCompany);
        model.addObject("computerDTO", computerdto);
    }

    /**
     * Verifie les arguments de l'utilisateur et si elles sont correct, fait
     * l'update.
     * @param computer : computer à modifier
     * @return boolean : si oui ou non la base de données à été modifié
     */
    public boolean demandeUpdate(ComputerDTO computerdto) {
        LOGGER.info("-------->demandeUpdate(computerdto) args: " + computerdto);
        Optional<Computer> computer = TransformationToDTO.dtoToComputer(computerdto);
        if (computer.isPresent()) {
            return servComputer.update(computer.get());
        }
        return false;
    }
}
