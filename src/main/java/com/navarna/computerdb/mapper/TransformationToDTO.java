package com.navarna.computerdb.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import com.navarna.computerdb.dto.CompanyDTO;
import com.navarna.computerdb.dto.ComputerDTO;
import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Company.CompanyBuilder;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Computer.ComputerBuilder;
import com.navarna.computerdb.model.Page;

public class TransformationToDTO {

    public static Optional<ComputerDTO> ComputerToDTO (Computer computer) {
        if(computer == null) {
            return Optional.empty();
        }
        else {
            ComputerDTO computerDTO = new ComputerDTO();
            if(computer.getId()!=null) {
                computerDTO.setId(computer.getId());
            }
            if(computer.getName()!=null) {
                computerDTO.setName(computer.getName());
            }
            if(computer.getIntroduced() != null) {
                computerDTO.setIntroduced(computer.getIntroduced().toString());
            }
            if(computer.getDiscontinued() != null) {
                computerDTO.setDiscontinued(computer.getDiscontinued().toString());
            }
            if(computer.getCompany() != null) {
                if(computer.getCompany().getId() != null) {
                    computerDTO.setIdCompany(computer.getCompany().getId());
                }
                if(computer.getCompany().getName() !=null) {
                    computerDTO.setNameCompany(computer.getCompany().getName());
                }
            }
            return Optional.of(computerDTO);
        }
    }
    
    public static Optional<Computer> DTOToComputer(ComputerDTO computerDTO) {
        if((computerDTO.getName() == null)||(computerDTO.getNameCompany() == null)) {
            return Optional.empty();
        }
        ComputerBuilder computerBuilder = new ComputerBuilder(computerDTO.getName()).setId(computerDTO.getId());
        CompanyBuilder companyBuilder = new CompanyBuilder(computerDTO.getName()).setId(computerDTO.getIdCompany());
        try {
            if(computerDTO.getIntroduced() != null) {
                LocalDate introduced = LocalDate.parse(computerDTO.getIntroduced());
                computerBuilder.setIntroduced(introduced);
            }
            if(computerDTO.getDiscontinued() != null) {
                LocalDate discontinued = LocalDate.parse(computerDTO.getDiscontinued());
                computerBuilder.setDiscontinued(discontinued);
            }
        } catch(DateTimeParseException de) {    
        }
        computerBuilder.setCompany(companyBuilder.build());
        Computer computer = computerBuilder.build();
        return Optional.of(computer);
    }
    
    public static Optional<CompanyDTO> CompanyToDTO (Company company) {
        if(company.getName() == null){
            return Optional.empty();
        }
        CompanyDTO companyDTO = new CompanyDTO();
        if(company.getId() != null) {
            companyDTO.setId(company.getId());
        }
        return Optional.of(companyDTO);
    }
    
    public static Optional<Company> DTOToCompany (CompanyDTO companyDTO) {
        if(companyDTO.getName() != null) {
        CompanyBuilder companyBuilder = new CompanyBuilder(companyDTO.getName()).setId(companyDTO.getId());
        Company company = companyBuilder.build();
        return Optional.of(company);
        }
        return Optional.empty();
    }
    
    public static Page<ComputerDTO> PageComputerToPageDTO (Page<Computer> page){
        Page<ComputerDTO> pageDTO = new Page<ComputerDTO>(page.getNbPage(),page.getNbElementPage());
        for(Computer computer : page.getPage()) {
            Optional<ComputerDTO> computerDTO = ComputerToDTO(computer); 
            if(computerDTO.isPresent()) {
                pageDTO.addElement(computerDTO.get());
            }
        }
        return pageDTO;
    }
    
    public static Page<CompanyDTO> PageCompanyToPageDTO (Page<Company> page){
        Page<CompanyDTO> pageDTO = new Page<CompanyDTO>(page.getNbPage(),page.getNbElementPage());
        for(Company company : page.getPage()) {
            Optional<CompanyDTO> companyDTO = CompanyToDTO(company);
            if(companyDTO.isPresent()) {
                pageDTO.addElement(companyDTO.get());
            }
        }
        return pageDTO;
    }
}
