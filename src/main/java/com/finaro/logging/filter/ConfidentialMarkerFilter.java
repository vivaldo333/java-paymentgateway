package com.finaro.logging.filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.AbstractMatcherFilter;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * @deprecated (2022.08.14, an example of logging confidential info, for removal)
 */
@Deprecated(forRemoval = true)
public class ConfidentialMarkerFilter extends AbstractMatcherFilter<ILoggingEvent> {

    Marker markerToMatch;

    @Override
    public void start() {
        if (markerToMatch != null) {
            super.start();
        } else {
            addError(String.format("The marker property must be set for [%s]", getName()));
        }
    }

    @Override
    public FilterReply decide(ILoggingEvent iLoggingEvent) {
        Marker marker = iLoggingEvent.getMarker();
        if (!isStarted()) {
            return FilterReply.NEUTRAL;
        }

        if (marker == null) {
            return onMismatch;
        }

        if (markerToMatch.contains(marker)) {
            return onMatch;
        }
        return onMismatch;
    }

    public void setMarker(String markerStr) {
        if (markerStr != null) {
            markerToMatch = MarkerFactory.getMarker(markerStr);
        }
    }
}
