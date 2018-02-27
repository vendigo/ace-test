package test.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;
import java.util.List;

@Configuration
@ComponentScan(basePackageClasses = Launcher.class)
@PropertySource("classpath:test.properties")
@Slf4j
public class Launcher {
    public static void main(String[] args) {
        boolean disableAreaLimit = parseArgs(args);
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(Launcher.class);
        BusinessLogicComponent businessLogicComponent = appContext.getBean(BusinessLogicComponent.class);
        businessLogicComponent.run(disableAreaLimit);
    }

    private static boolean parseArgs(String[] args) {
        if (args != null) {
            log.info("Running Launcher with params: {}", Arrays.toString(args));

            List<String> argsList = Arrays.asList(args);
            if (argsList.size() == 1) {
                return argsList.contains("-disableAreaLimit");
            } else {
                throw new IllegalArgumentException("Too many parameters");
            }
        }
        return false;
    }
}
