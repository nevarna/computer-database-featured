package com.navarna.computerdb.service;

import java.util.Optional;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.message.internal.MessageBodyProviderNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.navarna.computerdb.dto.ComputerDTO;
import com.navarna.computerdb.dto.PageComputerDTO;
import com.navarna.computerdb.exception.CLIException;

public class ServiceComputerRestImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceComputerRestImpl.class);
    private static String baseURL = "http://localhost:8080/ComputerDatabaseMultiModule/api/computer";

    public static PageComputerDTO liste(int page, int element) {
        LOGGER.info("-------->liste(page,element) args: " + page + " - " + element);
        try {
            Client client = ClientBuilder.newClient();
            PageComputerDTO p = client.target(baseURL + "/" + page + "/" + element).request(MediaType.APPLICATION_JSON)
                    .get().readEntity(PageComputerDTO.class);
            return p;
        } catch (NotFoundException e) {
            return new PageComputerDTO();
        } catch (MessageBodyProviderNotFoundException e) {
            return new PageComputerDTO();
        } catch (IllegalArgumentException e) {
            throw new CLIException("illegal argument", e);
        }
    }

    public static PageComputerDTO findByName(String name, int page, int element) {
        LOGGER.info("-------->findByName(name,page,element) args: " + name + " - " + page + " - " + element);
        try {
            Client client = ClientBuilder.newClient();
            PageComputerDTO p = client.target(baseURL + "/" + page + "/" + element + "/" + name)
                    .request(MediaType.APPLICATION_JSON).get().readEntity(PageComputerDTO.class);
            return p;
        } catch (NotFoundException e) {
            return new PageComputerDTO();
        } catch (MessageBodyProviderNotFoundException e) {
            return new PageComputerDTO();
        } catch (IllegalArgumentException e) {
            throw new CLIException("illegal argument", e);
        }
    }

    public static boolean delete(long id) {
        LOGGER.info("-------->delete(id) args: " + id);
        try {
            Client client = ClientBuilder.newClient();
            int status = client.target(baseURL +"/"+ id).request().delete().getStatus();
            return status == 200;
        } catch (NotFoundException e) {
            return false;
        } catch (IllegalArgumentException e) {
            throw new CLIException("illegal argument", e);
        }
    }

    public static Optional<ComputerDTO> findById(int id) {
        LOGGER.info("-------->findById(id) args: " + id);
        Client client = ClientBuilder.newClient();
        try {
            ComputerDTO p = client.target(baseURL + "/" + id).request(MediaType.APPLICATION_JSON_TYPE)
                    .get(ComputerDTO.class);
            return Optional.of(p);
        } catch (NotFoundException e) {
            return Optional.empty();
        } catch (MessageBodyProviderNotFoundException e) {
            return Optional.empty();
        } catch (IllegalArgumentException e) {
            throw new CLIException("illegal argument", e);
        }
    }

    public static boolean insert(ComputerDTO computerDTO) {
        LOGGER.info("-------->insert(compterDTO) args: " + computerDTO);
        try {
            Client client = ClientBuilder.newClient();
            int status = client.target(baseURL).request().put(Entity.entity(computerDTO, MediaType.APPLICATION_JSON))
                    .getStatus();
            return status == 200;
        } catch (IllegalArgumentException e) {
            throw new CLIException("illegal argument", e);
        }
    }

    public static boolean update(ComputerDTO computerDTO) {
        LOGGER.info("-------->update(computerDTO) args: " + computerDTO);
        try {
            Client client = ClientBuilder.newClient();
            System.out.println(computerDTO);
            int status = client.target(baseURL + "/" + computerDTO.getId()).request()
                    .post(Entity.entity(computerDTO, MediaType.APPLICATION_JSON)).getStatus();
            return status == 200;
        } catch (BadRequestException e) {
            return false;
        } catch (IllegalArgumentException e) {
            throw new CLIException("illegal argument", e);
        }

    }

}
