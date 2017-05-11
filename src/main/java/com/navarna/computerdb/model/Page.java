package com.navarna.computerdb.model;

import java.util.ArrayList;

public class Page<T> {
    private ArrayList<T> page;
    private int nbPage;
    private int nbElementPage;

    /**
     * Constructeur à 2 éléments.
     * @param nbPage : numero de page
     * @param nbElementPage : nombre d'éléments maximum dans la page
     */
    public Page(int nbPage, int nbElementPage) {
        this.nbElementPage = nbElementPage;
        this.nbPage = nbPage;
        this.page = new ArrayList<T>();
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

    public void setNbElement(int nbElementPage) {
        this.nbElementPage = nbElementPage;
    }

    public ArrayList<T> getPage() {
        return this.page;
    }

    /**
     * Indique si la page est vide.
     * @return boolean :true or false
     */
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
