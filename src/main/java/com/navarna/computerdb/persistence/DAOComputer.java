package com.navarna.computerdb.persistence;

import java.util.Optional;

import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;

public interface DAOComputer {

    /**
     * Ecris une requête à la base de donnée afin d'insérer un computer.
     * @param computer : élément à inserer
     * @return int : nombre de ligne modifié par la requête
     */
    int insert(Computer computer);

    /**
     * Ecris une requête à la base de donnée afin de modifier un computer.
     * @param computer : élément à modifier
     * @return int : nombre de ligne modifié par la requête
     */
    int update(Computer computer);

    /**
     * Ecris une requête à la base de donnée afin de supprimer un computer.
     * @param id : id de l'élément à supprimer
     * @return int : nombre de ligne modifié par la requête
     */
    int delete(long id);

    /**
     * Ecris une requête à la base de donnée afin d'avoir les détails un computer.
     * @param id : id de l'élément
     * @return int : nombre de ligne modifié par la requête
     */
    Optional<Computer> showId(long id);

    /**
     * Ecris une requête à la base de donnée afin d'avoir les détails de computer.
     * @param name : nom du computer
     * @return Page<Computer> : une page contenant tout les computer ayant le nom passé en arguments
     */
    Page<Computer> showName(String name, int numPage, int nbElement);

    /**
     * Ecris une requête à la base de donnée afin d'avoir la liste des computer.
     * @return Page<Computer> : Une page contenant une liste de computer
     */
    Page<Computer> list(int numPage, int nbElement);

    /**
     * Ecris une requête à la base de donnée afin d'avoir le nombre de computer
     * @return int : nombre de computer dans la base de donnée
     */
    int countComputer ();

    /**
     * Ecris une requête à la base de donnée afin d'avoir le nombre de computer ayant le nom name
     * @param name : name des computer
     * @return int : nombre de computer dans la base de donnée
     */
    int countComputerName(String name);
}
