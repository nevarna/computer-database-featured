package com.navarna.computerdb.service;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.message.internal.MessageBodyProviderNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.navarna.computerdb.dto.PageCompanyDTO;
import com.navarna.computerdb.exception.CLIException;

public class ServiceCompanyRestImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCompanyRestImpl.class);
    private static String baseURL = "http://localhost:8080/ComputerDatabaseMultiModule/api/company/";

    public static PageCompanyDTO liste(int page, int element) {
        LOGGER.info("-------->liste(page,element) args: " + page + " - " + element);
        try {
            Client client = ClientBuilder.newClient();
            PageCompanyDTO p3 = client.target(baseURL + page + "/" + element).request(MediaType.APPLICATION_JSON).get()
                    .readEntity(PageCompanyDTO.class);
            return p3;
        } catch (NotFoundException e) {
            return new PageCompanyDTO();
        } catch (MessageBodyProviderNotFoundException e) {
            return new PageCompanyDTO();
        } catch (IllegalArgumentException e) {
            throw new CLIException("illegal argument", e);
        }
    }

    public static boolean delete(long id) {
        LOGGER.info("-------->delete(id) args: " + id);
        try {
            Client client = ClientBuilder.newClient();
            int status = client.target(baseURL + id).request().delete().getStatus();
            return status == 200;
        } catch (NotFoundException e) {
            return false;
        } catch (IllegalArgumentException e) {
            throw new CLIException("illegal argument", e);
        }

    }
}
