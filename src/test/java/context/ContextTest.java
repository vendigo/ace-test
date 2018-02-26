package context;

import com.github.vendigo.acetest.config.Config;
import com.github.vendigo.acetest.config.DatasourceConfig;
import com.github.vendigo.acetest.spring.SpringConfig;
import com.github.vendigo.acetest.utils.Utils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.vendigo.acetest.db.DatasourceContext.H2_DRIVER_CLASS_NAME;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class ContextTest {
    private static final String STATEMENT = "SELECT count(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?";
    private static final String COMMON_TABLE = "CommonTable";
    private static final String TEST_ONLY = "TestOnly";
    private static final String PROD_ONLY = "ProdOnly";

    @Autowired
    private Config config;
    private Map<String, List<DatasourceConfig>> configMap;

    @Before
    public void groupConfigs() {
        configMap = config.getDatasources().stream()
                .filter(dsConfig -> dsConfig.getDbName().startsWith("context"))
                .collect(Collectors.groupingBy(DatasourceConfig::getDbName));
    }

    @Test
    public void dbWithNoContextsAllTablesShouldBeCreated() {
        DatasourceConfig allDbContext = Utils.getOnlyElement(configMap.get("contextDb"));
        testSchema(allDbContext, Arrays.asList(COMMON_TABLE, TEST_ONLY, PROD_ONLY), Collections.emptyList());
    }

    @Test
    public void dbWithProdContextNoTestContextTablesShouldBeCreated() {
        DatasourceConfig prodDbContext = Utils.getOnlyElement(configMap.get("contextProdDb"));
        testSchema(prodDbContext, Arrays.asList(COMMON_TABLE, PROD_ONLY), Collections.singletonList(TEST_ONLY));
    }

    @Test
    public void dbWithTestContextNoProdTablesShouldBeCreated() {
        DatasourceConfig testDbContext = Utils.getOnlyElement(configMap.get("contextTestDb"));
        testSchema(testDbContext, Arrays.asList(COMMON_TABLE, TEST_ONLY), Collections.singletonList(PROD_ONLY));
    }

    private void testSchema(DatasourceConfig dsConfig, List<String> presentTables, List<String> absentTables) {
        JdbcOperations jdbc = createTemplate(dsConfig);
        presentTables.forEach(table -> assertTable(jdbc, table, 1));
        absentTables.forEach(table -> assertTable(jdbc, table, 0));
    }

    private void assertTable(JdbcOperations jdbc, String tableName, long expectedCount) {
        long count = jdbc.queryForObject(STATEMENT, Long.class, tableName);
        assertEquals(expectedCount, count);
    }

    private JdbcOperations createTemplate(DatasourceConfig dsConfig) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(H2_DRIVER_CLASS_NAME);
        dataSource.setUrl(dsConfig.getUrl());
        return new JdbcTemplate(dataSource);
    }
}
