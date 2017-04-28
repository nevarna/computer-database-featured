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
     * @return boolean : si oui on non la base de données a été modfifié
     */
    boolean insert(Computer computer);

    /**
     * Demande au DAOComputer de modifier le computer.
     * @param computer : Computer à modifier
     * @return boolean : si oui on non la base de données a été modfifié
     */
    boolean update(Computer computer);

    /**
     * Demande au DAOComputer de supprimer un computer ayant un id précis.
     * @param id : id du computer à supprimer
     * @return boolean : si oui on non la base de données a été modfifié
     */
    boolean delete(long id);

    /**
     * Demande au DAOComputer de supprimer des computers ayant un id dans le
     * tableau.
     * @param id : tableau d'id des computer à supprimer
     * @return boolean : si oui on non la base de données a été modfifié
     */
    boolean deleteMultiple(long[] id);

    /**
     * Demande au DAOComputer la liste des computer.
     * @param numPage : numero de page
     * @param nbElement : nombre d'élément par page
     * @param typeOrder : colonne de l'order by
     * @param order : order croissant ou decroissant
     * @return Page<Computer> : page de la liste de computer
     */
    Page<Computer> liste(int numPage, int nbElement, String typeOrder, String order);

    /**
     * Demande au DAOComputer les détails du computer ayant l'id de l'arguments.
     * @param id : id du computer
     * @return Computer : le computer avec tout ses détails
     */
    Optional<Computer> findById(long id);

    /**
     * Demande au DAOComputer les détails des computer ayant le nom de
     * l'arguments.
     * @param name : name des computer
     * @param numPage : numero de page
     * @param nbElement : nombre d'élément par page
     * @param typeOrder : colonne de l'order by
     * @param order : order croissant ou decroissant
     * @return Page<Computer> : page des computer avec tout leus détails
     */
    Page<Computer> findByName(String name, int numPage, int nbElement, String typeOrder, String order);

    /**
     * Demande au DAOComputer les détails des computer ayant le nom de
     * l'arguments.
     * @param nameCompany : name des computer
     * @param numPage : numero de page
     * @param nbElement : nombre d'élément par page
     * @param typeOrder : colonne de l'order by
     * @param order : order croissant ou decroissant
     * @return Page<Computer> : page des computer avec tout leus détails
     */
    Page<Computer> findByCompany(String nameCompany, int numPage, int nbElement, String typeOrder, String order);

    /**
     * Demande au DAOComputer le nombre de computer.
     * @return int : nombre de computer
     */
    int count();

    /**
     * Demande au DAOComputer le nombre de computer ayant le nom de l'arguments.
     * @param name : name des computer
     * @return int : nombre de computer
     */
    int countWithName(String name);

    /**
     * Demande au DAOComputer le nombre de computer ayant le nom de l'arguments
     * en company.
     * @param nameCompany : name de la company
     * @return int : nombre de computer
     */
    int countWithNameCompany(String nameCompany);
}
