package test.app.product;

import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;

@Component
public class ProductWriter {
    public static final String fileName = "products.csv";
    @Value("${output.dir}")
    private String outFolderPath;

    public void writeProductsToFile(Iterable<Product> products) {
        BeanWriterProcessor<Product> processor = new BeanWriterProcessor<>(Product.class);
        CsvWriterSettings settings = new CsvWriterSettings();
        settings.setRowWriterProcessor(processor);
        settings.setHeaders("id", "name", "description", "price", "insertDate", "lastUpdateTime");
        File outFile = Paths.get(outFolderPath, fileName).toFile();
        CsvWriter csvWriter = new CsvWriter(outFile, settings);
        csvWriter.writeHeaders();
        products.forEach(csvWriter::processRecord);
        csvWriter.close();
    }
}
