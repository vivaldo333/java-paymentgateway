package com.finaro.dto.transaction;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    @NotNull(message = "Payment invoice is required.")
    Long invoice;
    @NotNull(message = "Payment amount is required.")
    @Positive(message = "Payment amount should be positive.")
    Long amount;
    @NotEmpty(message = "Payment currency is required.")
    String currency;
    @NotNull(message = "Payment cardholder is required.") @Valid
    ClientDto cardholder;
    @NotNull(message = "Payment card is required.") @Valid
    CardDto card;
}
