package com.navarna.computerdb.dto;

public class ComputerDTO {
    private long id;
    private String name;
    private String introduced;
    private String discontinued;
    private long idCompany;
    private String nameCompany;

    /**
     * Constructeur vide.
     */
    public ComputerDTO() {
    }

    /**
     * Constucteur avec tout les arguments.
     * @param id : id du computer
     * @param name :nom du computer
     * @param introduced : date de mise en marche
     * @param discontinued : date d'arret
     * @param idCompany : id de la company
     * @param nameCompany : nom de la company
     */
    public ComputerDTO(long id, String name, String introduced, String discontinued, long idCompany,
            String nameCompany) {
        this.id = id;
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.idCompany = idCompany;
        this.nameCompany = nameCompany;
    }

    @Override
    public String toString() {
        return "ComputerDTO [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
                + discontinued + ", idCompany=" + idCompany + ", nameCompany=" + nameCompany + "]";
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

    public String getIntroduced() {
        return introduced;
    }

    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }

    public String getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(String discontinued) {
        this.discontinued = discontinued;
    }

    public long getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(long idCompany) {
        this.idCompany = idCompany;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

}
