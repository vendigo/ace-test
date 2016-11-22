package test.app.country;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

@Component
public class CountryParser {
    public static final String COUNTRY_FILE_NAME = "countries.csv";
    @Value("${input.dir}")
    private String inFolderPath;

    public List<Country> parseCountriesCsv() {
        File file = Paths.get(inFolderPath, COUNTRY_FILE_NAME).toFile();
        BeanListProcessor<Country> rowProcessor = new BeanListProcessor<>(Country.class);
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.setDelimiterDetectionEnabled(true);
        settings.setLineSeparatorDetectionEnabled(true);
        settings.setProcessor(rowProcessor);
        CsvParser parser = new CsvParser(settings);
        parser.parse(file);
        return rowProcessor.getBeans();
    }
}
