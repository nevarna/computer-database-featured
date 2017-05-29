package com.navarna.computerdb.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import com.navarna.computerdb.dto.CompanyDTO;
import com.navarna.computerdb.dto.ComputerDTO;
import com.navarna.computerdb.model.Company;
import com.navarna.computerdb.model.Company.CompanyBuilder;
import com.navarna.computerdb.model.Computer;
import com.navarna.computerdb.model.Computer.ComputerBuilder;
import com.navarna.computerdb.model.Page;

public class TransformationToDTO {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransformationToDTO.class);
    private static final String DATE_FRANCAISE = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})";
    private static final String DATE_FRANCAISE_ENVERS = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])";
    private static final String DATE_ANGLAISE = "^(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])-[0-9]{4}";

    /**
     * Transforme un Computer en ComputerDTO.
     * @param computer : computer à transformer
     * @return Optional<ComputerDTO> : computerDTO correspondant à computer
     */
    public static Optional<ComputerDTO> computerToDTO(Computer computer) {
        LOGGER.info("-------->computerToDTO(computer) args :" + computer);
        if (computer == null) {
            return Optional.empty();
        } else {
            ComputerDTO computerDTO = new ComputerDTO();
            computerDTO.setId(computer.getId());
            computerDTO.setName(computer.getName() != null ? computer.getName() : "");
            computerDTO.setIntroduced(computer.getIntroduced() != null ? computer.getIntroduced().toString() : "");
            computerDTO
                    .setDiscontinued(computer.getDiscontinued() != null ? computer.getDiscontinued().toString() : "");
            if (computer.getCompany() != null) {
                computerDTO.setIdCompany(computer.getCompany().getId());
                computerDTO
                        .setNameCompany(computer.getCompany().getName() != null ? computer.getCompany().getName() : "");

            }
            return Optional.of(computerDTO);
        }
    }

    /**
     * Transforme un string en LocalDate selon la locale.
     * @param date : date en string
     * @return LocalDate : localDate associer au string selon la locale
     */
    public static LocalDate getLocalDate(String date) {
        LOGGER.info("-------->getLocalDate(date) args: " + date);
        Locale local = LocaleContextHolder.getLocale();
        if (local.equals(Locale.FRENCH)) {
            if (Pattern.matches(DATE_FRANCAISE, date)) {
                return stringEnDate(date, "dd-MM-yyyy");
            }
            if (Pattern.matches(DATE_FRANCAISE_ENVERS, date)) {
                return stringEnDate(date, "yyyy-MM-dd");
            }
        } else {
            if (Pattern.matches(DATE_ANGLAISE, date)) {
                return stringEnDate(date, "MM-dd-yyyy");
            }
        }
        return null;
    }

    /**
     * Vérifie si la date est correcte.
     * @param date : tableau de string représentant une date decouper en date et
     *            heure.
     * @param format : format de la date
     * @return boolean : reponse si oui ou non la date est correct
     */
    public static LocalDate stringEnDate(String date, String format) {
        LOGGER.info("-------->StringEnDate(date,format) args: " + date + " - " + format);
        SimpleDateFormat df = new SimpleDateFormat(format);
        df.setLenient(false);
        try {
            return df.parse(date).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException pe) {
            return null;
        }
    }

    /**
     * Transforme un ComputerDTO en Computer.
     * @param computerDTO : ComputerDTO à transformer
     * @return Optional<Computer> : Computer correspondant au ComputerDTO
     */
    public static Optional<Computer> dtoToComputer(ComputerDTO computerDTO) {
        LOGGER.info("-------->dtoToComputer(computerDTO) args :" + computerDTO);
        if (computerDTO.getName() == null) {
            return Optional.empty();
        }
        ComputerBuilder computerBuilder = new Computer.ComputerBuilder(computerDTO.getName())
                .setId(computerDTO.getId());
        CompanyBuilder companyBuilder = new Company.CompanyBuilder(computerDTO.getNameCompany())
                .setId(computerDTO.getIdCompany());
        try {
            if (computerDTO.getIntroduced() != null) {
                LocalDate introduced = getLocalDate(computerDTO.getIntroduced());
                computerBuilder.setIntroduced(introduced);
            }
            if (computerDTO.getDiscontinued() != null) {
                LocalDate discontinued = getLocalDate(computerDTO.getDiscontinued());
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
        LOGGER.info("-------->companyToDTO(company) args :" + company);
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
        LOGGER.info("-------->dtoToCompany(companyDTO) args :" + companyDTO);
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
        LOGGER.info("-------->pageComputerToPageDTO(page)");
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
        LOGGER.info("-------->pageCompanyToPageDTO(page)");
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
     * @return ArrayList<CompanyDTO> : arrayList de companyDTO correspondant à
     *         page
     */
    public static ArrayList<CompanyDTO> arraylistCompanyToArraylistDTO(ArrayList<Company> liste) {
        LOGGER.info("-------->arraylistCompanyToArraylistDTO(liste)");
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
