package test.app;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableJpaRepositories(basePackageClasses = Launcher.class)
public class AppConfig {

    @Bean
    public DataSource dataSource(@Value("${db.driverClassName}")String driverClassName,
                                 @Value("${db.url}")String dbUrl) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(dbUrl);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        entityManagerFactory.setJpaProperties(properties);
        entityManagerFactory.setPackagesToScan("test.app");
        return entityManagerFactory;
    }

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Bean
    public JpaTransactionManager transactionManager(FactoryBean<EntityManagerFactory> entityManagerFactory) throws Exception {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return transactionManager;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertyPlaceholderConfigurer();
    }
}
