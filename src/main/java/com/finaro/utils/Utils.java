package com.finaro.utils;

import lombok.experimental.UtilityClass;

import java.util.Base64;

@UtilityClass
public class Utils {

    public static String encode(String originalText) {
        return Base64.getEncoder().encodeToString(originalText.getBytes());
    }

    public static String decode(String encodedText) {
        return new String(Base64.getDecoder().decode(encodedText));
    }
}
