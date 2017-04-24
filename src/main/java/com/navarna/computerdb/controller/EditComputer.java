package com.navarna.computerdb.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.navarna.computerdb.dto.CompanyDTO;
import com.navarna.computerdb.mapper.TransformationToDTO;
import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;
import com.navarna.computerdb.model.Company.CompanyBuilder;
import com.navarna.computerdb.model.Computer.ComputerBuilder;
import com.navarna.computerdb.service.ServiceCompanyImpl;
import com.navarna.computerdb.service.ServiceComputerImpl;
import com.navarna.computerdb.validator.ValidationEntrer;

/**
 * Servlet implementation class EditComputer
 */
public class EditComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServiceComputerImpl servComputer = new ServiceComputerImpl();
	 private ServiceCompanyImpl servCompany = new ServiceCompanyImpl(); 
	private long id = 0;
    private int numPage = 0 ;
    private int nbElement = 100;
    private Integer reponse = null;   
	public void setIdComputer (String idLien) {
	       try {
	            int numero = idLien == null ? -1 : Integer.parseInt(idLien);
	            if (numero < 1) {
	                throw new ControllerException("Le nombre parametre de page n'est pas correct");
	            }
	            else {
	                this.id = numero;
	            }
	        } catch (NumberFormatException ne) {
	            throw new ControllerException("l'argument parametre de page n'est pas un nombre", ne);
	        }
	}
	
    protected ArrayList<CompanyDTO> initialisationListeCompany() {
        ArrayList<CompanyDTO> informationCompany = new ArrayList<CompanyDTO>();
        boolean fini = false;
        numPage = 0;
        while (!fini) {
            Page<CompanyDTO> page = TransformationToDTO.pageCompanyToPageDTO(servCompany.liste(numPage, nbElement));
            numPage++;
            if (page.estVide()) {
                fini = true;
            } else {
                for (CompanyDTO company : page.getPage()) {
                    informationCompany.add(company);
                }
            }
        }
        return informationCompany;
    }
    
    public void demandeUpdate (String name, String introduced, String discontinued, String idCompany) {
        if (ValidationEntrer.entrerValide(name, introduced, discontinued, idCompany)) {             
            LocalDate pIntroduced = LocalDate.parse(introduced);                                    
            LocalDate pDiscontinued = LocalDate.parse(discontinued);                                
            int CompanyId = ValidationEntrer.stringEnIntPositif(idCompany);                                
            Company company = new CompanyBuilder("null")                                            
                    .setId(new Long(CompanyId))                                                            
                    .build();  
            Computer computer = new ComputerBuilder(name)
                    .setId(this.id)
                    .setIntroduced(pIntroduced)                                                     
                    .setDiscontinued(pDiscontinued)                                                 
                    .setCompany(company)                                                                                                                                 
                    .build();                            
            reponse = servComputer.update(computer);                                                
        }                                                                                           
                                                                                                    
    }
	
	public void lireParametreGet (HttpServletRequest request) {
	    if(id == 0 ) {
	        String idLien = request.getParameter("id");
	        setIdComputer(idLien);
	    }
	}
	
	public void lireParametrePost(HttpServletRequest request) {
	    String name = request.getParameter("name");               
	    String introduced = request.getParameter("introduced");   
	    String discontinued = request.getParameter("discontinued");
	    String idCompany = request.getParameter("idCompany");     
	    demandeUpdate(name, introduced, discontinued, idCompany); 
	}
	
    public void ecrireAttribute(HttpServletRequest request) {                        
        ArrayList<CompanyDTO> informationCompany = initialisationListeCompany();     
        request.setAttribute("listeCompany", informationCompany);
        request.setAttribute("id", id);
        if(reponse!=null) {                                                          
            request.setAttribute("reponse", reponse);                                
            reponse = null;                                                          
        }                                                                            
    }                                                                                
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    RequestDispatcher fichierJSP = this.getServletContext().getRequestDispatcher("/resources/views/editComputer.jsp");
	    lireParametreGet(request);
	    ecrireAttribute(request);    
	    fichierJSP.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		lireParametrePost(request);
		doGet(request, response);
	}

}
