package com.navarna.computerdb.persistence;

import com.navarna.computerdb.model.Computer;

public interface DAOComputer {
	
	public int getPage() ;
	public int getNbElement () ;
	public boolean insert(Computer computer) ;
	public boolean update (Computer computer) ;
	public boolean delete (long id) ; 
	public boolean show (long id) ; 
	public boolean show (String name) ;
	public boolean list () ;
	public void resetList() ;
	public boolean suivantList() ;
	public boolean suivantShow(String name) ; 
}
