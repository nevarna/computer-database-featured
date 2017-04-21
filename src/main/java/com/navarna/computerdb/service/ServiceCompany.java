package com.navarna.computerdb.service;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Page;

public interface ServiceCompany {

    /**
     * Demande au DAOCompany la liste des compagnies.
     * @return Page<Company> : page de la liste de companie
     */
    Page<Company> liste(int numPage , int nbElement);
}
