package com.strongduanmu.config;

import com.strongduanmu.constant.ShardingConstants;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
import org.apache.shardingsphere.readwritesplitting.api.ReadwriteSplittingRuleConfiguration;
import org.apache.shardingsphere.readwritesplitting.api.rule.ReadwriteSplittingDataSourceRuleConfiguration;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Qiyi auth datasource config.
 */
@Configuration
@MapperScan(basePackages = {"com.strongduanmu.mapper.qiyiauth"}, sqlSessionFactoryRef = "qiyiAuthSqlSessionFactory")
public class QiyiAuthDataSourceConfig {

    @Bean(name = "mQiyiAuthDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.mqiyiauth")
    public DataSource mQiyiAuthDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean(name = "sQiyiAuthDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.sqiyiauth")
    public DataSource sQiyiAuthDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "qiyiAuthSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("qiyiAuthDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "qiyiAuthTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("qiyiAuthDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "qiyiAuthSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("qiyiAuthSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    
    private Map<String, ShardingSphereAlgorithmConfiguration> getLoadBalanceMap() {
        Map<String, ShardingSphereAlgorithmConfiguration> loadBalanceMaps = new HashMap<>();
        loadBalanceMaps.put(ShardingConstants.SHARDING_MS_LB_NAME, new ShardingSphereAlgorithmConfiguration("ROUND_ROBIN", new Properties()));
        return loadBalanceMaps;
    }
    
    @Bean(name = "qiyiAuthDataSource")
    public DataSource qiyiAuthDataSource(@Qualifier("mQiyiAuthDataSource") DataSource mQiyiAuthDataSource, @Qualifier("sQiyiAuthDataSource") DataSource sQiyiAuthDataSource) throws PropertyVetoException, SQLException {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("mQiyiAuth", mQiyiAuthDataSource);
        dataSourceMap.put("sQiyiAuth", sQiyiAuthDataSource);
        ReadwriteSplittingDataSourceRuleConfiguration masterSlaveRule = new ReadwriteSplittingDataSourceRuleConfiguration("qiyiAuth", null, "mQiyiAuth", Collections.singletonList("sQiyiAuth"), ShardingConstants.SHARDING_MS_LB_NAME);
        ReadwriteSplittingRuleConfiguration masterSlaveConfig = new ReadwriteSplittingRuleConfiguration(Collections.singletonList(masterSlaveRule), getLoadBalanceMap());
        Properties properties = new Properties();
        properties.setProperty("sql-show", "true");
        return ShardingSphereDataSourceFactory.createDataSource(dataSourceMap, Collections.singletonList(masterSlaveConfig), properties);
    }
}