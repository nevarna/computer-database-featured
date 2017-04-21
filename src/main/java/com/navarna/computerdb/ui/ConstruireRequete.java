package com.navarna.computerdb.ui;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;
import com.navarna.computerdb.service.ServiceCompany;
import com.navarna.computerdb.service.ServiceCompanyImpl;
import com.navarna.computerdb.service.ServiceComputer;
import com.navarna.computerdb.service.ServiceComputerImpl;
import com.navarna.computerdb.validator.ValidationEntrer;

public class ConstruireRequete {
    private static ServiceComputer servComputerImpl = new ServiceComputerImpl();
    private static ServiceCompany servCompanyImpl = new ServiceCompanyImpl();

    /**
     * L UI appelle le service pour satisfaire la demande de l'utilisateur.
     * demande : la page suivante de la liste.
     * @param type : indique si c'est pour une companies ou un computer
     */
    public static void demandePageSuivante(String type) {
        if (type.equals("computers")) {
            Page<Computer> page = servComputerImpl.listeSuivante();
            if (page != null) {
                SortieUtilisateur.lireListComputers(page);
            } else {
                SortieUtilisateur.lireAucuneDonnee();
            }
        } else if (type.equals("companies")) {
            Page<Company> page = servCompanyImpl.listeSuivante();
            if (page != null) {
                SortieUtilisateur.lireListCompanies(page);
            } else {
                SortieUtilisateur.lireAucuneDonnee();
            }
        } else {
            throw new CLIException("fonction demandePageSuivante arguments incorect");
        }
    }

    /**
     * On remet la liste à la page 0.
     * @param type : indique si c'est pour une companies ou un computer
     */
    public static void resetList(String type) {
        if (type.equals("computers")) {
            servComputerImpl.resetPage();
        } else if (type.equals("companies")) {
            servCompanyImpl.resetPage();
        } else {
            throw new CLIException("fonction resetList arguments incorect");
        }
    }



    /**
     * L UI appelle le service pour satisfaire la demande de l'utilisateur.
     * demande : une liste.
     * @param type : indique si c'est pour une companies ou un computer
     */
    public static void demandeListe(String type) {
        if (type.equals("computers")) {
            Page<Computer> page = servComputerImpl.liste();
            if (page != null) {
                SortieUtilisateur.lireListComputers(page);
            } else {
                SortieUtilisateur.lireAucuneDonnee();
            }
        } else if (type.equals("companies")) {
            Page<Company> page = servCompanyImpl.listeSuivante();
            if (page != null) {
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
     * demande : les informations d'un computer.
     * @param id : id en String.
     */
    public static void demandeShowId(String id) {
        int ids = ValidationEntrer.stringEnInt(id);
        if (ids != -1) {
            Computer computer = servComputerImpl.show(ids);
            SortieUtilisateur.lireDetailsComputer(computer);
        } else {
            throw new CLIException("fonction demandeShowId arguments incorect");
        }
    }

    /**
     * L UI appelle le service pour satisfaire la demande de l'utilisateur.
     * demande : information des computer ayant le nom passé en arguments.
     * @param name : nom de du computer
     */
    public static void demandeShowName(String name) {
        Page<Computer> page = servComputerImpl.show(name);
        if (page != null) {
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
    public static void demandeUpdate(String id) {
        int ids = ValidationEntrer.stringEnInt(id);
        if (ids != -1) {
            Computer computer = EntrerUtilisateur.computerInformation(new Long(ids));
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
    public static void demandeInsert(String type) {
        if (type.equals("computer")) {
            Computer computer = EntrerUtilisateur.computerInformation(new Long(0));
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
    public static void demandeDelete(String id) {
        int ids = ValidationEntrer.stringEnInt(id);
        if (ids != -1) {
            SortieUtilisateur.lireValidationChangement(servComputerImpl.delete(ids));
        } else {
            throw new CLIException("fonction demandeUpdate arguments incorect");
        }
    }

    /**
     * L UI appelle le service pour satisfaire la demande de l'utilisateur.
     * demande : changer le nombre d'élément par page
     * @param type : company ou computer
     * @param nbElement : le nombre d'éléments demandé
     */
    public static void demandeChangeNbElement(String type, int nbElement) {
        if (type.equals("computers")) {
            servComputerImpl.choisirNbElement(nbElement);
        } else if (type.equals("companies")) {
            servCompanyImpl.choisirNbElement(nbElement);
        } else {
            throw new CLIException("fonction demandeChangeNbElement arguments incorect");
        }
    }

    /**
     * L UI appelle le service pour satisfaire la demande de l'utilisateur.
     * demande : changer le numero de page.
     * @param type : company ou computer
     * @param nbPage : numero de page demandé
     */
    public static void demandeChangeNbPage(String type, int nbPage) {
        if (type.equals("computers")) {
            servComputerImpl.choisirPage(nbPage);
        } else if (type.equals("companies")) {
            servCompanyImpl.choisirPage(nbPage);
        } else {
            throw new CLIException("fonction demandeChangeNbPage arguments incorect");
        }
    }

    /**
     * Gère les demandes de l'utilisateur.
     * @param command : tableau de commande de l'utilisateur.
     */
    public static void command(String[] command) {
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
            }
        }
    }
}
