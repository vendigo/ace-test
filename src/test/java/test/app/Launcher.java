package test.app;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackageClasses = Launcher.class)
@PropertySource("test.properties")
public class Launcher {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(Launcher.class);
        BusinessComponent businessComponent = appContext.getBean(BusinessComponent.class);

        if (args.length > 0) {
            System.out.println("Argument: "+args[0]);
        }
        System.out.println("Username: "+businessComponent.getUserName());
    }

    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertyPlaceholderConfigurer();
    }
}
