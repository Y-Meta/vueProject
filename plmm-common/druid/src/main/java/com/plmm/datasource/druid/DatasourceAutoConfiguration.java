package com.plmm.datasource.druid;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.plmm.datasource.druid.config.DatasourceProperties;
import com.plmm.datasource.druid.config.DatasourceProperty;
import com.plmm.datasource.druid.config.Node;
import com.plmm.datasource.druid.filter.SQLFilter;
import com.plmm.datasource.druid.routing.MultipleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.fastjson.JSON;
import com.plmm.cryptography.Base64Cryptography;
import com.plmm.cryptography.Cryptography;

/**
 * 不同配置加载不同的数据源,非开发环境还需进行密文解密操作
 * single 加载单一数据源，
 * routing 加载带多数据源通过dsKey与数据源名称进行匹配，选择执行sql的数据源
 * routingwriteread 除路由功能后，还有读写分离的能力，通过prefix属性来区分读写操作
 */
@Configuration
//@EnableConfigurationProperties(value={DatasourceProperties.class,DatasourceProperty.class})
public class DatasourceAutoConfiguration {
	
	private Logger logger = LoggerFactory.getLogger(DatasourceAutoConfiguration.class);
	
	@Autowired
	@Qualifier("datasourceProperties")
    DatasourceProperties datasourceProperties;
	
	@Autowired
	@Qualifier("datasourceProperty")
    DatasourceProperty datasourceProperty;
	
	/**
	 * 是否开启SQL拦截数据同步
	 */
	@Value("${plmm.sqlAop.isUse:false}")
	boolean isUseSqlAop;
	/**
	 * 解密器
	 */
	@Autowired(required=false)
	Cryptography cryptography;
	
	
	@Value("${plmm.datasource.isRemoveAbandoned:false}")
    boolean isRemoveAbandoned;
	/**
	 * 初始化数据源
	 * @return 数据源
	 */
	@RefreshScope
	@Bean("datasource")
	public DataSource datasource() {
		logger.info("数据源连接池信息如下:{}", JSON.toJSONString(datasourceProperties));
		logger.info("SQL AOP 状态: {}", isUseSqlAop);
		if(DatasourceProperty.DATASOURCE_TYPE_SINGLE.equals(datasourceProperty.getType())){
			logger.info("亲，你选择的是single数据源，配置信息如下 ：{}", JSON.toJSONString(getNodeByName(datasourceProperty.getName())));
			return createDatasource(datasourceProperties, getNodeByName(datasourceProperty.getName()), false);
		} else if(DatasourceProperty.DATASOURCE_TYPE_ROUTING.equals(datasourceProperty.getType())) {
			MultipleDataSource mds = new MultipleDataSource();
			Map<Object, Object> dsMap = new HashMap<Object, Object>();
			logger.info("亲，你选择的是routing数据源，配置信息如下 ：");
			for(Node node : getNodeByName(datasourceProperty.getName().split(","))) {
				logger.info("node name:{}, properties:{}", node.getName(), JSON.toJSONString(node));
		        dsMap.put(node.getName(), createDatasource(datasourceProperties, node, false));
			}
			mds.setTargetDataSources(dsMap);
			String defaultName = dsMap.keySet().toArray(new String[0])[0];
			logger.info("default datasource is {}", defaultName);
			mds.setDefaultTargetDataSource(dsMap.get(defaultName));
			return mds;
		} else if(DatasourceProperty.DATASOURCE_TYPE_ROUTING_READWRITE.equals(datasourceProperty.getType())) {
			MultipleDataSource mds = new MultipleDataSource();
			Map<Object, Object> dsMap = new HashMap<Object, Object>();
			logger.info("亲，你选择的是routingreadwrite数据源，配置信息如下 ：");
			for(Node node : getNodeByName(datasourceProperty.getName().split(","))) {
		        dsMap.put(node.getName(), createDatasource(datasourceProperties, node, false));
		        logger.info("node name:{}, properties:{}", node.getName(), JSON.toJSONString(node));
		        Node dsp = node.getSlave();
		        if(dsp != null) {
		        	logger.info("node's slave name:{}, properties:{}", dsp.getName(), JSON.toJSONString(dsp));
		        	dsMap.put(node.getName() + "-slave", createDatasource(datasourceProperties, dsp, false));
		        }
			}
			mds.setTargetDataSources(dsMap);
			String defaultName = dsMap.keySet().toArray(new String[0])[0];
			logger.info("default datasource is {}", defaultName);
			mds.setDefaultTargetDataSource(dsMap.get(defaultName));
			return mds;
		} else {
			logger.error("亲，你的配置[{}]有问题，请仔细检查下数据源类型是否为[single,routing,routingreadwrite]中的一种", datasourceProperty.getType());
			throw new RuntimeException("亲，你的配置["+ datasourceProperty.getType() + "]有问题，请仔细检查下数据源类型是否为[single,routing,routingreadwrite]中的一种");
		}
	}
	
