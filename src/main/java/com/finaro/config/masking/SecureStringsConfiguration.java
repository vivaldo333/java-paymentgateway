package com.finaro.config.masking;

import com.finaro.dto.masking.MaskStringReplacement;
import com.finaro.dto.masking.SecureStringReplacement;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

@Configuration
@EnableConfigurationProperties(SecureStringsProperties.class)
public class SecureStringsConfiguration {
    @Bean
    public List<SecureStringReplacement> secureStringReplacements(
            SecureStringsProperties properties) {
        List<SecureStringReplacement> secureStringReplacements = new ArrayList<>();
        if (nonNull(properties)) {
            secureStringReplacements.addAll(properties.getLog());
        }
        return secureStringReplacements;
    }

    @Bean
    public Map<String, MaskStringReplacement> maskStringReplacements(
            SecureStringsProperties properties) {
        Map<String, MaskStringReplacement> maskStringReplacements = new HashMap<>();
        if (nonNull(properties)) {
            properties.getValue().forEach(maskData ->
                    maskStringReplacements.putIfAbsent(maskData.getFieldName(), maskData));
        }
        return maskStringReplacements;
    }
}
