package com.navarna.computerdb.service;

import java.util.ArrayList;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Page;

public interface ServiceCompany {

    /**
     * Demande au DAOCompany la liste des compagnies.
     * @param numPage : numero de page
     * @param nbElement : nombre d'élément par page
     * @return Page<Company> : page de la liste de companie
     */
    Page<Company> liste(int numPage, int nbElement);

    /**
     * Demande au DAOCompany la supression d'une compagnie.
     * @param id : id de la company
     * @return boolean : si oui ou non la base de données à été modifier.
     */
    boolean delete(long id);

    /**
     * Demande au DAOCompany la liste entière des compagnies.
     * @return ArrayList<Company> : liste complete de la company.
     */
    ArrayList<Company> listeComplete();
}
