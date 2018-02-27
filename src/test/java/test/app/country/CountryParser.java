package test.app.country;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import test.app.csv.CsvProcessor;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
@Slf4j
public class CountryParser {
    public static final String COUNTRY_FILE_NAME = "countries.csv";
    @Value("${input.dir}")
    private String inFolderPath;

    @SneakyThrows
    public List<Country> parseCountriesCsv() {
        log.info("Searching for file in folder: {}", inFolderPath);
        Files.lines(Paths.get(inFolderPath, COUNTRY_FILE_NAME)).forEach(line -> log.info("Line: {}", line));

        return CsvProcessor.set()
                .folder(inFolderPath)
                .fileName(COUNTRY_FILE_NAME)
                .entityClass(Country.class)
                .extractHeaders(true)
                .detectDelimiters(true)
                .detectLineSeparators(true)
                .and().parse();
    }
}
