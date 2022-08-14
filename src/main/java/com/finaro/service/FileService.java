package com.finaro.service;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Path;

public interface FileService {
    Path getPath(String directoryPath, String fileName);

    Path createFileIfNotExists(String directoryPath, String fileName) throws IOException;

    Path createFileIfNotExists(Resource fileResource) throws IOException;
}
