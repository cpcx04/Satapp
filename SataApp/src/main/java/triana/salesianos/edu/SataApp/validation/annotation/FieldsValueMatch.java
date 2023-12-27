package triana.salesianos.edu.SataApp.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import triana.salesianos.edu.SataApp.validation.validator.FieldsValueMatchValidator;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldsValueMatchValidator.class)
@Documented
public @interface FieldsValueMatch {

    String message() default "Los valores de los campos no coinciden";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String field();
    String fieldMatch();

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        FieldsValueMatch[] value();
    }


}
