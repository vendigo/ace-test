package test.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackageClasses = Launcher.class)
@PropertySource("classpath:test.properties")
public class Launcher {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(Launcher.class);
        BusinessLogicComponent businessLogicComponent = appContext.getBean(BusinessLogicComponent.class);
        businessLogicComponent.run();
    }
}
