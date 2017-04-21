package com.navarna.computerdb.model;

public class Company {
    private Long id;
    private String name;

    /**
     * Constructeur de Company.
     * @param builder : Un CompanyBuilder ayant tout les arguments
     */
    public Company(CompanyBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        String affiche = "id : " + this.id + " name : " + this.name;
        return affiche;
    }

    @Override
    public boolean equals(Object objet) {
        boolean egal = false;
        if (this == objet) {
            egal = true;
        } else if (objet instanceof Company) {
            Company oCompany = (Company) objet;
            if (this.name == oCompany.getName()) {
                egal = true;
            }
        }
        return egal;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash += this.name.hashCode();
        return hash;
    }

    public static final class CompanyBuilder {
        private Long id;
        private String name;

        /**
         * Constructeur de classe : un argument obligatoire.
         * @param pName : String argument obligatoire
         */
        public CompanyBuilder(String pName) {
            this.name = pName;
        }

        /**
         * setter de l'id.
         * @param pId : id de la company
         * @return CompanyBuilder : l'instance de classe
         */
        public CompanyBuilder setId(Long pId) {
            this.id = pId;
            return this;
        }

        /**
         * Créer une company à partir de l'instance de classe.
         * @return Company : la company fini
         */
        public Company build() {
            return new Company(this);
        }
    }
}