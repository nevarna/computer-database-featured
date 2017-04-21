package com.navarna.computerdb.model;

import java.time.LocalDate;

public class Computer {
    private Long id;
    private String name;
    private LocalDate introduced;
    private LocalDate discontinued;
    private Company company;


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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((company == null) ? 0 : company.hashCode());
        result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
    public boolean equals2(Object objet) {
        if (objet == this) {
            return true;
        }
        if(objet == null) {
            return false;
        }
        if (objet instanceof Computer) {
            Computer oComputer = (Computer) objet;
            if ((this.name == oComputer.getName()) 
                    && (this.company.equals(oComputer.getCompany()))
                    &&(this.introduced.equals(oComputer.getIntroduced()))
                    &&(this.discontinued.equals(oComputer.getDiscontinued()))) {
                return true;
            }
        }
        return false;
    }



    public static final class ComputerBuilder {
        private Computer computer;
        /**
         * Constructeur de classe : un argument obligatoire.
         * @param pName : String argument obligatoire
         */
        public ComputerBuilder(String pName) {
            computer = new Computer();
            computer.name = pName == null ? "undefined" : pName;
        }

        /**
         * setter de l'id.
         * @param pId : id du computer
         * @return ComputerBuilder : l'instance de classe
         */
        public ComputerBuilder setId(Long pId) {
            computer.id = pId;
            return this;
        }

        /**
         * setter d'introduced.
         * @param pIntroduced : date de mise en marche du computer
         * @return ComputerBuilder : l'instance de classe
         */
        public ComputerBuilder setIntroduced(LocalDate pIntroduced) {
            computer.introduced = pIntroduced;
            return this;
        }

        /**
         * setter de discontinued.
         * @param pDiscontinued : date d'arret du computer
         * @return ComputerBuilder : l'instance de classe
         */
        public ComputerBuilder setDiscontinued(LocalDate pDiscontinued) {
            computer.discontinued = pDiscontinued;
            return this;
        }

        /**
         * setter de company.
         * @param pCompany : Company de l'ordinateur
         * @return ComputerBuilder : l'instance de classe
         */
        public ComputerBuilder setCompany(Company pCompany) {
            computer.company = pCompany;
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
