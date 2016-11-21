package test.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class BusinessLogicComponent {
    @Autowired
    private UserService userService;
    @Value("${age.limit}")
    private int ageLimit;

    public void run() {
        List<User> oldUsers = userService.getUsers().stream()
                .filter(user -> user.getAge() > ageLimit)
                .collect(toList());
        userService.insertOldUsers(oldUsers);
    }
}
