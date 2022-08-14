package com.finaro.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.finaro.config.audit.AuditProperties;
import com.finaro.dto.transaction.TransactionDto;
import com.finaro.service.AuditService;
import com.finaro.service.FileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    AuditProperties auditProperties;
    ObjectMapper objectMapper;
    FileService fileService;

    @SneakyThrows
    @Async
    @Override
    public void log(TransactionDto maskedTransaction) {
        var jsonText = toJson(maskedTransaction);
        if (StringUtils.isNotEmpty(jsonText)) {
            var file = fileService.createFileIfNotExists(auditProperties.getFileResource());
            Files.writeString(file,
                    jsonText.concat(System.lineSeparator()), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }
    }

    private <O> String toJson(O object) {
        try {
            return objectMapper.writer()
                    .without(SerializationFeature.INDENT_OUTPUT)
                    .writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error during converting to JSON", e);
            return StringUtils.EMPTY;
        }
    }


}
