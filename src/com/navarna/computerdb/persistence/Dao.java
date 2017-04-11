package com.navarna.computerdb.persistence;

public abstract class Dao<T> {
	private int page = 0 ; 
	private int nbElement = 20 ;
	
	public int getPage() {
		return this.page ; 
	}
	
	public int getNbElement () {
		return this.nbElement;
	}
	
	public void resetList() {
		// TODO Auto-generated method stub
		page = 0 ;
	}
	
	public boolean suivantList() {
		// TODO Auto-generated method stub
		page ++ ; 
		return list();
	}
	
	public abstract boolean insert(String information);
	public abstract boolean update (int id , String information);
	public abstract boolean delete (int id) ; 
	public abstract boolean show (int id); 
	public abstract boolean list () ; 
	
}
