package com.navarna.computerdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.navarna.computerdb.mapper.Page;
import com.navarna.computerdb.mapper.TransformationResultSet;
import com.navarna.computerdb.model.Computer;

public final class DAOComputerImpl implements DAOComputer {
	private static final DAOComputerImpl INSTANCE = new DAOComputerImpl();
	
	public static int page = 0 ; 
	public static int nbElement = 20 ;
	
	public final static String INSERT = "INSERT INTO computer VALUES ( ?, ?, ?, ?, ? )";
	public final static String UPDATE = "UPDATE computer SET name = ?, introduce = ?, discontinued = ?, id_company = ? where id = ?" ; 
	public final static String DELETE = "DELETE FROM computer where id = ?";
	public final static String SELECT_LIST = "SELECT id,name from computer LIMIT ? OFFSET ?";
	public final static String SHOW_ID = "SELECT * from computer where id = ?";
	public final static String SHOW_NAME = "SELECT * from computer where name = ? LIMIT ? OFFSET ?";
			
	private DAOComputerImpl() {
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
	
	public static DAOComputerImpl getInstance() {
		return INSTANCE ; 
	}
	@Override
	public int insert(Computer computer) {
		try  {
			Connection conn = ConnectionDb.getInstance().open();
			int result = 0 ; 
			PreparedStatement statement = conn.prepareStatement(INSERT);
			statement.setLong(0, computer.getId());
			statement.setString(1, computer.getName());
			statement.setTimestamp(2, Timestamp.valueOf(computer.getIntroduced().atStartOfDay()));
			statement.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinued().atStartOfDay()));
			statement.setLong(4, computer.getCompany().getId());
			result = statement.executeUpdate();
			statement.close();
			ConnectionDb.getInstance().close();
			return result ; 
		}
		catch(SQLException se) {
			throw new DAOException("Erreur de base de donnée");
		}
	}

	@Override
	public int update(Computer computer) {
		try  {
			Connection conn = ConnectionDb.getInstance().open();
			int result = 0 ; 
			PreparedStatement statement = conn.prepareStatement(UPDATE);
			statement.setString(0, computer.getName());
			statement.setTimestamp(1, Timestamp.valueOf(computer.getIntroduced().atStartOfDay()));
			statement.setTimestamp(2, Timestamp.valueOf(computer.getDiscontinued().atStartOfDay()));
			statement.setLong(3, computer.getCompany().getId());
			statement.setLong(4, computer.getId());
			result = statement.executeUpdate();
			statement.close();
			ConnectionDb.getInstance().close();
			return result; 
		}
		catch(SQLException se) {
			throw new DAOException("Erreur de base de donnée");
		}
	}

	@Override
	public int delete(long id) {
		try  {
			Connection conn = ConnectionDb.getInstance().open();
			int result = 0 ; 
			PreparedStatement statement = conn.prepareStatement(DELETE);
			statement.setLong(0, id);
			result = statement.executeUpdate();
			statement.close();
			ConnectionDb.getInstance().close();
			return result; 
		}
		catch(SQLException se) {
			throw new DAOException("Erreur de base de donnée");
		}
	}

	@Override
	public Page<Computer> list() {
		try  {
			Connection conn = ConnectionDb.getInstance().open();
			ResultSet result = null; 
			PreparedStatement statement = conn.prepareStatement(SELECT_LIST);
			statement.setInt(0, nbElement);
			statement.setInt(1, page * nbElement);
			result = statement.executeQuery();
			Page<Computer> page = 	TransformationResultSet.extraireListeComputer(result);
			statement.close();
			ConnectionDb.getInstance().close();
			return page; 
		}
		catch(SQLException se) {
			throw new DAOException("Erreur de base de donnée");
		}
	}

	@Override
	public Computer showId(long id) {
		try  {
			Connection conn = ConnectionDb.getInstance().open();
			ResultSet result = null; 
			PreparedStatement statement = conn.prepareStatement(SHOW_ID);
			statement.setLong(0, id);
			result = statement.executeQuery();
			Computer computer = TransformationResultSet.extraireDetailsComputer(result);
			statement.close();
			ConnectionDb.getInstance().close();
			return computer; 
		}
		catch(SQLException se) {
			throw new DAOException("Erreur de base de donnée");
		}
	}
	
	@Override
	public Page<Computer> showName(String name) {
		try  {
			Connection conn = ConnectionDb.getInstance().open();
			ResultSet result = null; 
			PreparedStatement statement = conn.prepareStatement(SHOW_NAME);
			statement.setString(0, name);
			statement.setInt(1, nbElement);
			statement.setInt(2, page * nbElement);
			result = statement.executeQuery();
			Page<Computer> page = TransformationResultSet.extraireDetailsComputers(result);
			statement.close();
			ConnectionDb.getInstance().close();
			return page; 
		}
		catch(SQLException se) {
			throw new DAOException("Erreur de base de donnée");
		}
	}
	
	@Override
	public void resetList() {
		page = 0 ;
	}
	
	@Override
	public Page<Computer> suivantList() {
		page ++ ; 
		return list();
	}

	@Override
	public Page<Computer> suivantShow(String name) {
		page ++ ; 
		return showName(name);
	}
}
