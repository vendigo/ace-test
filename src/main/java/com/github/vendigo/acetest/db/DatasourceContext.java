package com.github.vendigo.acetest.db;

import com.github.vendigo.acetest.config.Config;
import com.github.vendigo.acetest.config.DatasourceConfig;
import com.github.vendigo.acetest.db.dao.CrudMapper;
import com.github.vendigo.acetest.utils.Utils;
import liquibase.integration.spring.SpringLiquibase;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class DatasourceContext {
    public static final String H2_DRIVER_CLASS_NAME = "org.h2.Driver";
    private static final String H2_DEFAULT_SCHEMA = "PUBLIC";
    private Map<String, SqlSessionFactory> sessionFactories = new HashMap<>();
    private SqlFileRunner sqlFileRunner = new SqlFileRunner();
    private final Config config;

    @Autowired
    public DatasourceContext(Config config) {
        this.config = config;
    }

    @PostConstruct
    public void init() {
        log.info("Init datasource");
        for (DatasourceConfig dsConfig : config.getDatasources()) {
            DataSource datasource = createDatasource(dsConfig);
            if (dsConfig.getSchemaFile() != null) {
                log.info("Applying {} to {}", dsConfig.getSchemaFile(), dsConfig.getDbName());
                sqlFileRunner.applySchemaFile(dsConfig.getSchemaFile(), datasource);
            }

            if (dsConfig.getLiquibaseConfig() != null) {
                applyLiquibase(dsConfig, datasource);
            }

            createSqlSessionFactory(dsConfig, datasource);
        }
    }

    @SneakyThrows
    private void applyLiquibase(DatasourceConfig dsConfig, DataSource datasource) {
        log.info("Applying {} to {}", dsConfig.getLiquibaseConfig(), dsConfig.getDbName());
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setDataSource(datasource);
        springLiquibase.setChangeLog(dsConfig.getLiquibaseConfig());
        String liquibaseContexts = dsConfig.getLiquibaseContexts();
        if (!StringUtils.isEmpty(liquibaseContexts)) {
            springLiquibase.setContexts(liquibaseContexts);
        }
        springLiquibase.setResourceLoader(new DefaultResourceLoader());
        springLiquibase.setDefaultSchema(H2_DEFAULT_SCHEMA);
        springLiquibase.afterPropertiesSet();
    }

    @SneakyThrows
    private void createSqlSessionFactory(DatasourceConfig dsConfig, DataSource datasource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(datasource);
        Configuration configuration = new Configuration();
        configuration.addMapper(CrudMapper.class);
        sqlSessionFactoryBean.setConfiguration(configuration);
        sessionFactories.put(dsConfig.getDbName(), sqlSessionFactoryBean.getObject());
    }

    private DataSource createDatasource(DatasourceConfig dsConfig) {
        log.info("Creating dataSource {}", dsConfig.getDbName());
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(H2_DRIVER_CLASS_NAME);
        dataSource.setUrl(dsConfig.getUrl());
        return dataSource;
    }

    public SqlSessionFactory getSqlSessionFactory(String dbName) {
        return sessionFactories.get(dbName);
    }

    public String getOnlyDbName() {
        return Utils.getOnlyElement(sessionFactories.keySet());
    }
}
