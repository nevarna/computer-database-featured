package com.navarna.computerdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import com.navarna.computerdb.mapper.TransformationResultSet;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;

public final class DAOComputerImpl implements DAOComputer {
	private static final DAOComputerImpl INSTANCE ;
	
	public static int page = 0 ; 
	public static int nbElement = 20 ;
	
	public final static String INSERT ;
	public final static String UPDATE ; 
	public final static String DELETE ;
	public final static String SELECT_LIST ;
	public final static String SHOW_ID ;
	public final static String SHOW_NAME ;
			
	static {
		INSERT = "INSERT INTO computer VALUES ( ?, ?, ?, ?, ? )";
		UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? where id = ?" ; 
		DELETE = "DELETE FROM computer where id = ?";
		SELECT_LIST = "SELECT id,name from computer LIMIT ? OFFSET ?";
		SHOW_ID = "SELECT * from computer left join company on company_id = company.id where computer.id = ?";
		SHOW_NAME = "SELECT * from computer left join company on company_id = company.id where computer.name = ? LIMIT ? OFFSET ?";
		INSTANCE = new DAOComputerImpl();
	}
	
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
			if(computer.getId() != null) {
				statement.setLong(1, computer.getId());
			}
			else {
				statement.setNull(1,Types.BIGINT);
			}
			statement.setString(2, computer.getName());
			if(computer.getIntroduced() != null) {
				statement.setTimestamp(3, Timestamp.valueOf(computer.getIntroduced().atStartOfDay()));
			}
			else {
				statement.setNull(3, Types.TIMESTAMP);
			}
			if(computer.getDiscontinued() != null) {
				statement.setTimestamp(4, Timestamp.valueOf(computer.getDiscontinued().atStartOfDay()));
			}
			else {
				statement.setNull(4, Types.TIMESTAMP);
			}
			if(computer.getCompany().getId() != null) {
				statement.setLong(5, computer.getCompany().getId());
			}
			else {
				statement.setNull(5, Types.BIGINT);
			}
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
			if(computer.getId() != null) {
				statement.setLong(5, computer.getId());
			}
			else {
				statement.setNull(5,Types.BIGINT);
			}
			statement.setString(1, computer.getName());
			if(computer.getIntroduced() != null) {
				statement.setTimestamp(2, Timestamp.valueOf(computer.getIntroduced().atStartOfDay()));
			}
			else {
				statement.setNull(2, Types.TIMESTAMP);
			}
			if(computer.getDiscontinued() != null) {
				statement.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinued().atStartOfDay()));
			}
			else {
				statement.setNull(3, Types.TIMESTAMP);
			}
			if(computer.getCompany().getId() != null) {
				statement.setLong(4, computer.getCompany().getId());
			}
			else {
				statement.setNull(4, Types.BIGINT);
			}
			result = statement.executeUpdate();
			statement.close();
			ConnectionDb.getInstance().close();
			return result; 
		}
		catch(SQLException se) {
			throw new DAOException("Erreur de base de donnée",se);
		}
	}

	@Override
	public int delete(long id) {
		try  {
			Connection conn = ConnectionDb.getInstance().open();
			int result = 0 ; 
			PreparedStatement statement = conn.prepareStatement(DELETE);
			statement.setLong(1, id);
			result = statement.executeUpdate();
			statement.close();
			ConnectionDb.getInstance().close();
			return result; 
		}
		catch(SQLException se) {
			throw new DAOException("Erreur de base de donnée",se);
		}
	}

	@Override
	public Page<Computer> list() {
		try  {
			Connection conn = ConnectionDb.getInstance().open();
			ResultSet result = null; 
			PreparedStatement statement = conn.prepareStatement(SELECT_LIST);
			statement.setInt(1, nbElement);
			statement.setInt(2, page * nbElement);
			result = statement.executeQuery();
			Page<Computer> page = 	TransformationResultSet.extraireListeComputer(result);
			statement.close();
			ConnectionDb.getInstance().close();
			return page; 
		}
		catch(SQLException se) {
			throw new DAOException("Erreur de base de donnée",se);
		}
	}

	@Override
	public Computer showId(long id) {
		try  {
			Connection conn = ConnectionDb.getInstance().open();
			ResultSet result = null; 
			PreparedStatement statement = conn.prepareStatement(SHOW_ID);
			statement.setLong(1, id);
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
			statement.setString(1, name);
			statement.setInt(2, nbElement);
			statement.setInt(3, page * nbElement);
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
	public void resetPage() {
		page = 0 ;
	}
	
	@Override
	public Page<Computer> listeSuivante() {
		page ++ ; 
		return list();
	}

	@Override
	public Page<Computer> showSuivant(String name) {
		page ++ ; 
		return showName(name);
	}
}
