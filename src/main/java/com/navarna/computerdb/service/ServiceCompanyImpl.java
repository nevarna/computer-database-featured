package com.navarna.computerdb.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Page;
import com.navarna.computerdb.persistence.DAOCompanyImpl;

@Service
public class ServiceCompanyImpl implements ServiceCompany {
    @Autowired
    @Qualifier("DAOCompanyImpl")
    private DAOCompanyImpl dCompanyImpl;

    @Override
    public Page<Company> liste(int numPage, int nbElement) {
        return this.dCompanyImpl.list(numPage, nbElement);
    }

    @Override
    public int delete(long id) {
        if (id > 0) {
            return this.dCompanyImpl.delete(id);
        }
        return 0;
    }

    @Override
    public ArrayList<Company> listeComplete() {
        return this.dCompanyImpl.listeComplete();
    }
}
