package triana.salesianos.edu.SataApp.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import triana.salesianos.edu.SataApp.validation.validator.PasswordsMatchValidator;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordsMatchValidator.class)
@Documented
public @interface PasswordsMatch {

    String message() default "Las contraseñas introducidas no coinciden";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};

    String passwordField();
    String verifyPasswordField();

}
