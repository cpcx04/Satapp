package triana.salesianos.edu.SataApp.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import triana.salesianos.edu.SataApp.validation.validator.UniqueUsernameValidator;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Documented
public @interface UniqueUsername {

    String message() default "El nombre de usuario ya existe";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}