package com.example.springjdbcmybatis3.todo.domain;

import com.example.springjdbcmybatis3.vehicle.dao.mapper.VehicleEntityJavaMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jdbc.core.convert.DataAccessStrategy;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.jdbc.mybatis.MyBatisDataAccessStrategy;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJdbcRepositories({"com.example"})
@EntityScan({"com.example"})
//@Import(MyBatisJdbcConfiguration.class) // @Import apparently is not to be used in tests. @ContextConfiguration is to be used in the tests. See TodoMapperTest.
public class DBTestConfig { //extends AbstractJdbcConfiguration { // commented because MyBatisJdbcConfiguration (included in the @ContextConfiguration) does it.

    @Bean
    @Autowired
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        //factoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        //factoryBean.setTypeHandlers(new EncryptedTypeHandler(), new EncrappedTypeHandler());
        factoryBean.setTypeHandlers(new LocalDateTimeTypeHandler());
        // For tests, either xml or java mapper must be configured. Declaring both will result
        // in conflicts. For normal app, execution both can co-exists as long methods declared
        // in one are not to be found in the other knowing both xml and java mapper share the
        // same namespace (or package).
        // factoryBean.setMapperLocations(
        // new ClassPathResource("com/example/springjdbcmybatis3/todo/domain/TodoMapper.xml"));
        factoryBean.setDataSource(dataSource);
        // You may consider using @MapperScan instead of manually adding each mapper here.
        factoryBean.getObject().getConfiguration().addMapper(VehicleEntityJavaMapper.class);
        factoryBean.getObject().getConfiguration().addMapper(TodoMapper.class);
        return factoryBean.getObject();
    }

    // MyBatisJdbcConfiguration requires SqlSession, and so this method uses the factory to create one.
    @Bean
    @Autowired
    public SqlSession sqlSession(SqlSessionFactory factory) {
        return factory.openSession();
    }

//    @Bean
//    @Primary
//    @Override
//    public DataAccessStrategy dataAccessStrategyBean(NamedParameterJdbcOperations operations, JdbcConverter jdbcConverter,
//                                                     JdbcMappingContext context, Dialect dialect) {
//        return super.dataAccessStrategyBean(operations, jdbcConverter, context, dialect);
//    }

//    @Bean
//    @Override
//    public DataAccessStrategy dataAccessStrategyBean(NamedParameterJdbcOperations operations, JdbcConverter jdbcConverter,
//                                                     JdbcMappingContext context, Dialect dialect) {
//        return MyBatisDataAccessStrategy.createCombinedAccessStrategy(context, jdbcConverter, operations, sqlSession, dialect);
//    }

}