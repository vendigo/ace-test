package com.github.vendigo.acetest.db;

import com.github.vendigo.acetest.config.Config;
import com.github.vendigo.acetest.config.DatasourceConfig;
import com.github.vendigo.acetest.db.init.SqlFileRunner;
import org.apache.commons.dbcp.BasicDataSource;
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
    private SqlFileRunner sqlFileRunner = new SqlFileRunner();

    @Autowired
    Config config;

    @PostConstruct
    public void init() {

        config.getDatasources().stream().forEach(dsConfig -> {
            DataSource datasource = createDatasource(dsConfig);
            if (dsConfig.getSchemaFile() != null) {
                sqlFileRunner.applySchemaFile(dsConfig.getSchemaFile(), datasource);
            }
        });
    }

    private DataSource createDatasource(DatasourceConfig dsConfig) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(H2_DRIVER_CLASSNAME);
        dataSource.setUrl(dsConfig.getUrl());
        datasources.put(dsConfig.getDbName(), dataSource);
        return dataSource;
    }

    public DataSource getDatasource(String dbName) {
        return datasources.get(dbName);
    }
}
