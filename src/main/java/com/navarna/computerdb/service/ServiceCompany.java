package com.navarna.computerdb.service;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Page;

public interface ServiceCompany {

    /**
     * Demande au DAOCompany la liste des compagnies.
     * @return Page<Company> : page de la liste de companie
     */
    Page<Company> liste();

    /**
     * Demande au DAOCompany la page suivante de la liste des compagnies.
     * @return Page<Company> : page de la liste de companie
     */
    Page<Company> listeSuivante();

    /**
     * Demande au DAOCompany de changer le numero de la page.
     * @param page : numero de la page
     */
    void choisirPage(int page);

    /**
     * Demande au DAOCompany de changer le nombre d'élément par page.
     * @param nbElement : nombre d'éléments
     */
    void choisirNbElement(int nbElement);

    /**
     * Demande au DAOCompany de remettre le numero de la page à zero.
     */
    void resetPage();
}
