package test.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BusinessComponent {
    @Value("${user.name}")
    private String userName;

    public String getUserName() {
        return userName;
    }
}
