package com.finaro.logging.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.finaro.constant.Constants.LogMarkers.MASKED_DATA;

/**
 * @deprecated (2022.08.14, an example of logging confidential info, for removal)
 */
@Deprecated(forRemoval = true)
@Getter
@AllArgsConstructor
public class LogConfidentialData<T> {
    private T data;

    @Override
    public String toString() {
        return MASKED_DATA;
    }
}