	/**
	 * 通过配置生成druid数据源
	 * @param DatasourceProperties 数据源配置 连接池信息配置 
	 * @param node 数据源连接信息配置 
	 * @return 数据源
	 */
	private DataSource createDatasource(DatasourceProperties datasourceProperties, Node node, boolean isJta) {
		DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(node.getUrl());
        datasource.setUsername(node.getUsername());
        datasource.setDriverClassName(node.getDriverClassName());
        String dev = System.getProperty("spring.profiles.active");
		String passwd = node.getPassword();
		if(!(dev==null || "default".equals(dev))) {//非开发环境密码需解密
			passwd = cryptography.decrypt(passwd);
	    } 
        datasource.setPassword(passwd); 
        //configuration  
        datasource.setInitialSize(datasourceProperties.getInitialSize());  
        datasource.setMinIdle(datasourceProperties.getMinIdle());  
        datasource.setMaxActive(datasourceProperties.getMaxActive());  
        datasource.setMaxWait(datasourceProperties.getMaxWait());  
        datasource.setTimeBetweenEvictionRunsMillis(datasourceProperties.getTimeBetweenEvictionRunsMillis());  
        datasource.setMinEvictableIdleTimeMillis(datasourceProperties.getMinEvictableIdleTimeMillis());  
        datasource.setValidationQuery(datasourceProperties.getValidationQuery());  
        datasource.setTestWhileIdle(datasourceProperties.isTestWhileIdle());
        datasource.setTestOnBorrow(datasourceProperties.isTestOnBorrow());
        datasource.setTestOnReturn(datasourceProperties.isTestOnReturn());
        datasource.setPoolPreparedStatements(datasourceProperties.isPoolPreparedStatements());
        datasource.setMaxPoolPreparedStatementPerConnectionSize(datasourceProperties.getMaxPoolPreparedStatementPerConnectionSize());  
        datasource.setConnectionProperties(datasourceProperties.getConnectionProperties()); 
        datasource.setProxyFilters(druidSqlFilter());
        if(this.isRemoveAbandoned) {
            datasource.setRemoveAbandoned(datasourceProperties.isRemoveAbandoned());
            datasource.setRemoveAbandonedTimeoutMillis(datasourceProperties.getRemoveAbandonedTimeoutMillis());
            datasource.setLogAbandoned(true);
            logger.info("开启removeAbandoned模式，获取连接后在{}毫秒内不执行完的SQL将会被取消", datasourceProperties.getRemoveAbandonedTimeoutMillis());
        }
        try {  
            datasource.setFilters(datasourceProperties.getFilters()); 
            datasource.init();
            logger.info("datasource name:[{}] url:[{}] username:[{}] inited successfully!", node.getName(), node.getUrl(), node.getUsername());
        } catch (SQLException e) {
            logger.error("datasource inited failed", e);  
        } catch (Exception e) {
			logger.error("db password decrypt failed");
		}
		return datasource;
	}
	
	private Node getNodeByName(String name) {
		Node temp = null;
		List<Node> nodes = datasourceProperties.getNodes();
		for(Node node : nodes) {
			if(name.equals(node.getName())) {
				temp = node;
			}
		}
		return temp;
	}
	
	private List<Node> getNodeByName(String[] names) {
		List<Node> temp = new ArrayList<Node>(names.length);
		for(String name : names) {
			temp.add(getNodeByName(name));
		}
		return temp;
	}
	
	@Bean
	public List<Filter> druidSqlFilter(){
		List<Filter> list = new ArrayList<Filter>(1);
		list.add(new SQLFilter());
		return list;
	}
	
	@Bean
	@ConditionalOnMissingBean
	@Profile({"dev","pro","uat","ft","sit","pre"})
	public Cryptography cryptography(){
		return new Base64Cryptography();
	}
	
    @Bean
    public ServletRegistrationBean<StatViewServlet> druidServlet() {
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<StatViewServlet>();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("loginUsername", "admin");// 用户名
        initParameters.put("loginPassword", "admin");// 密码
        initParameters.put("resetEnable", "false");// 禁用HTML页面上的“Reset All”功能
        initParameters.put("allow","");// IP白名单 (没有配置或者为空，则允许所有访问)
        initParameters.put("deny","");// IP黑名单 (存在共同时，deny优先于allow)
        servletRegistrationBean.setInitParameters(initParameters);
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<WebStatFilter> filterRegistrationBean() {
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<WebStatFilter>();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
	
}
