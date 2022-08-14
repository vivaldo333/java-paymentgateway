package com.finaro.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS;
import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SNAKE_CASE;
import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS;
import static com.finaro.constant.Constants.DateTime.DEFAULT_DATE_FORMAT;
import static com.finaro.constant.Constants.DateTime.DEFAULT_DATE_TIME_FORMAT;
import static com.finaro.constant.Constants.DateTime.DEFAULT_TIME_ZONE;

@Configuration
public class ObjectMapperModuleConfig {

    @Bean
    public JavaTimeModule javaTimeModule(
            @Qualifier("dateFormatter")
                    DateTimeFormatter dateFormatter,
            @Qualifier("dateTimeFormatter")
                    DateTimeFormatter dateTimeFormatter) {
        var javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(
                LocalDate.class,
                new LocalDateDeserializer(dateFormatter));
        javaTimeModule.addSerializer(
                LocalDate.class, new LocalDateSerializer(dateFormatter));
        javaTimeModule.addDeserializer(
                LocalDateTime.class,
                new LocalDateTimeDeserializer(dateTimeFormatter));
        javaTimeModule.addSerializer(
                LocalDateTime.class,
                new LocalDateTimeSerializer(dateTimeFormatter));

        return javaTimeModule;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(
            ObjectMapper objectMapper) {
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }

    @Bean
    public ObjectMapper objectMapper(
            JavaTimeModule javaTimeModule,
            DateFormat dateFormat
    ) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(SNAKE_CASE);
        mapper.setSerializationInclusion(NON_NULL);
        mapper.configure(INDENT_OUTPUT, true);
        mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.configure(FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.disable(WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
        mapper.disable(READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
        mapper.setDateFormat(dateFormat);
        mapper.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIME_ZONE));
        mapper.registerModule(javaTimeModule);
        mapper.registerModule(new ParameterNamesModule());
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new ProblemModule());
        mapper.registerModule(new ConstraintViolationProblemModule());

        return mapper;
    }

    @Bean
    public DateFormat getDateFormat() {
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT);
    }

    @Bean(name = "dateFormatter")
    public DateTimeFormatter getDateFormtatter() {
        return DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
    }

    @Bean(name = "dateTimeFormatter")
    public DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT);
    }
}
