package com.navarna.computerdb.persistence;

import com.navarna.computerdb.mapper.Page;
import com.navarna.computerdb.model.Computer;

public interface DAOComputer {
	
	public int getPage() ;
	public int getNbElement () ;
	public void setPage (int pPage) ;
	public void setNbElement (int pNbElement) ;
	public int insert(Computer computer) ;
	public int update (Computer computer) ;
	public int delete (long id) ; 
	public Computer showId (long id) ; 
	public Page<Computer> showName (String name) ;
	public Page<Computer> list () ;
	public void resetPage() ;
	public Page<Computer> listeSuivante() ;
	public Page<Computer> showSuivant(String name) ; 
}
