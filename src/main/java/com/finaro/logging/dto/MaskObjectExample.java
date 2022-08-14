package com.finaro.logging.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @deprecated (2022.08.14, an example of logging confidential info, for removal)
 */
@Deprecated(forRemoval = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaskObjectExample {
    String userName;
    LogConfidentialData<String> password;
}
