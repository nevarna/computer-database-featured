package com.navarna.computerdb.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;
import com.navarna.computerdb.service.ServiceComputerImpl;

/**
 * Servlet implementation class Dashboard
 */
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServiceComputerImpl servComputer = new ServiceComputerImpl();
	private String name ;
	private Page<Computer> pageComputer = null ;
	public void changerPage(String page) {
	    try {
	        int numero = page == null ? -2 : Integer.parseInt(page);
	        switch(numero) {
	        case -2 :
	            servComputer.resetPage();
	            break;
	        case -1 :
	            int courant = servComputer.recupererPage();
	            if(courant>0) {
	                courant--;
	                servComputer.choisirPage(courant);
	            }
	            break;
	        case 0 :
	            courant = servComputer.recupererPage();
	            if(courant < 5) {
    	            courant++;
    	            servComputer.choisirPage(courant);
	            }
	            break;
	        case 1 :
	            servComputer.resetPage();
	        case 2 :
	        case 3 :
	        case 4 :
	        case 5 :
	            servComputer.choisirPage(numero-1);
	            break;
	        default:
	            throw new ControllerException("Le nombre parametre de page n'est pas correct");
	        
	        }
	    } catch (NumberFormatException ne) {
	        throw new ControllerException("l'argument parametre de page n'est pas un nombre", ne);
	    }
	}
	
	public String transforme() {
	    String retour = "" ;
	    retour = name.replace("+", "%2B");
	    retour = name.replace(" ", "+");
	    retour = "?search="+retour;
	    return retour;
	}
	
	public void lireParametre (HttpServletRequest request) {
	    String page = request.getParameter("page");
        changerPage(page);
        name = request.getParameter("search");
        if(name == "") {
            name = null;
        }
	}
	
	public void creationListe () {
	    if(name == null) {
	        pageComputer = servComputer.liste();
	    }
	    else {
	        pageComputer = servComputer.show(name);
	    }
	}
	
	public void ecrireAttribute (HttpServletRequest request) {
	    request.setAttribute("computers", pageComputer);
	    if(name != null) {
	        request.setAttribute("name", name);
	        request.setAttribute("research", transforme());
	    }
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    RequestDispatcher fichierJSP = this.getServletContext().getRequestDispatcher("/resources/views/dashboard.jsp");
	    lireParametre(request);
	    creationListe();
	    ecrireAttribute(request);
	    fichierJSP.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
