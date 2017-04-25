package com.navarna.computerdb.dto;

public class CompanyDTO {
    private long id;
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

}
