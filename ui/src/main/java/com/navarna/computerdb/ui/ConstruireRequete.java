package com.navarna.computerdb.ui;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.navarna.computerdb.dto.ComputerDTO;
import com.navarna.computerdb.dto.PageCompanyDTO;
import com.navarna.computerdb.dto.PageComputerDTO;
import com.navarna.computerdb.exception.CLIException;
import com.navarna.computerdb.service.ServiceCompanyRestImpl;
import com.navarna.computerdb.service.ServiceComputerRestImpl;
import com.navarna.computerdb.validator.ValidationEntrer;

public class ConstruireRequete {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConstruireRequete.class);
    private EntrerUtilisateur entrerUtilisateur;
    private int nbElement = 20;
    private int numPage = 0;

    public ConstruireRequete(EntrerUtilisateur entrerUtilisateur) {
        this.entrerUtilisateur = entrerUtilisateur;
    }

    /**
     * L UI appelle le service pour satisfaire la demande de l'utilisateur.
     * demande : une liste.
     * @param type : indique si c'est pour une companies ou un computer
     */
    public void demandeListe(String type) {
        LOGGER.info("-------->demandeListe(type) args: " + type);
        if (type.equals("computers")) {
            PageComputerDTO page = ServiceComputerRestImpl.liste(numPage, nbElement);
            if (!page.estVide()) {
                SortieUtilisateur.lireListComputers(page);
            } else {
                SortieUtilisateur.lireAucuneDonnee();
            }
        } else if (type.equals("companies")) {
            PageCompanyDTO page = ServiceCompanyRestImpl.liste(numPage, nbElement);
            if (!page.estVide()) {
                SortieUtilisateur.lireListCompanies(page);
            } else {
                SortieUtilisateur.lireAucuneDonnee();
            }
        } else {
            throw new CLIException("fonction demandeListe arguments incorect");
        }
    }

    /**
     * L UI appelle le service pour satisfaire la demande de l'utilisateur.
     * demande : la page suivante.
     * @param type : indique si c'est pour une companies ou un computer
     */
    public void demandePageSuivante(String type) {
        LOGGER.info("-------->demandePageSuivante(type) args: " + type);
        this.numPage++;
        demandeListe(type);
    }

    /**
     * remet numpage à zero.
     * @param type : indique si c'est pour une companies ou un computer
     */
    public void resetList(String type) {
        LOGGER.info("-------->resetList(type) args: " + type);
        this.numPage = 0;
    }

    /**
     * L UI appelle le service pour satisfaire la demande de l'utilisateur.
     * demande : les informations d'un computer.
     * @param id : id en String.
     */
    public void demandeShowId(String id) {
        LOGGER.info("-------->demandeShowId(id) args: "+id);
        int ids = ValidationEntrer.stringEnIntPositif(id);
        if (ids != -1) {
            Optional<ComputerDTO> computer = ServiceComputerRestImpl.findById(ids);
            if (computer.isPresent()) {
                SortieUtilisateur.lireDetailsComputer(computer.get());
            } else {
                SortieUtilisateur.lireAucuneDonnee();
            }
        } else {
            throw new CLIException("fonction demandeShowId arguments incorect");
        }
    }

    /**
     * L UI appelle le service pour satisfaire la demande de l'utilisateur.
     * demande : information des computer ayant le nom passé en arguments.
     * @param name : nom de du computer
     */
    public void demandeShowName(String name) {
        LOGGER.info("-------->demandeShowName(name) args: "+name);
        PageComputerDTO page = ServiceComputerRestImpl.findByName(name, numPage, nbElement);
        if (!page.estVide()) {
            SortieUtilisateur.lireDetailsComputers(page);
        } else {
            SortieUtilisateur.lireAucuneDonnee();
        }
    }

    /**
     * L UI appelle le service pour satisfaire la demande de l'utilisateur.
     * demande : faire une modification sur un computer
     * @param id : id du computer
     */
    public void demandeUpdate(String id) {
        LOGGER.info("-------->demandeUpdate(id) args: "+id);
        int ids = ValidationEntrer.stringEnIntPositif(id);
        if (ids != -1) {
            ComputerDTO computerDTO = entrerUtilisateur.computerInformation(new Long(ids));
            SortieUtilisateur.lireValidationChangement(ServiceComputerRestImpl.update(computerDTO));
        } else {
            throw new CLIException("fonction demandeUpdate arguments incorect");
        }
    }

    /**
     * L UI appelle le service pour satisfaire la demande de l'utilisateur.
     * demande : inserer un ordinateur.
     * @param type :computer
     */
    public void demandeInsert(String type) {
        LOGGER.info("-------->demandeInsert(type) args: "+type);
        if (type.equals("computer")) {
            ComputerDTO computerDTO = entrerUtilisateur.computerInformation(new Long(0));
            SortieUtilisateur.lireValidationChangement(ServiceComputerRestImpl.insert(computerDTO));
        } else {
            throw new CLIException("fonction demandeInsert arguments incorect");
        }
    }

    /**
     * L UI appelle le service pour satisfaire la demande de l'utilisateur.
     * demande : supprimer un ordinateur par rapport à son id.
     * @param id : id du computer
     */
    public void demandeDelete(String id) {
        LOGGER.info("-------->demandeDelete(id) args: "+id);
        int ids = ValidationEntrer.stringEnIntPositif(id);
        if (ids != -1) {
            SortieUtilisateur.lireValidationChangement(ServiceComputerRestImpl.delete(ids));
        } else {
            throw new CLIException("fonction demandeUpdate arguments incorect");
        }
    }

    /**
     * L UI appelle le service pour satisfaire la demande de l'utilisateur.
     * demande : supprimer une company et ses ordinateurs par rapport à son id.
     * @param id : id de la company
     */
    public void demandeDeleteCompany(String id) {
        LOGGER.info("-------->demandeDeleteCompany(id) args: "+id);
        int ids = ValidationEntrer.stringEnIntPositif(id);
        if (ids != -1) {
            SortieUtilisateur.lireValidationChangement(ServiceCompanyRestImpl.delete(ids));
        } else {
            throw new CLIException("fonction demandeUpdate arguments incorect");
        }
    }

    /**
     * L UI appelle le service pour satisfaire la demande de l'utilisateur.
     * demande : changer le nombre d'élément par page
     * @param nbElement : le nombre d'éléments demandé
     */
    public void demandeChangeNbElement(int nbElement) {
        LOGGER.info("-------->demandeChangeNbElement(nbElement) args: "+nbElement);
        if (nbElement >= 0) {
            this.nbElement = nbElement;
        } else {
            throw new CLIException("fonction demandeChangeNbElement arguments incorect");
        }
    }

    /**
     * L UI appelle le service pour satisfaire la demande de l'utilisateur.
     * demande : changer le numero de page.
     * @param nbPage : numero de page demandé
     */
    public void demandeChangeNbPage(int nbPage) {
        LOGGER.info("-------->demandeChangeNbPage(nbPage) args: "+nbPage);
        if (nbPage >= 0) {
            this.numPage = nbPage;
        } else {
            throw new CLIException("fonction demandeChangeNbPage arguments incorect");
        }
    }

    /**
     * Gère les demandes de l'utilisateur.
     * @param command : tableau de commande de l'utilisateur.
     */
    public void command(String[] command) {
        LOGGER.info("-------->command(command)");
        if (command.length < 2) {
            throw new CLIException("fonction command nombre d'arguments incorect");
        } else {
            switch (command[0]) {
            case "List":
                demandeListe(command[1]);
                break;
            case "ShowId":
                demandeShowId(command[1]);
                break;
            case "ShowName":
                demandeShowName(command[1]);
                break;
            case "Update":
                demandeUpdate(command[1]);
                break;
            case "Insert":
                demandeInsert(command[1]);
                break;
            case "Delete":
                demandeDelete(command[1]);
                break;
            case "DeleteCompany":
                demandeDeleteCompany(command[1]);
            }
        }
    }
}
