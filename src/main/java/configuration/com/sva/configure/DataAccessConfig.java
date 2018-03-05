/**   
 * @Title: DataAccessConfig.java 
 * @Package com.sva.configure 
 * @Description: 数据源访问配置
 * @author labelCS   
 * @date 2018年1月5日 下午3:44:07 
 * @version V1.0   
 */
package com.sva.configure;

import java.io.IOException;
import org.apache.commons.dbcp.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/** 
 * @ClassName: DataAccessConfig 
 * @Description: 数据源访问配置
 * @author labelCS 
 * @date 2018年1月5日 下午3:44:07 
 *  
 */
@Configuration
@PropertySource({"classpath:db.properties","classpath:sva.properties"})
@MapperScan(basePackages="com.sva.dao")
@EnableTransactionManagement
public class DataAccessConfig
{
    @Autowired
    private Environment env;
    
    /** 
     * @Title: dataSource 
     * @Description: 数据源连接池
     * @return 
     */
    @Bean
    public BasicDataSource dataSource(){
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(env.getProperty("mysql.driver_class"));
        ds.setUrl(env.getProperty("mysql.jdbcurl"));
        ds.setUsername(env.getProperty("mysql.user"));
        ds.setPassword(env.getProperty("mysql.password"));
        return ds;
    }
    
    /** 
     * @Title: sqlSessionFactoryBean 
     * @Description: mybatis配置
     * @return
     * @throws IOException 
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();  
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();  
        sqlSessionFactoryBean.setDataSource(dataSource());
        // 指定mybatis的核心配置文件
        Resource resource = new ClassPathResource( "SqlMapConfig.xml" );
        sqlSessionFactoryBean.setConfigLocation(resource);
        // 所有配置的mapper文件
        sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources("classpath:com/sva/dao/mapper/*.xml"));
        return sqlSessionFactoryBean;
    }
    
    /** 
     * @Title: dataSourceTransactionManager 
     * @Description: 事务管理
     * @return 
     */
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());
        return dataSourceTransactionManager;
    }
}
