package com.finaro.dto.masking;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@Data
public class MaskStringReplacement {
    @NotBlank
    private String fieldName;
    @NotBlank
    private String regex;
    @NotBlank
    private String replacement;
    private Integer replaceRepeatTimes;
}
