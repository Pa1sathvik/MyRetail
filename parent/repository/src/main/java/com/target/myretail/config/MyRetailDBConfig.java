package com.target.myretail.config;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.target.myretail.exception.PersistentException;

@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:database.properties" })
@EnableJpaRepositories(basePackages = "com.target.myretail.repository", entityManagerFactoryRef = "myRetailEntityManagerFactory",
transactionManagerRef = "myRetailTransactionManager")
public class MyRetailDBConfig extends AbstractOltpJpaConfig {

	
	@Value("${myRetaildb.url}")
	private String url;

	@Value("${myRetaildb.username}")
	private String username;

	@Value("${myRetaildb.password}")
	private String password;

	@Value("${myRetaildb.minPoolsize}")
	private int minPoolsize;

	@Value("${myRetaildb.maxPoolsize}")
	private int maxPoolsize;

	@Value("${myRetaildb.packageToScan}")
	private String packageToScan;
	
	@Value("${myRetaildb.driverClassName}")
	private String drivername;
	
	@Value("${myRetaildb.Dialect}")
	private String dialect;
	
	
	/**
	 * @return
	 * @throws PersistentException 
	 */
	@Bean(name = "myRetailDataSource", destroyMethod = "close")
	public DataSource dataSource() throws PersistentException {
		return super.getDataSource(this.url, this.username.trim(), this.password.trim(), this.minPoolsize, this.maxPoolsize,this.drivername);
	}

	/**
	 * @param dataSource
	 * @return
	 */
	@Inject
	@Bean(name = "myRetailEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("myRetailDataSource") final DataSource dataSource) {

		return super.getEntityManagerFactory(dataSource, packageToScan.trim(),dialect.trim());
	}

	/**
	 * @param sessionFactory
	 * @return
	 */
	@Inject
	@Bean(name = "myRetailTransactionManager")
	public JpaTransactionManager transactionManager(
			@Qualifier("myRetailEntityManagerFactory") final EntityManagerFactory entityManagerFactory) {

		return super.getTransactionManager(entityManagerFactory);
	}
}
