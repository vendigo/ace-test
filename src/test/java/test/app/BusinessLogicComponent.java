package test.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import test.app.country.Country;
import test.app.country.CountryParser;
import test.app.country.CountryRepository;
import test.app.product.Product;
import test.app.product.ProductRepository;
import test.app.product.ProductWriter;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BusinessLogicComponent {
    @Value("${area.limit}")
    private int areaLimit;
    @Value("${input.dir}")
    private String inFolderPath;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductWriter productWriter;

    @Autowired
    private CountryParser countryParser;

    public void run(boolean disableAreaLimit) {
        List<Country> bigCountries = countryParser.parseCountriesCsv().stream()
                .filter(country -> disableAreaLimit || country.getArea() > areaLimit)
                .collect(Collectors.toList());
        countryRepository.save(bigCountries);

        Iterable<Product> products = productRepository.findAll();
        productWriter.writeProductsToFile(products);
    }

}
