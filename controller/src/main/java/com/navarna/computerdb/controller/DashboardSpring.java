package com.navarna.computerdb.controller;

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

import com.navarna.computerdb.dto.ComputerDTO;
import com.navarna.computerdb.dto.NavigationDashboardDTO;
import com.navarna.computerdb.exception.ControllerException;
import com.navarna.computerdb.mapper.TransformationToDTO;
import com.navarna.computerdb.model.Page;
import com.navarna.computerdb.service.ServiceComputerImpl;

@Controller
@RequestMapping("/dashboard")
public class DashboardSpring {

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardSpring.class);
    @Autowired
    private ServiceComputerImpl servComputer;

    /**
     * Navigation GET de l'url /dashboard.
     * @param navigation : les élément nécéssaire à la navigation
     * @param result : les erreurs sur les élément de la navigation
     * @return model : le model contenant les attribut et l'adresse de la page
     *         jsp
     */
    @GetMapping
    public ModelAndView getRequest(@Valid @ModelAttribute("navigation") NavigationDashboardDTO navigation,
            BindingResult result) {
        if ((result == null) || (!result.hasErrors())) {
            navigation.setPage(navigation.getPage()-1);
            Page<ComputerDTO> pageComputer = creationListe(navigation);
            int totalElement = compterElement(navigation.getSearch(), navigation.getType());
            ModelAndView model = new ModelAndView("dashboard");
            navigation.setPage(navigation.getPage()+1);
            ecrireAttribute(model, pageComputer, navigation, totalElement);
            return model;
        }
        return new ModelAndView("redirect:/erreur?message=dashboard");
    }

    /**
     * Navigation POST de l'url /dashboard
     * @param selection : indice de computer à supprimer
     * @return model : le model contenant les attribut et l'adresse de la page
     *         jsp
     */

    @PostMapping("/delete")
    public String postRequest(@RequestParam(value = "selection", defaultValue = "") String selection) {
        LOGGER.info("-------->postRequest(selection) args: " + selection);
        String[] tableauIdDelete = lireParametrePost(selection);
        demandeSuppression(tableauIdDelete);
        return "redirect:/dashboard";
    }

    /**
     * Lis les paramêtre reçu de manière POST et les utilise.
     * @param request : request reçu par le servlet
     * @return String[] : tableau contenant les indices à supprimer.
     */
    private String[] lireParametrePost(String selection) {
        LOGGER.info("-------->lireParametrePost(request) args: " + selection);
        
        return selection.split(",");
    }

    /**
     * Demande de suppression par l'utilisateur.
     * @param tableauIdDelete : tableau contenant les indices des computers à
     *            supprimer
     */
    private void demandeSuppression(String[] tableauIdDelete) {
        LOGGER.info("-------->demande suppresion(tableauIdDelete)");
        try {
            int taille = tableauIdDelete.length;
            if ((taille != 0)&&(!tableauIdDelete[0].equals(""))) {
                if (taille == 1) {
                    long idSupprimer = Long.parseLong(tableauIdDelete[0]);
                    servComputer.delete(idSupprimer);
                } else {
                    long[] tableauIdDeleteLong = new long[taille];
                    for (int i = 0; i < taille; i++) {
                        tableauIdDeleteLong[i] = Long.parseLong(tableauIdDelete[i]);
                    }
                    servComputer.deleteMultiple(tableauIdDeleteLong);
                }
            }
        } catch (NumberFormatException | NullPointerException ne) {
            throw new ControllerException(" Erreur delete mode ", ne);
        }
    }

    /**
     * Créer la liste à afficher dans pageComputer.
     * @param name : nom ecris en arguments
     * @param typeSearch : si on cherche une companies ou un computer
     * @param order : type de l'ordre ASC ou DESC
     * @param numPage : numero de la page
     * @param nbElement : nombre d'élément par page
     * @return Page<ComputerDTO> : page contenant la liste à afficher
     */
    private Page<ComputerDTO> creationListe(NavigationDashboardDTO navigation) {
        LOGGER.info("-------->creationListe(navigation) args : " + navigation);
        Page<ComputerDTO> pageComputer = null;
        if (navigation.getSearch().equals("")) {
            pageComputer = TransformationToDTO.pageComputerToPageDTO(servComputer.liste(navigation.getPage(),
                    navigation.getNbElement(), navigation.getType(), navigation.getOrder()));
        } else {
            if (navigation.getType().equals("name")) {
                pageComputer = TransformationToDTO.pageComputerToPageDTO(servComputer.findByName(navigation.getSearch(),
                        navigation.getPage(), navigation.getNbElement(), navigation.getType(), navigation.getOrder()));
            } else {
                pageComputer = TransformationToDTO
                        .pageComputerToPageDTO(servComputer.findByCompany(navigation.getSearch(), navigation.getPage(),
                                navigation.getNbElement(), navigation.getType(), navigation.getOrder()));
            }
        }
        return pageComputer;
    }

    /**
     * Compte le nombre d'élément de la liste complete.
     * @param name : nom entrer dans la recherche
     * @param typeSearch : si on cherche une company ou un computeur
     * @return int : compteur d'élément.
     */
    private int compterElement(String name, String typeSearch) {
        LOGGER.info("-------->compterElement(name, typeSearch) args : " + name + " - " + typeSearch);
        int totalElement = 0;
        if (name.equals("")) {
            totalElement = servComputer.count();
        } else {
            if (typeSearch.equals("name")) {
                totalElement = servComputer.countWithName(name);
            } else {
                totalElement = servComputer.countWithNameCompany(name);
            }
        }
        return totalElement;
    }

    /**
     * Ecrit les attributs dont a besoin le fichier JSP.
     * @param model : model à retourner
     * @param pageComputer : page contenant la liste de computer
     * @param numPage : numero de page
     * @param name : nom de la recherche
     * @param typeSearch : type de la recherche
     * @param totalElement : nombre total d'élément de la recherche
     * @param nbElement : nombre d'element par page
     */
    public void ecrireAttribute(ModelAndView model, Page<ComputerDTO> pageComputer, NavigationDashboardDTO navigation,
            int totalElement) {
        LOGGER.info(
                "-------->ecrireAttribut(model,pageComputer,numPage,name,typeSearch,totalElement,nbElement) args : undefined - undefined - "
                        + navigation + " - " + totalElement);
        model.addObject("computers", pageComputer);
        model.addObject("navigation", navigation);
        model.addObject("totalElement", totalElement);
        
    }
}
