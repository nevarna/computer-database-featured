package com.navarna.computerdb.service;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Page;
import com.navarna.computerdb.persistence.DAOCompanyImpl;

public class ServiceCompanyImpl implements ServiceCompany {
    private DAOCompanyImpl dCompanyImpl = DAOCompanyImpl.getInstance();

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
}
