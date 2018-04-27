package com.gl365.member;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.alibaba.druid.pool.DruidDataSource;
import com.gl365.dao.EnableMySqlAutoConfiguration;
import redis.clients.jedis.JedisPoolConfig;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients
@EnableTransactionManagement
@EnableAsync
@ServletComponentScan({ "com.gl365.dao.monitor" })
@MapperScan("com.gl365.member.mapper")
@EnableSwagger2
@Configuration
@EnableMySqlAutoConfiguration
public class Application implements TransactionManagementConfigurer {
	@Value("${gl365.member.redis.maxTotal}")
	private int maxTotal;
	@Value("${gl365.member.redis.maxIdle}")
	private int maxIdle;
	@Value("${gl365.member.redis.maxWaitMillis}")
	private long maxWaitMillis;
	@Value("${gl365.member.redis.database}")
	private int database;
	@Value("${gl365.member.redis.host}")
	private String host;
	@Value("${gl365.member.redis.password}")
	private String password;
	@Value("${gl365.member.redis.port}")
	private int port;
	@Value("${gl365.member.redis.usePool}")
	private boolean usePool;
	@Value("${gl365.member.redis.timeout}")
	private int timeout;
	
	@Autowired
	private DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public StringRedisTemplate getRedisTemplate() {
		StringRedisTemplate redisTemplate = new StringRedisTemplate();
		redisTemplate.setConnectionFactory(connectionFactory());
		return redisTemplate;
	}

	@Bean
	public RedisConnectionFactory connectionFactory() {
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory(config());
		connectionFactory.setDatabase(database);
		connectionFactory.setHostName(host);
		connectionFactory.setPassword(password);
		connectionFactory.setPort(port);
		connectionFactory.setUsePool(usePool);
		connectionFactory.setTimeout(timeout);
		return connectionFactory;
	}

	@Bean
	public JedisPoolConfig config() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMaxWaitMillis(maxWaitMillis);
		return config;
	}
	
	@Bean
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
//		DruidDataSource ds = (DruidDataSource)dataSource;
//		ds.setMaxActive(100);
//		ds.setInitialSize(15);
//		ds.setMinIdle(1);
//		ds.setTestWhileIdle(true);
//		ds.setValidationQuery("select 0");
		return new DataSourceTransactionManager(dataSource);
	}
}
