package com.github.vendigo.acetest.config;

import lombok.Data;

@Data
public class DatasourceConfig {
    private String dbName;
    private String url;
    private String liquibaseConfig;
    private String schemaFile;
}
