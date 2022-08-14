package com.finaro.logging.converter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.ReplacingCompositeConverter;
import com.finaro.logging.dto.LogConfidentialData;
import org.slf4j.Marker;
import org.slf4j.helpers.MessageFormatter;

import static com.finaro.constant.Constants.LogMarkers.CONFIDENTIAL;
import static com.finaro.constant.Constants.LogMarkers.MASKED_DATA;

/**
 * @deprecated (2022.08.14, an example of logging confidential info, for removal)
 */
@Deprecated(forRemoval = true)
public class MaskingConverter extends ReplacingCompositeConverter<ILoggingEvent> {

    @Override
    public String convert(ILoggingEvent event) {
        Marker eventMarker = event.getMarker();

        Object[] args = event.getArgumentArray();
        if (eventMarker != null && eventMarker.contains(CONFIDENTIAL)) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].getClass().isAssignableFrom(LogConfidentialData.class)) {
                    args[i] = MASKED_DATA;
                }
            }
        }

        return MessageFormatter.arrayFormat(event.getMessage(), args).getMessage();
    }

    @Override
    public void start() {
        started = true;
    }
}
