package com.finaro.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finaro.dto.masking.MaskStringReplacement;
import com.finaro.dto.masking.SecureStringReplacement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaskingService {
    private final List<SecureStringReplacement> secureStringReplacements;
    private final Map<String, MaskStringReplacement> maskStringReplacements;
    private final ObjectMapper objectMapper;

    public String maskString(String fieldName, String fieldValue) {
        if (StringUtils.isNotBlank(fieldValue)
                && maskStringReplacements.containsKey(fieldName)) {
            var maskConfig = maskStringReplacements.get(fieldName);
            var replaceRepeatTimesConfig = maskConfig.getReplaceRepeatTimes();
            var replaceRepeatTimes = nonNull(replaceRepeatTimesConfig)
                    ? replaceRepeatTimesConfig
                    : fieldValue.length();
            return fieldValue.replaceAll(
                    maskConfig.getRegex(), maskConfig.getReplacement().repeat(replaceRepeatTimes));
        }
        return fieldValue;
    }

    /*
     * For masking sensative info into logs
     * @return text
     */
    public String secureString(String stringToSecure) {
        if (StringUtils.isBlank(stringToSecure)) {
            return stringToSecure;
        }
        StringBuilder securedString = new StringBuilder(stringToSecure);
        try {
            secureStringReplacements.forEach(secureStringReplacement -> {
                Matcher matcher =
                        Pattern.compile(secureStringReplacement.getRegex()).matcher(securedString);
                if (matcher.find()) {
                    int groupToReplaceIndex = // matcher.group(0) returns full match,
                            secureStringReplacement.isGroupReplace()
                                    ? 1
                                    : 0; // matcher.group(1) returns captured group,
                    securedString.replace(
                            matcher.start(groupToReplaceIndex),
                            matcher.end(groupToReplaceIndex),
                            secureStringReplacement.getReplacement());
                }
            });
            return securedString.toString();
        } catch (Exception ex) {
            return StringUtils.EMPTY;
        }
    }

    public <T> String toSecureJson(T object) {
        try {
            return secureString(objectMapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            log.warn("Impossible to convert into JSON", e);
            return StringUtils.EMPTY;
        }
    }
}
