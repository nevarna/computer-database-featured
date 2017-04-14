package com.navarna.computerdb.service;

import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;

public interface ServiceComputer {
	
	public int insert (Computer computer) ;
	public int update (Computer computer) ;
	public int delete (long id) ;
	public Page<Computer> liste () ;
	public Computer show (long id) ;
	public Page<Computer> show (String name) ;
	public Page<Computer> listeSuivante () ;
	public Page<Computer> showSuivant (String name) ; 
	public void choisirPage (int page) ;
	public void choisirNbElement (int nbElement) ;
	public void resetPage () ;
}
