package com.navarna.computerdb.service;

import java.util.Optional;

import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;
import com.navarna.computerdb.persistence.DAOComputerImpl;

public class ServiceComputerImpl implements ServiceComputer {
    private DAOComputerImpl dComputerImpl = DAOComputerImpl.getInstance();

    @Override
    public boolean insert(Computer computer) {
        if (computer.getName() != null) {
            return this.dComputerImpl.insert(computer);
        }
        return false;
    }

    @Override
    public boolean update(Computer computer) {
        if (computer.getName() != null) {
            return this.dComputerImpl.update(computer);
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        if (id > 0) {
            return this.dComputerImpl.delete(id);
        }
        return false;
    }

    @Override
    public boolean deleteMultiple(long[] id) {
        if (id.length > 1) {
            return this.dComputerImpl.deleteMultiple(id);
        }
        return false;
    }

    @Override
    public Page<Computer> liste(int numPage, int nbElement, String typeOrder, String order) {
        return this.dComputerImpl.list(numPage, nbElement, typeOrder, order);
    }

    @Override
    public Optional<Computer> findById(long id) {
        if (id >= 0) {
            return this.dComputerImpl.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public Page<Computer> findByName(String name, int numPage, int nbElement, String typeOrder, String order) {
        if (name != null) {
            return this.dComputerImpl.findByName(name, numPage, nbElement, typeOrder, order);
        }
        return new Page<Computer>(0, 0);
    }

    @Override
    public Page<Computer> findByCompany(String nameCompany, int numPage, int nbElement, String typeOrder,
            String order) {
        if (nameCompany != null) {
            return this.dComputerImpl.findByCompany(nameCompany, numPage, nbElement, typeOrder, order);
        }
        return new Page<Computer>(0, 0);
    }

    @Override
    public int count() {
        return this.dComputerImpl.count();
    }

    @Override
    public int countWithName(String name) {
        if (name != null) {
            return this.dComputerImpl.countWithName(name);
        }
        return 0;
    }

    @Override
    public int countWithNameCompany(String nameCompany) {
        if (nameCompany != null) {
            return this.dComputerImpl.countWithNameCompany(nameCompany);
        }
        return 0;
    }
}
