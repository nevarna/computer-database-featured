package com.navarna.computerdb.model;

public class Company {
    private Long id;
    private String name;


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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Company other = (Company) obj;
        if(name.equals(other.getName())){
            return true;
        }
        return false;
    }



    public static final class CompanyBuilder {
        private Company company;

        /**
         * Constructeur de classe : un argument obligatoire.
         * @param pName : String argument obligatoire
         */
        public CompanyBuilder(String pName) {
            company = new Company();
            company.name =pName;
        }

        /**
         * setter de l'id.
         * @param pId : id de la company
         * @return CompanyBuilder : l'instance de classe
         */
        public CompanyBuilder setId(Long pId) {
            company.id = pId;
            return this;
        }

        /**
         * Créer une company à partir de l'instance de classe.
         * @return Company : la company fini
         */
        public Company build() {
            return company;
        }
    }
};