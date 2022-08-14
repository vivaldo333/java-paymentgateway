package com.finaro.validation.constraint;

import com.finaro.mapper.CardMapper;
import com.finaro.validation.NotExpired;
import org.mapstruct.factory.Mappers;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class NotExpiredValidator implements ConstraintValidator<NotExpired, String> {
    private static final CardMapper CARD_MAPPER = Mappers.getMapper(CardMapper.class);

    @Override
    public void initialize(NotExpired constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String textExpireDate, ConstraintValidatorContext constraintValidatorContext) {
        var expireDateOptional = CARD_MAPPER.convert(textExpireDate);
        if (expireDateOptional.isPresent()) {
            var currentDate = LocalDate.now();
            return currentDate.isEqual(expireDateOptional.get()) || currentDate.isBefore(expireDateOptional.get());
        }
        return false;
    }
}
