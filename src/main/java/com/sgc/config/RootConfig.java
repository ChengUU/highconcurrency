package com.sgc.config;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
//定义Spring扫描的包
@ComponentScan(value = "com.*",includeFilters={@ComponentScan.Filter(type=FilterType.ANNOTATION,value={Service.class})})
//使用事务驱动管理器
@EnableTransactionManagement
public class RootConfig implements TransactionManagementConfigurer {
    private DataSource dataSource=null;

    /**
     * 数据库配置
     * @return
     */
    @Bean(name = "dataSource")
    public DataSource initDataSource(){
        if(null!=dataSource) return dataSource;
        try{
            Properties properties=new Properties();
            properties.load(RootConfig.class.getClassLoader().getResourceAsStream("jdbc.properties"));
            properties.setProperty("driverClassName",properties.getProperty("jdbc.driver"));
            properties.setProperty("url",properties.getProperty("jdbc.url"));
            properties.setProperty("username",properties.getProperty("jdbc.username"));
            properties.setProperty("password",properties.getProperty("jdbc.password"));
            dataSource=BasicDataSourceFactory.createDataSource(properties);
        }catch(Exception e){
            e.printStackTrace();
        }
        return dataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean initSqlSessionFactory(){
        SqlSessionFactoryBean sqlSessionFactory=new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(initDataSource());

        //配置mybatis配置文件
        Resource resource=new ClassPathResource("mybatis/mybatis-config.xml");
        sqlSessionFactory.setConfigLocation(resource);
        return sqlSessionFactory;
    }

    /**
     * 通过自动扫描,发现mybatis mapper接口
     * @return
     */
    @Bean
    public MapperScannerConfigurer initMapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer=new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.*");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setAnnotationClass(Repository.class);
        return mapperScannerConfigurer;
    }

    /**
     * 实现接口方法,注册注解事务,当@Transactional使用的时候产生数据库事务
     * @return
     */
   @Bean(name = "annotationDrivenTransactionManager")
   @Override
   public PlatformTransactionManager annotationDrivenTransactionManager(){
       DataSourceTransactionManager transactionManager=new DataSourceTransactionManager();
       transactionManager.setDataSource(initDataSource());
       return transactionManager;
   }
}
