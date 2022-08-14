package com.finaro.constant;

import lombok.experimental.UtilityClass;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.RequestPath;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class Constants {

    @UtilityClass
    public static class DateTime {
        public static final String DEFAULT_TIME_ZONE = "Europe/Kiev";
        public static final String DEFAULT_DATE_FORMAT = "dd.MM.yyyy";
        public static final String DEFAULT_DATE_TIME_FORMAT = "dd.MM.yyyy'T'HH:mm:ss.SSS'Z'";
    }

    @UtilityClass
    public static class Http {
        public static final String TRACE_HEADER_ID = "X-Trace-Id";
        public static final Set<String> NON_TRACE_PATHS =
                Stream.of("/actuator", "/readinessState", "/livenessState", "/metrics")
                        .map(x -> RequestPath.parse(x, null))
                        .map(PathContainer::value)
                        .collect(Collectors.toSet());
    }


    @UtilityClass
    public class LogMarkers {
        public static final Marker CONFIDENTIAL = MarkerFactory.getDetachedMarker("CONFIDENTIAL");
        public static final String MASKED_DATA = "********";
    }
}
