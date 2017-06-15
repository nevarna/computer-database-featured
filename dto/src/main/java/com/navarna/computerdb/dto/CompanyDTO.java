package com.navarna.computerdb.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CompanyDTO {
    @Min(0)
    private long id;
    @NotNull
    private String name;

    /**
     * Constructeur vide.
     */
    public CompanyDTO() {
    }

    /**
     * Constructeur avec tout les arguments.
     * @param id : id de company
     * @param name : name de company
     */
    public CompanyDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CompanyDTO [id=" + id + ", name=" + name + "]";
    }

}
