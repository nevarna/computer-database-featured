package com.navarna.computerdb.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "computer")
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name = "";
    @Column
    private LocalDate introduced;
    @Column
    private LocalDate discontinued;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    public String getName() {
        return this.name;
    }

    @JsonSerialize()
    public LocalDate getIntroduced() {
        return this.introduced;
    }

    public LocalDate getDiscontinued() {
        return this.discontinued;
    }

    public Company getCompany() {
        return this.company;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        String affich = "id : " + this.id + " name : " + this.name + " introduced : " + this.introduced
                + " discontinued : " + this.discontinued + " Company-id " + this.company;
        return affich;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object objet) {
        if (objet == this) {
            return true;
        }
        if (objet == null) {
            return false;
        }
        if (objet instanceof Computer) {
            Computer oComputer = (Computer) objet;
            if ((this.name == oComputer.getName()) && (this.company.equals(oComputer.getCompany()))
                    && (this.introduced.equals(oComputer.getIntroduced()))
                    && (this.discontinued.equals(oComputer.getDiscontinued()))) {
                return true;
            }
        }
        return false;
    }

    public static final class ComputerBuilder {
        private Computer computer;

        /**
         * Constructeur de classe : un argument obligatoire.
         * @param name : String argument obligatoire
         */
        public ComputerBuilder(String name) {
            computer = new Computer();
            computer.name = name;
        }

        /**
         * setter de l'id.
         * @param id : id du computer
         * @return ComputerBuilder : l'instance de classe
         */
        public ComputerBuilder setId(long id) {
            computer.id = id;
            return this;
        }

        /**
         * setter d'introduced.
         * @param introduced : date de mise en marche du computer
         * @return ComputerBuilder : l'instance de classe
         */
        public ComputerBuilder setIntroduced(LocalDate introduced) {
            computer.introduced = introduced;
            return this;
        }

        /**
         * setter de discontinued.
         * @param discontinued : date d'arret du computer
         * @return ComputerBuilder : l'instance de classe
         */
        public ComputerBuilder setDiscontinued(LocalDate discontinued) {
            computer.discontinued = discontinued;
            return this;
        }

        /**
         * setter de company.
         * @param company : Company de l'ordinateur
         * @return ComputerBuilder : l'instance de classe
         */
        public ComputerBuilder setCompany(Company company) {
            computer.company = company;
            return this;
        }

        /**
         * Créer un computer à partir de l'instance de classe.
         * @return Computer: le computer fini
         */
        public Computer build() {
            return computer;
        }
    }
}
