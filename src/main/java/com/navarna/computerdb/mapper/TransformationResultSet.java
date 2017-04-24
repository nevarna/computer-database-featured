package com.navarna.computerdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Company.CompanyBuilder;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Page;
import com.navarna.computerdb.model.Computer.ComputerBuilder;

public class TransformationResultSet {


    /**
     * extrait du ResultSet une liste de compagnie.
     * @param result : ResultSet de la requête
     * @return Page<Company> : une page contenant la liste de compagnie
     */
    public static Page<Company> extraireListeCompany(ResultSet result, int numPage , int nbElement) {
        try {
            Page<Company> page = new Page<Company>(numPage,nbElement);
            while (result.next()) {
                Long id = result.getLong("id");
                String name = result.getString("name");
                Company company = new CompanyBuilder(name)
                        .setId(id)
                        .build();
                page.addElement(company);
            }
            return page ;
        } catch (SQLException se) {
            throw new MapperException("Erreur de result.next (fonction extraireListeCompany())", se);
        }
    }

    /**
     * transforme une date de type String en date de type LocalDate.
     * @param dateEnString : date en type String
     * @return LocalDate : localDate correspondant au String en arguments
     */
    public static Optional<LocalDate> recupererDate(String dateEnString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss.S");
            LocalDate date = LocalDate.parse(dateEnString, formatter);
            return Optional.of(date);
        } catch (DateTimeParseException pe) {
            return Optional.empty();
        }
    }

    /**
     * extrait du resultSet un computer.
     * @param result : ResultSet de la requête
     * @return Computer : un computer contenant les données du resultSet.
     */
    public static Optional<Computer> extraireDetailsComputer(ResultSet result) {
        try {
            if (result.next()) {
                Long id = result.getLong("id");
                String nameComputer = result.getString("computer.name");
                String tIntroduced = result.getString("introduced");
                LocalDate introduced = null;
                if (tIntroduced != null) {
                    Optional<LocalDate> OpInroduced = recupererDate(tIntroduced);                   
                    introduced = OpInroduced.isPresent()? OpInroduced.get() : null ;
                }
                String tDiscontinued = result.getString("discontinued");
                LocalDate discontinued = null;
                if (tDiscontinued != null) {
                    Optional<LocalDate> OpDiscontinued = recupererDate(tDiscontinued);
                    discontinued = OpDiscontinued.isPresent()? OpDiscontinued.get() : null ;
                }
                Long companyId  = result.getLong("company_id");
                String nameCompany = result.getString("company.name");
                Company company = new CompanyBuilder(nameCompany)
                        .setId(companyId)
                        .build();
                Computer computer = new ComputerBuilder(nameComputer)
                        .setId(id)
                        .setIntroduced(introduced)
                        .setDiscontinued(discontinued)
                        .setCompany(company)
                        .build();
                return Optional.of(computer);
            } else {
                return Optional.empty();
            }
        } catch (SQLException se) {
            throw new MapperException("Erreur de result.next()", se);
        }
    }

    /**
     * extrait du ResultSet une liste de computer avec tout leurs détails.
     * @param result : ResultSet de la requête
     * @return Page<Computer> : une liste de computer
     */
    public static Page<Computer> extraireDetailsComputers(ResultSet result, int numPage, int nbElement) {
        try {
            Page<Computer> page = new Page<Computer>(numPage,nbElement);
            while (result.next()) {
                Long id = result.getLong("id");
                String nameComputer = result.getString("computer.name");
                Timestamp tIntroduced = result.getTimestamp("introduced");
                LocalDate introduced = null;
                if (tIntroduced != null) {
                    introduced = tIntroduced.toLocalDateTime().toLocalDate();
                }
                Timestamp tDiscontinued = result.getTimestamp("discontinued");
                LocalDate discontinued = null;
                if (tDiscontinued != null) {
                    discontinued = tDiscontinued.toLocalDateTime().toLocalDate();
                }
                Long companyId  = result.getLong("company_id");
                String nameCompany = result.getString("company.name");
                Company company = new CompanyBuilder(nameCompany)
                        .setId(companyId)
                        .build();
                Computer computer = new ComputerBuilder(nameComputer)
                        .setId(id)
                        .setIntroduced(introduced)
                        .setDiscontinued(discontinued)
                        .setCompany(company)
                        .build();
                page.addElement(computer);
            }
            return page;
        } catch (SQLException se) {
            throw new MapperException("Erreur de result.next()", se);
        }
    }

    public static int extraireNombreElement (ResultSet result) {
        try {
            if(result.next()) {
                int compteur = result.getInt(1);
                return compteur;
            }
            return 0;
        } catch (SQLException se) {
            throw new MapperException("Erreur de result.next column 0", se);
        }
    }
}
