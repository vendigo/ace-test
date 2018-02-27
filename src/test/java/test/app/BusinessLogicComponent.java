package test.app;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        List<Country> parsedCountries = countryParser.parseCountriesCsv();
        log.info("Parsed countries: {}", parsedCountries);
        List<Country> bigCountries = parsedCountries.stream()
                .filter(country -> disableAreaLimit || country.getArea() > areaLimit)
                .collect(Collectors.toList());

        log.info("DisableAreaLimit: {}, Big countries: {}", disableAreaLimit, bigCountries);
        countryRepository.save(bigCountries);

        Iterable<Product> products = productRepository.findAll();
        productWriter.writeProductsToFile(products);
    }

}
