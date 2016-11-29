package com.github.vendigo.acetest.db.dao;

import org.apache.ibatis.jdbc.SQL;

import java.util.Collection;
import java.util.Map;

import static java.util.stream.Collectors.joining;

public class CrudSqlProvider {
    public String deleteAll(String tableName) {
        return new SQL() {{
            DELETE_FROM(tableName);
        }}.toString();
    }

    public String select(String tableName, Collection<String> columnNames) {
        return new SQL(){{
            SELECT(columnNames.stream().collect(joining(", ")));
            FROM(tableName);
        }}.toString();
    }

    public String insertOne(String tableName, Map<String, String> row) {
        return new SQL() {{
            INSERT_INTO(tableName);
            VALUES(row.keySet().stream().collect(joining(", ")),
                    row.values().stream()
                            .map(s -> quote(s))
                            .collect(joining(", ")));
        }}.toString();
    }

    public String count(String tableName) {
        return new SQL(){{
            SELECT("count(*)");
            FROM(tableName);
        }}.toString();
    }

    private String quote(String str) {
        return "'"+ str +"'";
    }
}
