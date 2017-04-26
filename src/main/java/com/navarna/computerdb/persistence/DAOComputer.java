package com.navarna.computerdb.persistence;

import java.util.Optional;

import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;

public interface DAOComputer {

    /**
     * Ecris une requête à la base de donnée afin d'insérer un computer.
     * @param computer : élément à inserer
     * @return boolean : si oui ou non la base de donnée à été modifié par la
     *         requête
     */
    boolean insert(Computer computer);

    /**
     * Ecris une requête à la base de donnée afin de modifier un computer.
     * @param computer : élément à modifier
     * @return boolean : si oui ou non la base de donnée à été modifié par la
     *         requête
     */
    boolean update(Computer computer);

    /**
     * Ecris une requête à la base de donnée afin de supprimer un computer.
     * @param id : id de l'élément à supprimer
     * @return boolean : si oui ou non la base de donnée à été modifié par la
     *         requête
     */
    boolean delete(long id);

    /**
     * Ecris une requête à la base de donnée afin d'avoir les détails un
     * computer.
     * @param id : id de l'élément
     * @return int : nombre de ligne modifié par la requête
     */
    Optional<Computer> findById(long id);

    /**
     * Ecris une requête à la base de donnée afin d'avoir les détails de
     * computer.
     * @param name : nom du computer
     * @param numPage : numero de la page
     * @param nbElement : nombre d'élément par page
     * @return Page<Computer> : une page contenant tout les computer ayant le
     *         nom passé en arguments
     */
    Page<Computer> findByName(String name, int numPage, int nbElement);

    /**
     * Ecris une requête à la base de donnée afin d'avoir les détails de
     * computer.
     * @param nameCompany : nom du computer
     * @param numPage : numero de la page
     * @param nbElement : nombre d'élément par page
     * @return Page<Computer> : une page contenant tout les computer ayant le
     *         nom passé en arguments
     */
    Page<Computer> findByCompany(String nameCompany, int numPage, int nbElement);

    /**
     * Ecris une requête à la base de donnée afin d'avoir la liste des computer.
     * @param numPage : numero de la page
     * @param nbElement : nombre d'élément par page
     * @return Page<Computer> : Une page contenant une liste de computer
     */
    Page<Computer> list(int numPage, int nbElement);

    /**
     * Ecris une requête à la base de donnée afin d'avoir le nombre de computer.
     * @return int : nombre de computer dans la base de donnée
     */
    int count();

    /**
     * Ecris une requête à la base de donnée afin d'avoir le nombre de computer.
     * ayant le nom name
     * @param name : name des computer
     * @return int : nombre de computer dans la base de donnée
     */
    int countWithName(String name);

    /**
     * Ecris une requête à la base de donnée afin d'avoir le nombre de computer.
     * ayant la company name
     * @param nameCompany : name de la company
     * @return int : nombre de computer dans la base de donnée
     */
    int countWithNameCompany(String nameCompany);
}
