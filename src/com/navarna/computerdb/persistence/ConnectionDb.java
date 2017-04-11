package com.navarna.computerdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.navarna.computerdb.service.ConstruireRequete;

public final class ConnectionDb {
	private final static ConnectionDb _instance = new ConnectionDb() ; 
	private static Connection conn ; 
	private ConnectionDb() {
		// TODO Auto-generated constructor stub
		try {
			System.out.println("CONNECTION EN COURS"); 
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/computer-database-db","root","");
		}
		catch (SQLException se) {
			System.out.println("Echec de la connexion à la base de donnée");
			//se.printStackTrace();
		}
		catch(ClassNotFoundException ce) {
			System.out.println("Class non trouver ");
			//ce.printStackTrace();
		}
	}
	
	public static ConnectionDb getInstance () {
		return _instance;
	}
	
	public boolean envoieQuery (String query , int mode) {
		boolean retour = false ; 
		try  {
			ResultSet result = null ;
			int changementDB = -1 ; 
			Statement statement = conn.createStatement();
			if((mode > -1)&&(mode < 3))
				result = statement.executeQuery(query);
			else {
				 changementDB = statement.executeUpdate(query);
				 if(changementDB ==0)
					 mode = 3 ;
				 else 
					 mode = 4 ; 
			}
			retour = ConstruireRequete.switchResult(result,mode);
			if(result != null)
				result.close();
			statement.close();
		}
		catch(SQLException se) {
			//se.printStackTrace();
			System.out.println("Erreur de base de donnée");
		}
		return retour; 
	}
	
	protected void finalize() throws Throwable {
		try {
			if(conn.isValid(5)) {
				conn.close();
				System.out.println("fermeture base de donnée");
			}
			System.out.println("base de données non fermer ") ; 
		}
		catch(SQLException se) {
			System.out.println("Erreur lors de la fermeture de la base de donnée");
			se.printStackTrace();
		}
	}
}
