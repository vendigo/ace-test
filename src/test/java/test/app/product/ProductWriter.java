package test.app.product;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import test.app.csv.CsvProcessor;

import static java.util.Arrays.asList;

@Component
public class ProductWriter {
    public static final String fileName = "products.csv";
    @Value("${output.dir}")
    private String outFolderPath;

    public void writeProductsToFile(Iterable<Product> products) {
        CsvProcessor.set()
                .entityClass(Product.class)
                .folder(outFolderPath)
                .fileName(fileName)
                .headers(asList("id", "name", "description", "price", "insertDate", "lastUpdateTime"))
                .and().write(products);
    }
}
