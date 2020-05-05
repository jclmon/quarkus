package org.acme.quickstart;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({
    ElementType.METHOD, ElementType.FIELD, ElementType.PACKAGE, ElementType.PARAMETER, ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {NotExpiredValidator.class})
public @interface NotExpired {

    /**
     * mensaje de fallo
     * @return
     */
    String message() default "Beer must not be expired";
    /**
     * Validacion en grupo, mas de una validacion en un grupo de validaciones
     */
    Class<?>[] groups() default { };
    /**
     * Informacion extra, metadata porque ha fallado
     */
    Class<? extends Payload>[] payload() default { };

}