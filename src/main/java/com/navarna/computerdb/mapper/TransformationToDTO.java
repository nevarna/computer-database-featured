package com.navarna.computerdb.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Optional;

import com.navarna.computerdb.dto.CompanyDTO;
import com.navarna.computerdb.dto.ComputerDTO;
import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Company.CompanyBuilder;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Computer.ComputerBuilder;
import com.navarna.computerdb.model.Page;

public class TransformationToDTO {

    /**
     * Transforme un Computer en ComputerDTO.
     * @param computer : computer à transformer
     * @return Optional<ComputerDTO> : computerDTO correspondant à computer
     */
    public static Optional<ComputerDTO> computerToDTO(Computer computer) {
        if (computer == null) {
            return Optional.empty();
        } else {
            ComputerDTO computerDTO = new ComputerDTO();
            if (computer.getId() != 0) {
                computerDTO.setId(computer.getId());
            }
            if (computer.getName() != null) {
                computerDTO.setName(computer.getName());
            }
            if (computer.getIntroduced() != null) {
                computerDTO.setIntroduced(computer.getIntroduced().toString());
            }
            if (computer.getDiscontinued() != null) {
                computerDTO.setDiscontinued(computer.getDiscontinued().toString());
            }
            if (computer.getCompany() != null) {
                if (computer.getCompany().getId() != 0) {
                    computerDTO.setIdCompany(computer.getCompany().getId());
                }
                if (computer.getCompany().getName() != null) {
                    computerDTO.setNameCompany(computer.getCompany().getName());
                }
            }
            return Optional.of(computerDTO);
        }
    }

    /**
     * Transforme un ComputerDTO en Computer.
     * @param computerDTO : ComputerDTO à transformer
     * @return Optional<Computer> : Computer correspondant au ComputerDTO
     */
    public static Optional<Computer> dtoToComputer(ComputerDTO computerDTO) {
        if ((computerDTO.getName() == null) || (computerDTO.getNameCompany() == null)) {
            return Optional.empty();
        }
        ComputerBuilder computerBuilder = new Computer.ComputerBuilder(computerDTO.getName()).setId(computerDTO.getId());
        CompanyBuilder companyBuilder = new Company.CompanyBuilder(computerDTO.getName()).setId(computerDTO.getIdCompany());
        try {
            if (computerDTO.getIntroduced() != null) {
                LocalDate introduced = LocalDate.parse(computerDTO.getIntroduced());
                computerBuilder.setIntroduced(introduced);
            }
            if (computerDTO.getDiscontinued() != null) {
                LocalDate discontinued = LocalDate.parse(computerDTO.getDiscontinued());
                computerBuilder.setDiscontinued(discontinued);
            }
        } catch (DateTimeParseException de) {
        }
        computerBuilder.setCompany(companyBuilder.build());
        Computer computer = computerBuilder.build();
        return Optional.of(computer);
    }

    /**
     * Transformation de Company en CompanyDTO.
     * @param company : Company à tranformer
     * @return Optional<CompanyDTO> : CompanyDTO correspondant à la company
     */
    public static Optional<CompanyDTO> companyToDTO(Company company) {
        if (company.getName() == null) {
            return Optional.empty();
        }
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setName(company.getName());
        if (company.getId() != 0) {
            companyDTO.setId(company.getId());
        }
        return Optional.of(companyDTO);
    }

    /**
     * Transforme une companyDTO en Company.
     * @param companyDTO : CompanyDTO à transformer
     * @return Optional<Company> : Company transformer en CompanyDTO
     */
    public static Optional<Company> dtoToCompany(CompanyDTO companyDTO) {
        if (companyDTO.getName() != null) {
            Company company = new Company.CompanyBuilder(companyDTO.getName()).setId(companyDTO.getId()).build();
            return Optional.of(company);
        }
        return Optional.empty();
    }

    /**
     * Transforme une page Computer en une page ComputerDTO.
     * @param page : page de computer à transformer
     * @return Page<ComputerDTO> : page de computerDTO correspondant à page
     */
    public static Page<ComputerDTO> pageComputerToPageDTO(Page<Computer> page) {
        Page<ComputerDTO> pageDTO = new Page<ComputerDTO>(page.getNbPage(), page.getNbElementPage());
        for (Computer computer : page.getPage()) {
            Optional<ComputerDTO> computerDTO = computerToDTO(computer);
            if (computerDTO.isPresent()) {
                pageDTO.addElement(computerDTO.get());
            }
        }
        return pageDTO;
    }

    /**
     * Transforme une page Company en une page CompanyDTO.
     * @param page : page de company à transformer
     * @return Page<CompanyDTO> : page de companyDTO correspondant à page
     */
    public static Page<CompanyDTO> pageCompanyToPageDTO(Page<Company> page) {
        Page<CompanyDTO> pageDTO = new Page<CompanyDTO>(page.getNbPage(), page.getNbElementPage());
        for (Company company : page.getPage()) {
            Optional<CompanyDTO> companyDTO = companyToDTO(company);
            if (companyDTO.isPresent()) {
                pageDTO.addElement(companyDTO.get());
            }
        }
        return pageDTO;
    }

    /**
     * Transforme une ArrayList Company en une ArrayList CompanyDTO.
     * @param liste : liste de company à transformer
     * @return ArrayList<CompanyDTO> : arrayList de companyDTO correspondant à page
     */
    public static ArrayList<CompanyDTO> arraylistCompanyToArraylistDTO(ArrayList<Company> liste) {
        ArrayList<CompanyDTO> listeDTO = new ArrayList<CompanyDTO>();
        for (Company company : liste) {
            Optional<CompanyDTO> companyDTO = companyToDTO(company);
            if (companyDTO.isPresent()) {
                listeDTO.add(companyDTO.get());
            }
        }
        return listeDTO;
    }
}
