package com.navarna.computerdb.dto;

import java.util.ArrayList;

public class PageComputerDTO {

    private ArrayList<ComputerDTO> page;
    private int nbPage;
    private int nbElementPage;

    public PageComputerDTO() {
        this.page = new ArrayList<>();
    }

    /**
     * Constructeur à 2 éléments.
     * @param nbPage : numero de page
     * @param nbElementPage : nombre d'éléments maximum dans la page
     */
    public PageComputerDTO(int nbPage, int nbElementPage) {
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

    public ArrayList<ComputerDTO> getPage() {
        return this.page;
    }

    public void setPage(ArrayList<ComputerDTO> page) {
        this.page  = new ArrayList<>();
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
