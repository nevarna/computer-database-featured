package com.navarna.computerdb.persistence;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Page;

/**
 * Interface DAOCompany.
 * @author Navarna
 */
public interface DAOCompany {

    /**
     * Demande la requête: liste des companies à la base de données - renvoie le résultat.
     * @return Page<Company> : une page contenant les résultat de la requête
     */
    Page<Company> list(int page , int nbElement);
}
