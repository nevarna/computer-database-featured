package com.navarna.computerdb.model;

import java.util.ArrayList;

public class Page<T> {
    private ArrayList<T> page;
    private int nbPage;
    private int nbElementPage;

    public Page() {
    }
    /**
     * Constructeur à 2 éléments.
     * @param pNbPage : numero de page
     * @param pNbElementPage : nombre d'éléments maximum dans la page
     */
    public Page(int pNbPage, int pNbElementPage) {
        this.nbElementPage = pNbElementPage;
        this.nbPage = pNbPage;
        this.page = new ArrayList<T>();
    }

    public int getNbPage() {
        return this.nbPage;
    }

    public int getNbElementPage() {
        return this.nbElementPage;
    }

    public void setNbPage(int pNbPage) {
        this.nbPage = pNbPage < 0 ? 0 : pNbPage;
    }

    public ArrayList<T> getPage() {
        return this.page;
    }
    
    public boolean estVide() {
        return this.page.isEmpty();
    }

    /**
     * Ajouter un élément dans l'arrayList.
     * @param element : élément à ajouter dans la liste
     */
    public void addElement(T element) {
        if (this.page.size() < nbElementPage) {
            this.page.add(element);
        }
    }
}
