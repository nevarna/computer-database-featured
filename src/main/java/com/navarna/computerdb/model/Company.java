package com.navarna.computerdb.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Company {
    private long id;
    private String name;

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Company)) {
            return false;
        }
        Company other = (Company) obj;
        if (id != other.id) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
    @Component
    public static final class CompanyBuilder {
        private Company company;

        /**
         * Constructeur de classe : un argument obligatoire.
         * @param name : String argument obligatoire
         */
        public CompanyBuilder(String name) {
            company = new Company();
            company.name = name == null ? "" : name;
        }

        /**
         * setter de l'id.
         * @param id : id de la company
         * @return CompanyBuilder : l'instance de classe
         */
        public CompanyBuilder setId(long id) {
            company.id = id;
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