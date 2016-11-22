package test.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import test.app.country.CountryRepository;
import test.app.product.ProductRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class BusinessLogicComponent {
    @Autowired
    private UserService userService;
    @Value("${age.limit}")
    private int ageLimit;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private ProductRepository productRepository;

    public void run() {
        List<User> oldUsers = userService.getUsers().stream()
                .filter(user -> user.getAge() > ageLimit)
                .collect(toList());
        userService.insertOldUsers(oldUsers);

        System.out.println("Countries: " + countryRepository.findAll());
        System.out.println();
        System.out.println("Products: " +productRepository.findAll());
    }
}
