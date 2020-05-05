package org.acme.quickstart;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotExpiredValidator implements ConstraintValidator<NotExpired, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if(value == null) {
            return true;
        }else{
            LocalDate now = LocalDate.now();
            return ChronoUnit.YEARS.between(now, value) > 0;
        }
    }

}