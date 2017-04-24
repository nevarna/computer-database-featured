package com.navarna.computerdb.service;

import java.util.Optional;

import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;
import com.navarna.computerdb.persistence.DAOComputerImpl;

public class ServiceComputerImpl implements ServiceComputer {
    private DAOComputerImpl dComputerImpl = DAOComputerImpl.getInstance();

    @Override
    public int insert(Computer computer) {
        if (computer.getName() != null) {
            return this.dComputerImpl.insert(computer);
        }
        return 0;
    }

    @Override
    public int update(Computer computer) {
        if (computer.getName() != null) {
            return this.dComputerImpl.update(computer);
        }
        return 0;
    }

    @Override
    public int delete(long id) {
        if (id >= 0) {
            return this.dComputerImpl.delete(id);
        }
        return 0;
    }

    @Override
    public Page<Computer> liste(int numPage , int nbElement) {
        return this.dComputerImpl.list(numPage ,nbElement);
    }

    @Override
    public Optional<Computer> show(long id) {
        if (id >= 0) {
            return this.dComputerImpl.showId(id);
        }
        return Optional.empty();
    }

    @Override
    public Page<Computer> show(String name,int numPage , int nbElement) {
        if (name != null) {
            return this.dComputerImpl.showName(name, numPage, nbElement);
        }
        return new Page<Computer>();
    }

    @Override
    public int countComputer() {
        return this.dComputerImpl.countComputer();
    }

    @Override
    public int countComputerName(String name) {
        if(name != null) {
            return this.dComputerImpl.countComputerName(name);
        }
        return 0;
    }
}
