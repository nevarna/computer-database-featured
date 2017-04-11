package com.navarna.computerdb.service;

import java.util.ArrayList;

public class Page<T> {
	private ArrayList<T> page;
	private int nbPage ; 
	private int nbElementPage ; 
	public Page() {
		this.nbElementPage = -1; 
		this.nbPage = -1; 
		this.page = new ArrayList<T>() ;
	}
	
	public Page (int pNbPage, int pNbElementPage) {
		this.nbElementPage = pNbElementPage ; 
		this.nbPage = pNbPage ; 
		this.page = new ArrayList<T>() ;
	}
	
	public int getNbPage () {
		return this.nbPage ; 
	}
	
	public int getNbElementPage () {
		return this.nbElementPage ; 
	}
	
	public void setNbPage(int pNbPage) {
		this.nbPage = pNbPage < -1 ? -1 : pNbPage ; 
	}
	
	public ArrayList<T> getPage () {
		return this.page ; 
	}
	
	public void addElement(T element) {
		if(this.page.size() < nbElementPage )
			this.page.add(element);
	}
}
