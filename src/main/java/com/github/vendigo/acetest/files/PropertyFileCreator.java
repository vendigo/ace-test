package com.github.vendigo.acetest.files;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.List;

@Component
public class PropertyFileCreator {
    private static final String PROPERTIES_FOLDER = "target/properties";
    @Autowired
    FileInputData fileInputData;

    public void createPropertyFile(String fileName) throws Exception {
        List<String> lines = fileInputData.getLines(fileName);

        if (lines != null) {
            createFolder(PROPERTIES_FOLDER);
            writeFile(PROPERTIES_FOLDER, fileName, lines);
            loadFolderToClassPath(PROPERTIES_FOLDER);
        } else {
            throw new IllegalArgumentException("Unknown fileName");
        }
    }

    private void loadFolderToClassPath(String folder) throws Exception {
        File folderFile = new File(folder);
        ClassLoader classLoader = this.getClass().getClassLoader();
        Method addURLMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        addURLMethod.setAccessible(true);
        addURLMethod.invoke(classLoader, folderFile.toURI().toURL());
    }

    private void writeFile(String folder, String fileName, List<String> lines) throws Exception {
        File file = Paths.get(folder, fileName).toFile();
        FileUtils.writeLines(file, lines);
    }

    private void createFolder(String folder) {
        File folderFile = new File(folder);
        if (!folderFile.exists()) {
            folderFile.delete();
        }
        folderFile.mkdir();
    }
}
