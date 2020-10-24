package com.plmm.datasource.druid.mybatis;

import javax.sql.DataSource;

import com.plmm.datasource.druid.mapper.DefaultService;
import com.plmm.datasource.druid.mapper.IDefaultService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;


@Configuration //调用配置服务中的配置文件
@EnableTransactionManagement
@EnableApolloConfig("tp_micro_service.ds_mysql_config")// 指定namespace
@ConditionalOnExpression("'${datasource-config.type}'=='single'")
//@ConditionalOnExpression("'${plmm.datasource-config.type}'=='single'")
public class SingleMybatisConfig implements TransactionManagementConfigurer {

	@Autowired
	private DataSource dataSource ;
	
	//获取配置文件中mybatisConfig配置文件路径
	@Value("${mybatis.location}")
	private String configLocation;
	
	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setConfigLocation(resolver.getResource(configLocation));
		return sqlSessionFactoryBean.getObject();
	}

	@Bean(name = "sqlSession")
	public SqlSessionTemplate sqlSession(@Qualifier(value = "sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource){
		return new JdbcTemplate(dataSource);
	}

	@Bean(name = "defaultService")
	@Primary
	public IDefaultService defaultService(
			@Qualifier(value = "sqlSession") SqlSessionTemplate sqlSession,
			@Qualifier(value = "jdbcTemplate") JdbcTemplate jdbcTemplate) {
		DefaultService service = new DefaultService();
		service.setSession(sqlSession);
		service.setJdbcTemplate(jdbcTemplate);
		return service;
	}
	
	
}
