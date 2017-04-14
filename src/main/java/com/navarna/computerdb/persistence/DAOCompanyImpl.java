package com.navarna.computerdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.navarna.computerdb.mapper.TransformationResultSet;
import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Page;

public final class DAOCompanyImpl implements DAOCompany {
	private static final DAOCompanyImpl INSTANCE ;
	
	private static int page = 0 ; 
	private static int nbElement = 20 ;
	
	private static final String SELECT ;
	
	static {
		SELECT  = "SELECT id,name from company LIMIT ? OFFSET ?" ;
		INSTANCE = new DAOCompanyImpl();
	}

	private DAOCompanyImpl () {
		
	}
	public static DAOCompanyImpl getInstance() {
		return INSTANCE ;
	}
	
	@Override
	public int getPage() {
		return page ; 
	}

	@Override
	public int getNbElement() {
		return nbElement ;
	}
	
	@Override
	public void setPage(int pPage) {
		page = pPage ;	
	}

	@Override
	public void setNbElement(int pNbElement) {
		nbElement = pNbElement ;
	}

	@Override
	public Page<Company> list() {
		try  {
			Connection conn = ConnectionDb.getInstance().open();
			ResultSet result = null ;
			PreparedStatement statement = conn.prepareStatement(SELECT);
			statement.setInt(1, nbElement);
			statement.setInt(2, page*nbElement);
			result = statement.executeQuery();
			Page<Company> page = TransformationResultSet.extraireListeCompany(result);
			statement.close();
			return page ;
		}
		catch(SQLException se) {
			throw new DAOException("Erreur de base de donnée");
		}
	}
	
	@Override
	public void resetPage() {
		page = 0 ;
	}
	
	@Override
	public Page<Company> listeSuivante() {
		page ++ ; 
		return list();
	}
	
}
