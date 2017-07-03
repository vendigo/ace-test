package com.github.vendigo.acetest.files;

import com.github.vendigo.acetest.config.Config;
import com.github.vendigo.acetest.properties.PropertySetter;
import lombok.SneakyThrows;
import org.junit.rules.TemporaryFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Component
public class FileManager {
    private static final String TEST_DIR_SUFFIX_1 = ".dir";
    private static final String TEST_DIR_SUFFIX_2 = ".folder";
    private final Config config;
    private final PropertySetter propertySetter;
    private TemporaryFolder tempFolder;
    private Map<String, File> folders = new HashMap<>();

    @Autowired
    public FileManager(Config config, PropertySetter propertySetter) {
        this.config = config;
        this.propertySetter = propertySetter;
    }

    @SneakyThrows
    public void createFolders() {
        if (config.getFolders() != null) {
            tempFolder = new TemporaryFolder();
            tempFolder.create();
            for (String folderName : config.getFolders()) {
                File folder = tempFolder.newFolder(folderName);
                propertySetter.addPlaceholder(folderName + TEST_DIR_SUFFIX_1, folder.getAbsolutePath());
                propertySetter.addPlaceholder(folderName + TEST_DIR_SUFFIX_2, folder.getAbsolutePath());
                folders.put(folderName, folder);
            }
        }
    }

    public void deleteTempFolder() {
        if (tempFolder != null) {
            tempFolder.delete();
        }
    }

    @SneakyThrows
    public void createTestFile(String folderName, String fileName, List<String> lines) {
        Path newFilePath = folders.get(folderName).toPath().resolve(fileName);
        Files.write(newFilePath, lines);
    }

    @SneakyThrows
    public List<String> readFile(String folderName, String fileName) {
        Path filePath = folders.get(folderName).toPath().resolve(fileName);
        return Files.lines(filePath).collect(toList());
    }

    public List<String> fileList(String folderName) {
        File[] files = folders.get(folderName).listFiles();
        if (files != null) {
            return Stream.of(files).map(File::getName).collect(toList());
        } else {
            return Collections.emptyList();
        }
    }
}
