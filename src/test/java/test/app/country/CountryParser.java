package test.app.country;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import test.app.csv.CsvProcessor;

import java.util.List;

@Component
public class CountryParser {
    public static final String COUNTRY_FILE_NAME = "countries.csv";
    @Value("${input.dir}")
    private String inFolderPath;

    public List<Country> parseCountriesCsv() {
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
