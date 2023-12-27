package triana.salesianos.edu.SataApp.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.PropertyAccessorFactory;
import triana.salesianos.edu.SataApp.validation.annotation.FieldsValueMatch;

public class FieldsValueMatchValidator implements ConstraintValidator<FieldsValueMatch, Object> {

    private String field;
    private String fieldMatch;

    @Override
    public void initialize(FieldsValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context) {

        Object fieldValue = PropertyAccessorFactory
                .forBeanPropertyAccess(o).getPropertyValue(field);
        Object fieldMatchValue = PropertyAccessorFactory
                .forBeanPropertyAccess(o).getPropertyValue(fieldMatch);

        if (fieldValue != null) {
            return fieldValue.equals(fieldMatchValue);
        } else {
            return fieldMatchValue == null;
        }
    }
}