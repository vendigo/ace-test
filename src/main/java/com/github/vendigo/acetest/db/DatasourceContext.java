package com.github.vendigo.acetest.db;

import com.github.vendigo.acetest.config.Config;
import com.github.vendigo.acetest.config.DatasourceConfig;
import com.github.vendigo.acetest.db.dao.CrudMapper;
import com.github.vendigo.acetest.db.init.SqlFileRunner;
import com.google.common.collect.Iterables;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DatasourceContext {
    public static final String H2_DRIVER_CLASSNAME = "org.h2.Driver";
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

            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(datasource);
            Configuration configuration = new Configuration();
            configuration.addMapper(CrudMapper.class);
            sqlSessionFactoryBean.setConfiguration(configuration);
            sessionFactories.put(dsConfig.getDbName(), sqlSessionFactoryBean.getObject());
        }
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
