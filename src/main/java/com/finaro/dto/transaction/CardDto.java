package com.finaro.dto.transaction;

import com.finaro.validation.CardNumberValid;
import com.finaro.validation.NotExpired;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardDto {
    @CardNumberValid
    String pan;
    @NotExpired
    String expiry;
    @NotEmpty(message = "Payment card cvv is required.")
    String cvv;
}
