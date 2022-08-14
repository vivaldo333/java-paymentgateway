package com.finaro.validation;

import com.finaro.validation.constraint.NotExpiredValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotExpiredValidator.class)
public @interface NotExpired {
    String message() default "Payment card is expired.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
