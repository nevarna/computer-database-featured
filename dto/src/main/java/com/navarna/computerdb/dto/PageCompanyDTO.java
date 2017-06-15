package com.navarna.computerdb.dto;

import java.util.ArrayList;

public class PageCompanyDTO {
    private ArrayList<CompanyDTO> page;
    private int nbPage;
    private int nbElementPage;

    public PageCompanyDTO() {
        this.page = new ArrayList<>();
    }

    /**
     * Constructeur à 2 éléments.
     * @param nbPage : numero de page
     * @param nbElementPage : nombre d'éléments maximum dans la page
     */
    public PageCompanyDTO(int nbPage, int nbElementPage) {
        this.nbElementPage = nbElementPage;
        this.nbPage = nbPage;
        this.page = new ArrayList<>();
    }

    public int getNbPage() {
        return this.nbPage;
    }

    public int getNbElementPage() {
        return this.nbElementPage;
    }

    public void setNbPage(int nbPage) {
        this.nbPage = nbPage < 0 ? 0 : nbPage;
    }

    public void setNbElementPage(int nbElementPage) {
        this.nbElementPage = nbElementPage;
    }

    public ArrayList<CompanyDTO> getPage() {
        return this.page;
    }

    public void setPage(ArrayList<CompanyDTO> page) {
        this.page.addAll(page);
    }

    /**
     * Indique si la page est vide.
     * @return boolean :true or false
     */
    public boolean estVide() {
        return this.page.isEmpty();
    }

    @Override
    public String toString() {
        return "Page [page=" + page + ", nbPage=" + nbPage + ", nbElementPage=" + nbElementPage + "]\n";
    }
}
