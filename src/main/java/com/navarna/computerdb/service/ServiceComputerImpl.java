package com.navarna.computerdb.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;
import com.navarna.computerdb.persistence.DAOComputerImpl;

@Service
public class ServiceComputerImpl implements ServiceComputer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceComputerImpl.class);
    @Autowired
    private DAOComputerImpl dComputerImpl;

    @Override
    public boolean insert(Computer computer) {
        LOGGER.info("-------->insert(computer) args: " + computer);
        if (computer.getName() != null) {
            return this.dComputerImpl.insert(computer);
        }
        return false;
    }

    @Override
    public boolean update(Computer computer) {
        LOGGER.info("-------->update(computer) args: " + computer);
        if (computer.getName() != null) {
            return this.dComputerImpl.update(computer);
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        LOGGER.info("-------->delete(id) args: " + id);
        if (id > 0) {
            return this.dComputerImpl.delete(id);
        }
        return false;
    }

    @Override
    public boolean deleteMultiple(long[] id) {
        LOGGER.info("-------->deleteMultiple()");
        if (id.length > 1) {
            return this.dComputerImpl.deleteMultiple(id);
        }
        return false;
    }

    @Override
    public Page<Computer> liste(int numPage, int nbElement, String typeOrder, String order) {
        LOGGER.info("-------->list(numPage,nbElement,typeOrder,order) args: " + numPage + " - " + nbElement + " - "
                + typeOrder + " - " + order);
        return this.dComputerImpl.list(numPage, nbElement, typeOrder, order);
    }

    @Override
    public Optional<Computer> findById(long id) {
        LOGGER.info("-------->findById(id) args: " + id);
        if (id >= 0) {
            return this.dComputerImpl.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public Page<Computer> findByName(String name, int numPage, int nbElement, String typeOrder, String order) {
        LOGGER.info("-------->findByName(name,numPage,nbElement,typeOrder,order) args: " + name + " - " + numPage
                + " - " + nbElement + " - " + typeOrder + " - " + order);
        if (name != null) {
            return this.dComputerImpl.findByName(name, numPage, nbElement, typeOrder, order);
        }
        return new Page<Computer>(0, 0);
    }

    @Override
    public Page<Computer> findByCompany(String nameCompany, int numPage, int nbElement, String typeOrder,
            String order) {
        LOGGER.info("-------->findByNameCompany(nameCompany,numPage,nbElement,typeOrder,order) args: " + nameCompany
                + " - " + numPage + " - " + nbElement + " - " + typeOrder + " - " + order);
        if (nameCompany != null) {
            return this.dComputerImpl.findByCompany(nameCompany, numPage, nbElement, typeOrder, order);
        }
        return new Page<Computer>(0, 0);
    }

    @Override
    public int count() {
        LOGGER.info("-------->count()");
        return this.dComputerImpl.count();
    }

    @Override
    public int countWithName(String name) {
        LOGGER.info("-------->countWithName(name) args: " + name);
        if (name != null) {
            return this.dComputerImpl.countWithName(name);
        }
        return 0;
    }

    @Override
    public int countWithNameCompany(String nameCompany) {
        LOGGER.info("-------->countWithNameCompany(nameCompany) args: " + nameCompany);
        if (nameCompany != null) {
            return this.dComputerImpl.countWithNameCompany(nameCompany);
        }
        return 0;
    }
}
