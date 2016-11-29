package test.app.csv;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import lombok.Builder;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

@Builder(builderMethodName = "set", buildMethodName = "and")
public class CsvProcessor {
    private Class<?> entityClass;
    private String folder;
    private String fileName;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private List<String> headers;
    private boolean extractHeaders;
    private boolean detectDelimiters;
    private boolean detectLineSeparators;

    @SuppressWarnings("unchecked")
    public <T> List<T> parse() {
        File file = Paths.get(folder, fileName).toFile();

        CsvParserSettings settings = new CsvParserSettings();

        settings.setHeaderExtractionEnabled(extractHeaders);
        settings.setDelimiterDetectionEnabled(detectDelimiters);
        settings.setLineSeparatorDetectionEnabled(detectLineSeparators);

        BeanListProcessor<?> rowProcessor = new BeanListProcessor<>(entityClass);
        settings.setProcessor(rowProcessor);

        CsvParser parser = new CsvParser(settings);
        parser.parse(file);
        return (List<T>) rowProcessor.getBeans();
    }

    public void write(Iterable<?> records) {
        File outFile = Paths.get(folder, fileName).toFile();

        CsvWriterSettings settings = new CsvWriterSettings();

        BeanWriterProcessor<?> processor = new BeanWriterProcessor<>(entityClass);
        settings.setRowWriterProcessor(processor);

        settings.setHeaders((String[]) headers.toArray());
        CsvWriter csvWriter = new CsvWriter(outFile, settings);
        csvWriter.writeHeaders();
        records.forEach(csvWriter::processRecord);
        csvWriter.close();
    }
}
