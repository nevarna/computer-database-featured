package com.navarna.computerdb.ui;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Page;

public class ServiceCompanyRestImpl {

    public static Page<Company> liste(int page, int element){
        Client client = ClientBuilder.newClient();
        Page p= client.target("http://localhost:8080/ComputerDatabase/api/company/"+page+"/"+element)
                .request(MediaType.APPLICATION_JSON)
                .get(Page.class);
        return p;
    }

    public static void main(String [] args) {
        System.out.println(liste(1, 3));
    }
}
