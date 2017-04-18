package com.navarna.computerdb.service;

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
    public Page<Computer> liste() {
        return this.dComputerImpl.list();
    }

    @Override
    public Computer show(long id) {
        if (id >= 0) {
            return this.dComputerImpl.showId(id);
        }
        return null;
    }

    @Override
    public Page<Computer> show(String name) {
        if (name != null) {
            return this.dComputerImpl.showName(name);
        }
        return null;
    }

    @Override
    public Page<Computer> listeSuivante() {
        return this.dComputerImpl.listeSuivante();
    }

    @Override
    public Page<Computer> showSuivant(String name) {
        return this.dComputerImpl.showSuivant(name);
    }

    @Override
    public void choisirPage(int page) {
        this.dComputerImpl.setPage(page);
    }

    @Override
    public void choisirNbElement(int nbElement) {
        this.dComputerImpl.setNbElement(nbElement);
    }

    @Override
    public void resetPage() {
        this.dComputerImpl.resetPage();
    }

}
