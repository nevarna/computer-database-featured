package com.navarna.computerdb.persistence;

public class MainPersistence {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] command = {"Delete","575"};
		boolean reponse = ConnectionDb.command(command);
		System.out.println("reponse : " + reponse);
	}

}
