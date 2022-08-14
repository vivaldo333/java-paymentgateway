package com.finaro.service.impl;

import com.finaro.service.FileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @Override
    public Path getPath(String directoryPath, String fileName) {
        return Paths.get(directoryPath + File.separator + fileName);
    }

    @Override
    public Path createFileIfNotExists(String directoryPath, String fileName) throws IOException {
        var file = getPath(directoryPath, fileName);
        if (isFalse(Files.exists(file))) {
            var directory = Paths.get(directoryPath);
            Files.createDirectories(directory);
            return Files.createFile(file);
        }
        return file;
    }

    @Override
    public Path createFileIfNotExists(Resource fileResource) throws IOException {
        var filePath = ((ClassPathResource) fileResource).getPath();
        if (isFalse(fileResource.exists())) {
            var fileName = fileResource.getFilename();
            var directoryPath = filePath.replace(fileName, StringUtils.EMPTY);
            return createFileIfNotExists(directoryPath, fileName);
        }
        return fileResource.getFile().toPath();

    }
}
