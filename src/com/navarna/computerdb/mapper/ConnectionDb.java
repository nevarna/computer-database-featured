package com.navarna.computerdb.mapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Scanner;

import com.navarna.computerdb.model.Computer;


public class ConnectionDb {

	public ConnectionDb() {
		// TODO Auto-generated constructor stub
	}
	
	public static String updateInformation () {
		String information = "" ;
		Scanner sc  = new Scanner (System.in); 
		System.out.println("--------- INFORMATION UPDATE ---------\nEntrer des modifications pour les champs à modifier\nSi vous ne souhaitez pas modifier le champ entrez '/noMod' ");
		System.out.println("\nNom de l'ordinateur : ") ; 
		String cInformation ="'"+ sc.nextLine()+"'" ; 
		if(! cInformation.equals("'/noMod'")) {
			information += "name = " ;
			if( cInformation.equals("''")) {
				 cInformation = "NULL" ; 
			}
			information +=  cInformation ; 
		}
		System.out.println("\nchamps introduced de l'ordinateur");
		cInformation ="'"+ sc.nextLine()+"'" ; 
		if(! cInformation.equals("'/noMod'")) {
			if(information != "" ) information +=" , " ; 
			information += "introduced = " ;
			if( cInformation.equals("''")) {
				 cInformation = "NULL" ; 
			}
			information +=  cInformation ; 
		}
		System.out.println("\nchamps discontinued de l'ordinateur");
		cInformation ="'"+ sc.nextLine()+"'" ; 
		if(! cInformation.equals("'/noMod'")) {
			if(information != "" ) information +=" , " ; 
			information += "discontinued = " ;
			if( cInformation.equals("''")) {
				 cInformation = "NULL" ; 
			}
			information +=  cInformation ; 
		}
		System.out.println("id de la company");
		cInformation =sc.nextLine() ; 
		if(! cInformation.equals("/noMod")) {
			if(information != "" ) information +=" , " ; 
			information += "company_id = " ;
			if( cInformation.equals("")||(StringEnInt(cInformation)== -1)) {
				 cInformation = "NULL" ; 
			}
			information +=  cInformation ; 
		}
		sc.close();
		return information ;
	}
	
	public static String createInformation () {
		String information = "NULL, " ;
		Scanner sc  = new Scanner (System.in); 
		String cInformation = "''";
		System.out.println("--------- INFORMATION UPDATE ---------\nEntrer La valeur des champs, ne rien entrer equivaut à mettre la valeur par défaut \n");
		System.out.println("\nNom de l'ordinateur : ") ; 
		cInformation ="'"+ sc.nextLine()+"'" ; 
		if( cInformation.equals("''")) {
			cInformation = "NULL" ; 
		}
		information +=  cInformation ; 
		System.out.println("\nchamps introduced de l'ordinateur");
		cInformation ="'"+ sc.nextLine()+"'" ; 
		information +=" , " ; 
		if( cInformation.equals("''")) {
			cInformation = "NULL" ; 
		}
		information +=  cInformation ; 
		System.out.println("\nchamps discontinued de l'ordinateur");
		cInformation ="'"+ sc.nextLine()+"'" ; 
		information +=" , " ; 
		if( cInformation.equals("''")) {
			cInformation = "NULL" ; 	
		}
		information +=  cInformation ; 
		System.out.println("id de la company");
		cInformation =sc.nextLine() ; 
		information +=" , " ; 
		if( cInformation.equals("")||(StringEnInt(cInformation)== -1)) {
			 cInformation = "NULL" ; 
		}
		information +=  cInformation ; 
		sc.close();
		return information ;
	}

	public static int StringEnInt (String integer) {
		int retour = -1 ; 
		try {
			retour = Integer.valueOf(integer);
		}
		catch (NumberFormatException ne) {
			System.out.println("ERREUR : le second arguments n'est pas un int") ; 
		}
		return retour ;
	}
	
	public static boolean command (String [] command) {
		if(command.length < 2)
			return false ;
		else {
			String query = null  ; 
			int mode = -1 ; 
			switch(command[0]) {
			case "List" :
				if(command[1].equals("computers"))
					query = "SELECT id,name from computer" ; 
				if(command[1].equals("companies"))
					query = "SELECT id,name from company" ; 
				mode = 0 ; 
				break ; 
			case "Show" :
				int ids = StringEnInt(command[1]);
				if(ids != -1)
					query = "SELECT * from computer where id = "+ids ;
				mode = 1 ; 
				break ; 
			case "Update" :
				int idu = StringEnInt(command[1]);
				if(idu != -1) {
					query = "UPDATE computer SET "+updateInformation() +" where id = "+ idu ; 
				}
				mode = 2 ; 
				break ; 
			case "Create" : 
				if(command[1].equals("computer"))
					query = "INSERT INTO computer VALUES (" +createInformation() +")";
				System.out.println(query); 
				mode = 3 ; 
				break ; 
			case "Delete" : 
				int idd = StringEnInt(command[1]);
				if(idd != -1) {
					query = "DELETE FROM computer where id = "+ idd ; 
				}
				mode = 4 ; 
				break ; 
			}
			if(query != null) 
				return envoieQuery(query, mode);
			return false; 
		}
	}
	
	
	public static boolean envoieQuery (String query , int mode) {
		System.out.println("FONCTION ENVOIE_QUERY"); 
		Connection conn  = null;
		boolean retour = false ; 
		try  {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("test sans erreur ");
			String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db";
			String name = "root" ;
			String password = ""; 
			conn = DriverManager.getConnection(url,name,password);
			System.out.println("Execution sans erreur");
			ResultSet result = null ;
			int changementDB = -1 ; 
			Statement statement = conn.createStatement();
			if((mode > -1)&&(mode < 2))
				result = statement.executeQuery(query);
			else {
				 changementDB = statement.executeUpdate(query);
				 System.out.println("changementDB : "+changementDB);
			}
			retour = switchResult(result,mode);
		}
		catch( ClassNotFoundException ce) {
			ce.printStackTrace();
			System.out.println("erreur !!!!!!!!!!!");
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		finally {
			if(conn != null )
				try {
					conn.close();
				}
				catch(SQLException se) {
					se.printStackTrace();
				}
		}
		return retour; 
	}
	
	public static boolean switchResult (ResultSet result , int mode) {
		boolean retour = false ; 
		if((mode < 0)||(mode > 4))
			return retour; 
		switch(mode) {
		case 0 : 
			retour = extraireName(result) ; 
			break ; 
		case 1 : 
			retour = extraireDetail(result);
			break ; 
		default :
			retour = true;
			break ; 
		}	
		return retour ; 
	}
	
	public static boolean extraireName(ResultSet result) {
		boolean retour = false; 
		try {
			while(result.next()) {
				 int id = result.getInt("id");
				 String name = result.getString("name");
				 System.out.println("id : "+id + " name : " + name );
			}
			retour = true ;
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		return retour ;
	}
	
	public static boolean extraireDetail(ResultSet result) {
		boolean retour = false; 
		try {
			while(result.next()) {
				 int id = result.getInt("id");
				 String name = result.getString("name");
				 Timestamp introduced = result.getTimestamp("introduced");
				 Timestamp discontinued =  result.getTimestamp("discontinued");
				 int companyId  = result.getInt("company_id");
				 System.out.println("id : "+id + " name : " + name + " introduced : " + introduced + " discontinued : "+ discontinued + " Company-id "+ companyId);
			}
			retour = true ;
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		return retour ;
	}

}
