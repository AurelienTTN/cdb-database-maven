package config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({"com.excilys.controlers","com.excilys.mapper",
"com.excilys.service","com.excilys.ui","com.excilys.validator","com.excilys.persistence"})
@EnableJpaRepositories(basePackages = {"com.excilys.persistence"})
@EnableTransactionManagement
public class AppConfig {
		
	
	
		private HikariConfig config=new HikariConfig();
		
	    @Bean
	    public DataSource getDataSource() {
	    		
			  config.setJdbcUrl( "jdbc:mysql://127.0.0.1:3306/computer-database-db" );
	          config.setUsername( "admincdb" );
	          config.setPassword( "qwerty1234" );
	          config.setDriverClassName("com.mysql.jdbc.Driver");
	          config.addDataSourceProperty( "cachePrepStmts" , "true" );
	          config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
	          config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
	          return new HikariDataSource( config );
	    }
	    
	    //config dao jpa
	    
	    @Bean
	    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	       LocalContainerEntityManagerFactoryBean em 
	         = new LocalContainerEntityManagerFactoryBean();
	       em.setDataSource(this.getDataSource());
	       em.setPackagesToScan(new String[] { "com.excilys.model" });

	       JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	       em.setJpaVendorAdapter(vendorAdapter);
	       em.setJpaProperties(this.additionalProperties());

	       return em;
	    }
	    
	   
	    @Bean
	    public PlatformTransactionManager transactionManager() {
	        JpaTransactionManager transactionManager = new JpaTransactionManager();
	        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

	        return transactionManager;
	    }

	    @Bean
	    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
	        return new PersistenceExceptionTranslationPostProcessor();
	    }
	    
	    Properties additionalProperties() {
	        Properties properties = new Properties();
	        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
	           
	        return properties;
	    }

}