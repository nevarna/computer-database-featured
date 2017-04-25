package com.navarna.computerdb.persistence;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Page;

/**
 * Interface DAOCompany.
 * @author Navarna
 */
public interface DAOCompany {

    /**
     * Demande la requête: liste des companies à la base de données - renvoie le
     * résultat.
     * @param page : numero de la page courante
     * @param nbElement : nombre d'élément par page
     * @return Page<Company> : une page contenant les résultat de la requête
     */
    Page<Company> list(int page, int nbElement);

    /**
     * Demande la requête: suppression de la company ayant l'id id et de ses
     * computers.
     * @param id : id de la company
     * @return int : nombre de ligne supprimé
     */
    int delete(long id);
}
