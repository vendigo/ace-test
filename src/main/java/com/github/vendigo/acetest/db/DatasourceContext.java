package com.github.vendigo.acetest.db;

import com.github.vendigo.acetest.config.Config;
import com.github.vendigo.acetest.config.DatasourceConfig;
import com.github.vendigo.acetest.db.dao.CrudMapper;
import com.google.common.collect.Iterables;
import liquibase.integration.spring.SpringLiquibase;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class DatasourceContext {
    public static final String H2_DRIVER_CLASSNAME = "org.h2.Driver";
    public static final String H2_DEFAULT_SCHEMA = "PUBLIC";
    private Map<String, SqlSessionFactory> sessionFactories = new HashMap<>();
    private SqlFileRunner sqlFileRunner = new SqlFileRunner();

    @Autowired
    Config config;

    @PostConstruct
    public void init() {
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
        dataSource.setDriverClassName(H2_DRIVER_CLASSNAME);
        dataSource.setUrl(dsConfig.getUrl());
        return dataSource;
    }

    public SqlSessionFactory getSqlSessionFactory(String dbName) {
        return sessionFactories.get(dbName);
    }

    public String getOnlyDbName() {
        return Iterables.getOnlyElement(sessionFactories.keySet());
    }
}
