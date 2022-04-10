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
 * Intl biz data source config.
 */
@Configuration
@MapperScan(basePackages = {"com.strongduanmu.mapper.intlbiz"}, sqlSessionFactoryRef = "intlBizSqlSessionFactory")
public class IntlBizDataSourceConfig {
    
    @Bean(name = "mIntlBizDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.mintlbiz")
    public DataSource mIntlBizDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean(name = "sIntlBizDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.sintlbiz")
    public DataSource sIntlBizDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean(name = "intlBizSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("intlBizDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return bean.getObject();
    }
    
    @Bean(name = "intlBizTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("intlBizDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    
    @Bean(name = "intlBizSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("intlBizSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    
    private Map<String, ShardingSphereAlgorithmConfiguration> getLoadBalanceMap() {
        Map<String, ShardingSphereAlgorithmConfiguration> loadBalanceMaps = new HashMap<>();
        loadBalanceMaps.put(ShardingConstants.SHARDING_MS_LB_NAME, new ShardingSphereAlgorithmConfiguration("ROUND_ROBIN", new Properties()));
        return loadBalanceMaps;
    }
    
    @Bean(name = "intlBizDataSource")
    public DataSource intlBizDataSource(@Qualifier("mIntlBizDataSource") DataSource mIntlBizDataSource, @Qualifier("sIntlBizDataSource") DataSource sIntlBizDataSource) throws PropertyVetoException, SQLException {
        Map<String, DataSource> dataSource = new HashMap<>();
        dataSource.put("mIntlBiz", mIntlBizDataSource);
        dataSource.put("sIntlBiz", sIntlBizDataSource);
        
        ReadwriteSplittingDataSourceRuleConfiguration masterSlaveRule = new ReadwriteSplittingDataSourceRuleConfiguration("intlBiz", null, "mIntlBiz", Collections.singletonList("sIntlBiz"), ShardingConstants.SHARDING_MS_LB_NAME);
        ReadwriteSplittingRuleConfiguration masterSlaveConfig = new ReadwriteSplittingRuleConfiguration(Collections.singletonList(masterSlaveRule), getLoadBalanceMap());
        Properties properties = new Properties();
        properties.setProperty("sql-show", "true");
        return ShardingSphereDataSourceFactory.createDataSource(dataSource, Collections.singletonList(masterSlaveConfig), properties);
    }
}