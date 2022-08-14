package com.finaro.utility;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.util.Objects.nonNull;

public interface UtilityTestService {
    ObjectMapper OBJECT_MAPPER = getObjectMapper();
    ResourceLoader RESOURCE_LOADER = new DefaultResourceLoader(
            UtilityTestService.class.getClassLoader());
    String JSON_DIRECTORY = "json";
    String RESPONSE_DIRECTORY = "response";

    static byte[] readResourceFile(String pathOnClassPath) throws Exception {
        /*var fileUrl = Thread.currentThread().getContextClassLoader()
                .getResource(JSON_DIRECTORY + File.separator + pathOnClassPath);*/
        var fileUrl = RESOURCE_LOADER.getResource(JSON_DIRECTORY + File.separator + pathOnClassPath).getURL();
        return nonNull(fileUrl)
                ? Files.readAllBytes(Paths.get(fileUrl.toURI()))
                : ArrayUtils.EMPTY_BYTE_ARRAY;
    }

    static String getResourceResponseJson(String pathOnClassPath) throws Exception {
        return new String(
                UtilityTestService.readResourceFile(RESPONSE_DIRECTORY + File.separator + pathOnClassPath));
    }

    static String toJson(Object file) {
        try {
            return OBJECT_MAPPER.writeValueAsString(file);
        } catch (JsonProcessingException e) {
            return StringUtils.EMPTY;
        }
    }

    private static ObjectMapper getObjectMapper() {
        var mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.findAndRegisterModules();
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new ParameterNamesModule());
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new ConstraintViolationProblemModule());

        return mapper;
    }
}
