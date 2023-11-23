package com.example.springkmsmybatis3.config;

import com.example.springkmsmybatis3.dao.mapper.VehicleEntityJavaMapper;
import com.example.springkmsmybatis3.dao.typehandler.EncrappedTypeHandler;
import com.example.springkmsmybatis3.dao.typehandler.EncryptedTypeHandler;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class MyBatisConfig implements WebMvcConfigurer {

    static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";
    static final String CONNECTION_TEST_QUERY = "SELECT 1";
    static final int IDLE_TIMEOUT = 60 * 1000;
    static final int VALIDATION_TIMEOUT = 15000;
    static final int MAX_LIFE_TIME = 60 * 1000;
    static final int LEAK_DETECTION_THRESHOLD = 30 * 1000;
    static final String DATASOURCE_PRIMARY = "primary";

//    @Bean
//    public DataSource dataSource() {
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .addScript("schema.sql")
//                .addScript("data.sql")
//                .build();
//    }

    @Primary
    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(DRIVER_CLASS_NAME);
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres?application_name=vehicle");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("password");
        hikariConfig.setMaximumPoolSize(50);
        hikariConfig.setMinimumIdle(10);
        hikariConfig.setConnectionTestQuery(CONNECTION_TEST_QUERY);
        hikariConfig.setIdleTimeout(IDLE_TIMEOUT);
        hikariConfig.setValidationTimeout(VALIDATION_TIMEOUT);
        hikariConfig.setMaxLifetime(MAX_LIFE_TIME);
        hikariConfig.setLeakDetectionThreshold(LEAK_DETECTION_THRESHOLD);
        hikariConfig.setPoolName(DATASOURCE_PRIMARY);

        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());
        return dataSourceTransactionManager;
    }

    @Bean
    public DefaultTransactionDefinition defaultTxDefinition() {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setTimeout(5000);
        return defaultTransactionDefinition;
    }

    @Bean
    @Autowired
    public SqlSessionFactory sqlSessionFactory(EncryptedTypeHandler encryptedTypeHandler,
                                               EncrappedTypeHandler encrappedTypeHandler) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        //factoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        // The reason behind DataEncryptDecrypt bean not being autowired originates from
        // the commented out code. Type handlers must be injected as beans, not constructed
        // in the code. The alternative would be to set DataEncryptDecrypt at later time as
        // being done in the sibling JDBC example project. This approach may be preferred in
        // the case of type handlers defined in the configuration xml file.
        //factoryBean.setTypeHandlers(new EncryptedTypeHandler(), new EncrappedTypeHandler());
        factoryBean.setTypeHandlers(encryptedTypeHandler, encrappedTypeHandler);
        factoryBean.setMapperLocations(
                new ClassPathResource("com/example/springkmsmybatis3/dao/VehicleEntityXMLMapper.xml"));
        factoryBean.setDataSource(dataSource());
        return factoryBean.getObject();
    }

    @Bean
    @Autowired
    // Learning from https://mybatis.org/spring/getting-started.html, https://mybatis.org/mybatis-3/getting-started.html
    public VehicleEntityJavaMapper vehicleMapper(SqlSessionFactory sqlSessionFactory) throws Exception {
        // adding the vehicle mapper to prevent template from throwing "unable to find mapper in the mapper registry" exception
        sqlSessionFactory.getConfiguration().addMapper(VehicleEntityJavaMapper.class);
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate.getMapper(VehicleEntityJavaMapper.class);
    }

}