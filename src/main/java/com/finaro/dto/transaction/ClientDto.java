package com.finaro.dto.transaction;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientDto {
    @NotEmpty(message = "Cardholder name is required.")
    String name;
    @NotEmpty(message = "Cardholder email is required.")
    @Email(message = "Invalid cardholder email format.")
    String email;
}
