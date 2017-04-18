package com.navarna.computerdb.model;

import java.time.LocalDate;

public class Computer {
    private Long id;
    private String name;
    private LocalDate introduced;
    private LocalDate discontinued;
    private Company company;

    /**
     * Constructeur de computer.
     * @param builder : un ComputerBuilder ayant tout les arguments
     */
    public Computer(ComputerBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.company = builder.company;
    }

    public String getName() {
        return this.name;
    }

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
        return this.id == null ? new Long(0) : id;
    }

    @Override
    public String toString() {
        String affich = "id : " + this.id + " name : " + this.name + " introduced : " + this.introduced
                + " discontinued : " + this.discontinued + " Company-id " + this.company;
        return affich;
    }

    @Override
    public boolean equals(Object objet) {
        boolean egal = false;
        if (objet == this) {
            egal = true;
        }
        if (objet instanceof Computer) {
            Computer oComputer = (Computer) objet;
            if ((this.name == oComputer.getName()) && (this.company == oComputer.getCompany())) {
                egal = true;
            }
        }
        return egal;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash += 13 * this.name.hashCode();
        hash += 21 * this.company.hashCode();
        return hash;
    }

    public static final class ComputerBuilder {
        private Long id;
        private String name;
        private LocalDate introduced;
        private LocalDate discontinued;
        private Company company;

        /**
         * Constructeur de classe : un argument obligatoire.
         * @param pName : String argument obligatoire
         */
        public ComputerBuilder(String pName) {
            this.name = pName == null ? "undefined" : pName;
        }

        /**
         * setter de l'id.
         * @param pId : id du computer
         * @return ComputerBuilder : l'instance de classe
         */
        public ComputerBuilder setId(Long pId) {
            this.id = pId;
            return this;
        }

        /**
         * setter d'introduced.
         * @param pIntroduced : date de mise en marche du computer
         * @return ComputerBuilder : l'instance de classe
         */
        public ComputerBuilder setIntroduced(LocalDate pIntroduced) {
            this.introduced = pIntroduced;
            return this;
        }

        /**
         * setter de discontinued.
         * @param pDiscontinued : date d'arret du computer
         * @return ComputerBuilder : l'instance de classe
         */
        public ComputerBuilder setDiscontinued(LocalDate pDiscontinued) {
            this.discontinued = pDiscontinued;
            return this;
        }

        /**
         * setter de company.
         * @param pCompany : Company de l'ordinateur
         * @return ComputerBuilder : l'instance de classe
         */
        public ComputerBuilder setCompany(Company pCompany) {
            this.company = pCompany;
            return this;
        }

        /**
         * Créer un computer à partir de l'instance de classe.
         * @return Computer: le computer fini
         */
        public Computer build() {
            return new Computer(this);
        }
    }
}
