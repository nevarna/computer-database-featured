package com.navarna.computerdb.persistence;

public interface DAOComputer {
	public static int page = 0 ; 
	public static int nbElement = 20 ;
	
	public int getPage() ;
	public int getNbElement () ;
	public boolean insert(String information) ;
	public boolean update (int id , String information) ;
	public boolean delete (int id) ; 
	public boolean show (int id); 
	public boolean list () ;
	public void resetList() ;
	public boolean suivantList() ;
}
