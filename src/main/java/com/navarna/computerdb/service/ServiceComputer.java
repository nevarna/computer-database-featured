package com.navarna.computerdb.service;

import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;

/**
 * @author excilys
 *
 */
public interface ServiceComputer {

    /**
     * Demande au DAOComputer d'insérer le computer.
     * @param computer : Computer à insérer
     * @return int : nombre de ligne changé
     */
    int insert(Computer computer);

    /**
     * Demande au DAOComputer de modifier le computer.
     * @param computer : Computer à modifier
     * @return int : nombre de ligne changé
     */
    int update(Computer computer);

    /**
     * Demande au DAOComputer de supprimer un computer ayant un id prècis.
     * @param id : id du computer à supprimer
     * @return int : nombre de ligne changé
     */
    int delete(long id);

    /**
     * Demande au DAOComputer la liste des computer.
     * @return Page<Computer> : page de la liste de computer
     */
    Page<Computer> liste();

    /**
     * Demande au DAOComputer les détails du computer ayant l'id de l'arguments.
     * @param id : id du computer
     * @return Computer : le computer avec tout ses détails
     */
    Computer show(long id);

    /**
     * Demande au DAOComputer les détails des computer ayant le nom de l'arguments.
     * @param name : name des computer
     * @return Page<Computer> : page des computer avec tout leus détails
     */
    Page<Computer> show(String name);

    /**
     * Demande au DAOComputer la page suivante de la liste des computer.
     * @return Page<Computer> : page de la liste de computer
     */
    Page<Computer> listeSuivante();

    /**
     * Demande au DAOComputer la page suivantes des détails des computer ayant le nom de l'arguments.
     * @param name : name des computer
     * @return Page<Computer> : page des computer avec tout leus détails
     */
    Page<Computer> showSuivant(String name);

    /**
     * Demande au DAOComputer de changer le numero de la page.
     * @param page : numero de la page
     */
    void choisirPage(int page);

    /**
     * Demande au DAOComputer le numero de la page actuelle
     * @return int : numero de la page
     */
    int recupererPage();

    /**
     * Demande au DAOComputer de changer le nombre d'élément par page.
     * @param nbElement : nombre d'éléments
     */
    void choisirNbElement(int nbElement);

    /**
     * Demande au DAOComputer de remettre le numero de la page à zero.
     */
    void resetPage();
}
