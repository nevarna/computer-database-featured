package com.navarna.computerdb.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Page;
import com.navarna.computerdb.persistence.DAOCompanyImpl;
import com.navarna.computerdb.persistence.DAOComputerImpl;

@Service
public class ServiceCompanyImpl implements ServiceCompany {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCompanyImpl.class);
    @Autowired
    private DAOCompanyImpl dCompanyImpl;
    @Autowired
    private DAOComputerImpl dComputerImpl;

    @Override
    public Page<Company> liste(int numPage, int nbElement) {
        LOGGER.info("-------->liste(numPage,nbElement) args: "+numPage+" - "+nbElement);
        return this.dCompanyImpl.list(numPage, nbElement);
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        LOGGER.info("-------->delete(id) args: "+id);
        if (id > 0) {
            if(this.dComputerImpl.deleteCompany(id)) {
                return this.dCompanyImpl.delete(id);
            }
        }
        return false;
    }

    @Override
    public ArrayList<Company> listeComplete() {
        LOGGER.info("-------->listeComplete()");
        return this.dCompanyImpl.listeComplete();
    }
}
