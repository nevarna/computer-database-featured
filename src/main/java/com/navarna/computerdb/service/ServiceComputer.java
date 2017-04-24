package com.navarna.computerdb.service;

import java.util.Optional;

import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;

/**
 * @author navarna
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
    Page<Computer> liste(int numPage , int nbElement);

    /**
     * Demande au DAOComputer les détails du computer ayant l'id de l'arguments.
     * @param id : id du computer
     * @return Computer : le computer avec tout ses détails
     */
    Optional<Computer> show(long id);

    /**
     * Demande au DAOComputer les détails des computer ayant le nom de l'arguments.
     * @param name : name des computer
     * @return Page<Computer> : page des computer avec tout leus détails
     */
    Page<Computer> show(String name,int numPage , int nbElement);

    /**
     * Demande au DAOComputer le nombre de computer
     * @return int : nombre de computer
     */
    int countComputer ();

    /**
     * Demande au DAOComputer le nombre de computer ayant le nom de l'arguments.
     * @param name : name des computer
     * @return int : nombre de computer
     */
    int countComputerName(String name);
}
