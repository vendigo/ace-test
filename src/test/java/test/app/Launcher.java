package test.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;

@Configuration
@ComponentScan(basePackageClasses = Launcher.class)
@PropertySource("classpath:test.properties")
public class Launcher {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(Launcher.class);
        BusinessLogicComponent businessLogicComponent = appContext.getBean(BusinessLogicComponent.class);
        boolean disableAreaLimit = parseArgs(args);
        businessLogicComponent.run(disableAreaLimit);
    }

    private static boolean parseArgs(String[] args) {
        return args != null && Arrays.asList(args).contains("-disableAreaLimit");
    }
}
