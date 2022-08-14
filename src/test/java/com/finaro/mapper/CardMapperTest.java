package com.finaro.mapper;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class CardMapperTest {

    private CardMapper testInstance = new CardMapperImpl();

    @Test
    void convert_shouldReturnLastDateOfJune2021_WhenTextDateIsValid() {
        var textExpireCardDate = "0621";
        var result = testInstance.convert(textExpireCardDate);
        assertThat(result).isPresent()
                .get()
                .isEqualTo(LocalDate.of(2021, 6, 30));
    }
}