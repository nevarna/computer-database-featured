package com.navarna.computerdb.persistence;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Page;

/**
 * Interface DAOCompany.
 * @author Navarna
 */
public interface DAOCompany {

    /**
     * getter de l'attribut page.
     * @return numero de la page
     */
    int getPage();

    /**
     * getter de l'attribut nbElement.
     * @return nombre d élément par page
     */
    int getNbElement();

    /**
     * setter de l'attribut page.
     * @param pPage : int
     */
    void setPage(int pPage);

    /**
     * setter de l'attibut Nb_element.
     * @param pNbElement : int
     */
    void setNbElement(int pNbElement);

    /**
     * Demande la requête: liste des companies à la base de données - renvoie le résultat.
     * @return Page<Company> : une page contenant les résultat de la requête
     */
    Page<Company> list();

    /**
     *remet l'attibut page à 0 - équivalent à setPage(0).
     */
    void resetPage();

    /**
     * Demande la page suivante de la requête liste - renvoie le résultat.
     * @return Page<Company> : une page contenant les résultat de la requête
     */
    Page<Company> listeSuivante();
}
