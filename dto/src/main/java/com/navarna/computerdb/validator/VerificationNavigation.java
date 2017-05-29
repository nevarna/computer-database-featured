package com.navarna.computerdb.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NavigationValidator.class)
public @interface VerificationNavigation {

    /**
     * retourne le message associer à l'erreur de l'annotation.
     * @return String : message de l'erreur
     */
    String message() default "Erreur dans la verification navigation";

    /**
     * @return Class<?>[] : tableau de classe
     */
    Class<?>[] groups() default {};
    /**
     * @return Class<? extends Payload>[] : tablea de classe.
     */
    Class<? extends Payload>[] payload() default {};

}
