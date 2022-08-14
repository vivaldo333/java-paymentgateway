package com.finaro.validation.constraint;

import com.finaro.validation.CardNumberValid;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CardNumberValidator implements ConstraintValidator<CardNumberValid, String> {
    private static final int PAN_LENGTH = 16;

    @Override
    public void initialize(CardNumberValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String pan, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.length(pan) == PAN_LENGTH && LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(pan);
    }
}
