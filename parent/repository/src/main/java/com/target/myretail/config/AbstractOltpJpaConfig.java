package com.target.myretail.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.target.myretail.exception.PersistentException;

public abstract class AbstractOltpJpaConfig {

	/**
	 * @return
	 */
	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

	/**
	 * @param url
	 * @param username
	 * @param password
	 * @param minPoolsize
	 * @param maxPoolsize
	 * @return
	 * @throws PersistentException
	 */
	protected DataSource getDataSource(final String url, final String username, final String password,
			final int minPoolsize, final int maxPoolsize, final String drivername) throws PersistentException {
		final ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass(drivername);
		} catch (final PropertyVetoException ex) {
			throw new PersistentException("Failed to load DB Driver class.", ex);
		}
		dataSource.setJdbcUrl(url);
		dataSource.setUser(username);
		dataSource.setPassword(password);
		dataSource.setMinPoolSize(minPoolsize);
		dataSource.setMaxPoolSize(maxPoolsize);
		return dataSource;
	}

	/**
	 * @return
	 */
	protected Properties hibernateProperties(final String dialect) {

		final Properties properties = new Properties();
		properties.put("hibernate.dialect", dialect);
		properties.put("hibernate.show_sql", "false");
		properties.put("hibernate.format_sql", "true");
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.current_session_context_class", "thread");
		properties.put("hibernate.enable_lazy_load_no_trans", "true");
		return properties;
	}

	/**
	 * @param dataSource
	 * @param basePackage
	 * @return
	 */
	protected LocalContainerEntityManagerFactoryBean getEntityManagerFactory(final DataSource dataSource,
			final String basePackage, final String dialect) {

		final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		entityManagerFactoryBean.setPackagesToScan(basePackage);
		entityManagerFactoryBean.setJpaProperties(this.hibernateProperties(dialect));
		return entityManagerFactoryBean;
	}

	/**
	 * @param entityManagerFactory
	 * @return
	 */
	protected JpaTransactionManager getTransactionManager(final EntityManagerFactory entityManagerFactory) {
		final JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		return txManager;
	}

}
