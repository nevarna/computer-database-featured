package com.navarna.computerdb.ui;

import java.util.Optional;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;
import com.navarna.computerdb.service.ServiceCompany;
import com.navarna.computerdb.service.ServiceCompanyImpl;
import com.navarna.computerdb.service.ServiceComputer;
import com.navarna.computerdb.service.ServiceComputerImpl;
import com.navarna.computerdb.validator.ValidationEntrer;

public class ConstruireRequete {
    private ServiceComputer servComputerImpl;
    private ServiceCompany servCompanyImpl;
    private EntrerUtilisateur entrerUtilisateur;
    private int nbElement = 20;
    private int numPage = 0;

    /**
     * Constructeur.
     * @param entrerUtilisateur : entrer Utilisateur qui utilise cette instance
     */
    public ConstruireRequete(EntrerUtilisateur entrerUtilisateur) {
        servCompanyImpl = new ServiceCompanyImpl();
        servComputerImpl = new ServiceComputerImpl();
        this.entrerUtilisateur = entrerUtilisateur;
    }

    /**
     * L UI appelle le service pour satisfaire la demande de l'utilisateur.
     * demande : une liste.
     * @param type : indique si c'est pour une companies ou un computer
     */
    public void demandeListe(String type) {
        if (type.equals("computers")) {
            Page<Computer> page = servComputerImpl.liste(numPage, nbElement);
            if (!page.estVide()) {
                SortieUtilisateur.lireListComputers(page);
            } else {
                SortieUtilisateur.lireAucuneDonnee();
            }
        } else if (type.equals("companies")) {
            Page<Company> page = servCompanyImpl.liste(numPage, nbElement);
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
     *L UI appelle le service pour satisfaire la demande de l'utilisateur.
     * demande : la page suivante.
     * @param type : indique si c'est pour une companies ou un computer
     */
    public void demandePageSuivante(String type) {
        this.numPage++;
        demandeListe(type);
    }

    /**
     * remet numpage à zero.
     * @param type : indique si c'est pour une companies ou un computer
     */
    public void resetList(String type) {
        this.numPage = 0;
    }

    /**
     * L UI appelle le service pour satisfaire la demande de l'utilisateur.
     * demande : les informations d'un computer.
     * @param id : id en String.
     */
    public void demandeShowId(String id) {
        int ids = ValidationEntrer.stringEnIntPositif(id);
        if (ids != -1) {
            Optional<Computer> computer = servComputerImpl.show(ids);
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
        Page<Computer> page = servComputerImpl.showName(name, numPage, nbElement);
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
        int ids = ValidationEntrer.stringEnIntPositif(id);
        if (ids != -1) {
            Computer computer = entrerUtilisateur.computerInformation(new Long(ids));
            SortieUtilisateur.lireValidationChangement(servComputerImpl.update(computer));
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
        if (type.equals("computer")) {
            Computer computer = entrerUtilisateur.computerInformation(new Long(0));
            SortieUtilisateur.lireValidationChangement(servComputerImpl.insert(computer));
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
        int ids = ValidationEntrer.stringEnIntPositif(id);
        if (ids != -1) {
            SortieUtilisateur.lireValidationChangement(servComputerImpl.delete(ids));
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
        int ids = ValidationEntrer.stringEnIntPositif(id);
        if (ids != -1) {
            SortieUtilisateur.lireValidationChangement(servCompanyImpl.delete(ids));
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
