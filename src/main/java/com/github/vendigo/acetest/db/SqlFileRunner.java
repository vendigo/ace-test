package com.github.vendigo.acetest.db;

import org.apache.commons.io.IOUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.InputStream;
import java.nio.charset.Charset;

class SqlFileRunner {
    void applySchemaFile(String fileName, DataSource dataSource) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        try (InputStream fileStream = classLoader.getResourceAsStream(fileName)) {
            String fileContent = IOUtils.toString(fileStream, Charset.defaultCharset());
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.execute(fileContent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
