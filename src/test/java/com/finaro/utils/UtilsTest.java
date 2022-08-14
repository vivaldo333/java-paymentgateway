package com.finaro.utils;

import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void encode() {
        var cardExpire = "0624";
        var result = Utils.encode(cardExpire);
        var expectedResult = new String(Base64.getDecoder().decode(result));

        assertThat(expectedResult).isEqualTo(cardExpire);
    }

    @Test
    void decode() {
        var expectedCardExpire = "0624";
        var encodeCardExpire = "MDYyNA==";
        var result = Utils.decode(encodeCardExpire);

        assertThat(result).isEqualTo(expectedCardExpire);
    }
}