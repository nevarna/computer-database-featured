package com.navarna.computerdb.validator;

import java.time.LocalDate;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import com.navarna.computerdb.dto.ComputerDTO;
import com.navarna.computerdb.mapper.TransformationToDTO;

public class ComputerDTOValidator implements ConstraintValidator<VerificationComputerDTO, ComputerDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDTOValidator.class);
    private static final String regexDateFrancaise = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})";
    private static final String regexDateFrancaiseEnvers = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])";
    private static final String regexDateAnglaise = "^(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])-[0-9]{4}";
    private Locale langue;

    @Override
    public void initialize(VerificationComputerDTO arg0) {
        langue = LocaleContextHolder.getLocale();
    }

    @Override
    public boolean isValid(ComputerDTO computerDTO, ConstraintValidatorContext arg1) {
        LOGGER.info("-------->isValide(ComputerDTO, validatorContext) args: " + computerDTO);
        return verificationName(computerDTO.getName())
                && (verificationDateValide(computerDTO.getIntroduced(), computerDTO.getDiscontinued()));
    }

    /**
     * Verifie que les dates de computerDto sont valide.
     * @param introducedDTO : date introduced en String
     * @param discontinuedDTO : date discontinued en String
     * @return boolean : si la date est valide
     */
    public boolean verificationDateValide(String introducedDTO, String discontinuedDTO) {
        LOGGER.info("-------->verificationDateValide(introducedDTO,discontinuedDTO) args: " + introducedDTO + " - "
                + discontinuedDTO);
        if (formatDate(introducedDTO) && (formatDate(discontinuedDTO))) {
            LocalDate introduced = TransformationToDTO.getLocalDate(introducedDTO);
            LocalDate discontinued = TransformationToDTO.getLocalDate(discontinuedDTO);
            return dateLogique(introduced, discontinued);
        }
        return false;
    }

    /**
     * verifie que le format de la date est correct.
     * @param date : date en string
     * @return boolean : si oui ou non le format est correct
     */
    public boolean formatDate(String date) {
        LOGGER.info("-------->getLocalDate(date) args: " + date);
        if (this.langue.equals(Locale.FRENCH)) {
            return (Pattern.matches(regexDateFrancaise, date)) || (Pattern.matches(regexDateFrancaiseEnvers, date))
                    || (date.equals(""));
        } else {
            return ((Pattern.matches(regexDateAnglaise, date)) || (date.equals("")));
        }
    }

    /**
     * Vérifie la logique entre les dates.
     * @param introduced : date de debut
     * @param discontinued : date de fin
     * @return boolean : si les 2 valeurs sont logique entre elles
     */
    public static boolean dateLogique(LocalDate introduced, LocalDate discontinued) {
        LOGGER.info("-------->dateLogique(introduced,discontinued) args: " + introduced + " - " + discontinued);
        if ((introduced == null) && (discontinued == null)) {
            return true;
        }
        if ((introduced == null) && (discontinued != null)) {
            return false;
        }
        if ((introduced != null) && (discontinued == null)) {
            return true;
        }
        if ((introduced == discontinued) || (introduced.equals(discontinued))) {
            return true;
        }
        return introduced.isBefore(discontinued);
    }

    /**
     * indique le nom du computerDTO est correct (non null, non vide).
     * @param name : string name
     * @return boolean : si oui ou non le nom est correct
     */
    public static boolean verificationName(String name) {
        LOGGER.info("-------->verificationName(name) args: " + name);
        if (name == null) {
            return false;
        }
        return !name.equals("");
    }
}
