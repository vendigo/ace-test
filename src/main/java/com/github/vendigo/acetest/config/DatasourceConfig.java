package com.github.vendigo.acetest.config;

import com.google.common.base.MoreObjects;

public class DatasourceConfig {
    private String dbName;
    private String url;
    private String liquibaseConfig;
    private String schemaFile;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLiquibaseConfig() {
        return liquibaseConfig;
    }

    public void setLiquibaseConfig(String liquibaseConfig) {
        this.liquibaseConfig = liquibaseConfig;
    }

    public String getSchemaFile() {
        return schemaFile;
    }

    public void setSchemaFile(String schemaFile) {
        this.schemaFile = schemaFile;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("dbName", dbName)
                .add("url", url)
                .add("liquibaseConfig", liquibaseConfig)
                .add("schemaFile", schemaFile)
                .toString();
    }
}
