package com.github.vendigo.acetest.db;

import com.github.vendigo.acetest.config.Config;
import com.github.vendigo.acetest.config.DatasourceConfig;
import com.github.vendigo.acetest.db.dao.CrudMapper;
import com.github.vendigo.acetest.db.init.SqlFileRunner;
import com.google.common.collect.Iterables;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
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
public class DatasourceContext {
    public static final String H2_DRIVER_CLASSNAME = "org.h2.Driver";
    public static final String H2_DEFAULT_SCHEMA = "PUBLIC";
    private Map<String, DataSource> datasources = new HashMap<>();
    private Map<String, SqlSessionFactory> sessionFactories = new HashMap<>();
    private SqlFileRunner sqlFileRunner = new SqlFileRunner();

    @Autowired
    Config config;

    @PostConstruct
    public void init() throws Exception {
        for (DatasourceConfig dsConfig : config.getDatasources()) {
            DataSource datasource = createDatasource(dsConfig);
            if (dsConfig.getSchemaFile() != null) {
                sqlFileRunner.applySchemaFile(dsConfig.getSchemaFile(), datasource);
            }

            if (dsConfig.getLiquibaseConfig() != null) {
                applyLiquibase(dsConfig, datasource);
            }

            createSqlSessionFactory(dsConfig, datasource);
        }
    }

    private void applyLiquibase(DatasourceConfig dsConfig, DataSource datasource) throws LiquibaseException {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setDataSource(datasource);
        springLiquibase.setChangeLog(dsConfig.getLiquibaseConfig());
        springLiquibase.setResourceLoader(new DefaultResourceLoader());
        springLiquibase.setDefaultSchema(H2_DEFAULT_SCHEMA);
        springLiquibase.afterPropertiesSet();
    }

    private void createSqlSessionFactory(DatasourceConfig dsConfig, DataSource datasource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(datasource);
        Configuration configuration = new Configuration();
        configuration.addMapper(CrudMapper.class);
        sqlSessionFactoryBean.setConfiguration(configuration);
        sessionFactories.put(dsConfig.getDbName(), sqlSessionFactoryBean.getObject());
    }

    private DataSource createDatasource(DatasourceConfig dsConfig) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(H2_DRIVER_CLASSNAME);
        dataSource.setUrl(dsConfig.getUrl());
        datasources.put(dsConfig.getDbName(), dataSource);
        return dataSource;
    }

    public SqlSessionFactory getSqlSessionFactory(String dbName) {
        return sessionFactories.get(dbName);
    }

    public String getOnlyDbName() {
        return Iterables.getOnlyElement(sessionFactories.keySet());
    }
}
