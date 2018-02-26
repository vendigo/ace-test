package context;

import com.github.vendigo.acetest.config.Config;
import com.github.vendigo.acetest.config.ConfigParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class TestContext {
    @Bean
    public Config config() {
        return ConfigParser.readConfig(Collections.singletonList("test-contexts-settings.yml"));
    }
}
