package com.finaro.config.masking;

import com.finaro.dto.masking.MaskStringReplacement;
import com.finaro.dto.masking.SecureStringReplacement;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.secure-strings-replacements")
public class SecureStringsProperties {
    private List<SecureStringReplacement> log;
    private List<MaskStringReplacement> value;
}
