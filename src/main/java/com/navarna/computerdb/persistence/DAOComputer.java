package com.navarna.computerdb.persistence;

import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;

public interface DAOComputer {

    /**
     * getter de l'attribut page.
     * @return int : numero de la page
     */
    int getPage();

    /**getter de l'atribut nbElement.
     * @return int : nombre d'élément par page
     */
    int getNbElement();

    /**
     * setter de l'attribut page.
     * @param pPage : numero de la page
     */
    void setPage(int pPage);

    /**
     * setter de l'attribut NbElement.
     * @param pNbElement : nombre d'élément par page.
     */
    void setNbElement(int pNbElement);

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
    Computer showId(long id);

    /**
     * Ecris une requête à la base de donnée afin d'avoir les détails de computer.
     * @param name : nom du computer
     * @return Page<Computer> : une page contenant tout les computer ayant le nom passé en arguments
     */
    Page<Computer> showName(String name);

    /**
     * Ecris une requête à la base de donnée afin d'avoir la liste des computer.
     * @return Page<Computer> : Une page contenant une liste de computer
     */
    Page<Computer> list();

    /**
     * remet page à zero - équivalent à setPage(0).
     */
    void resetPage();

    /**
     * Ecris une requête à la base de donnée afin d'avoir la suite de la liste des computer.
     * @return Page<Computer> : Une page contenant une liste de computer
     */
    Page<Computer> listeSuivante();

    /**
     * Ecris une requête à la base de donnée afin d'avoir la liste suivante des détails de computer.
     * @param name : nom du computer
     * @return Page<Computer> : une page contenant tout les computer ayant le nom passé en arguments
     */
    Page<Computer> showSuivant(String name);
}
