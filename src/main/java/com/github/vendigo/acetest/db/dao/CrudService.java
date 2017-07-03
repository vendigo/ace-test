package com.github.vendigo.acetest.db.dao;

import com.github.vendigo.acetest.db.DatasourceContext;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class CrudService {
    private final DatasourceContext datasourceContext;

    @Autowired
    public CrudService(DatasourceContext datasourceContext) {
        this.datasourceContext = datasourceContext;
    }

    public void deleteAll(String tableName) {
        deleteAll(datasourceContext.getOnlyDbName(), tableName);
    }

    public void deleteAll(String dbName, String tableName) {
        doVoidQuery(dbName, crudMapper -> crudMapper.deleteAll(tableName));
    }

    public void insert(String tableName, List<Map<String, String>> rows) {
        insert(datasourceContext.getOnlyDbName(), tableName, rows);
    }

    public void insert(String dbName, String tableName, List<Map<String, String>> rows) {
        doVoidQuery(dbName, crudMapper -> rows.forEach(row -> crudMapper.insertOne(tableName, row)));
    }

    public List<Map<String, Object>> selectAll(String tableName, Collection<String> columns) {
        return doQuery(datasourceContext.getOnlyDbName(), crudMapper -> crudMapper.select(tableName, columns));
    }

    public List<Map<String, Object>> selectAll(String dbName, String tableName, Collection<String> columns) {
        return doQuery(dbName, crudMapper -> crudMapper.select(tableName, columns));
    }

    public int count(String tableName) {
        return doQuery(datasourceContext.getOnlyDbName(), crudMapper -> crudMapper.count(tableName));
    }

    public int count(String dbName, String tableName) {
        return doQuery(dbName, crudMapper -> crudMapper.count(tableName));
    }

    private <T> T doQuery(String dbName, Function<CrudMapper, T> action) {
        try (SqlSession sqlSession = datasourceContext.getSqlSessionFactory(dbName).openSession()) {
            CrudMapper crudMapper = sqlSession.getMapper(CrudMapper.class);
            return action.apply(crudMapper);
        }
    }

    private void doVoidQuery(String dbName, Consumer<CrudMapper> action) {
        try (SqlSession sqlSession = datasourceContext.getSqlSessionFactory(dbName).openSession()) {
            CrudMapper crudMapper = sqlSession.getMapper(CrudMapper.class);
            action.accept(crudMapper);
        }
    }
}
