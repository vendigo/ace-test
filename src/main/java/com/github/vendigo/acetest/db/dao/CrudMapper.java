package com.github.vendigo.acetest.db.dao;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CrudMapper {
    @DeleteProvider(type = CrudSqlProvider.class, method = "deleteAll")
    void deleteAll(String tableName);

    @SelectProvider(type = CrudSqlProvider.class, method = "select")
    List<Map<String, Object>> select(String tableName, Collection<String> columnNames);

    @InsertProvider(type = CrudSqlProvider.class, method = "insertOne")
    void insertOne(String tableName, Map<String, String> row);

    @SelectProvider(type = CrudSqlProvider.class, method = "count")
    int count(String tableName);
}
