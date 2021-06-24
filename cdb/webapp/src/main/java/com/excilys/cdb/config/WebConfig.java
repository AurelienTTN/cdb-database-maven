package com.excilys.cdb.config;

import java.util.Locale;
import java.util.Properties;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableWebMvc
@ComponentScan({"com.excilys.cdb.servlets","com.excilys.cdb.mapper",
	"com.excilys.cdb.daos","com.excilys.cdb.service","com.excilys.cdb.validator"})
@EnableJpaRepositories(basePackages = {"com.excilys.cdb.daos"})
@EnableTransactionManagement
public class WebConfig implements WebMvcConfigurer {
	
	
	//Jpa and hibernate configuration
	
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
       LocalContainerEntityManagerFactoryBean em 
         = new LocalContainerEntityManagerFactoryBean();
       em.setDataSource(this.getDataSource());
       em.setPackagesToScan(new String[] { "com.excilys.cdb.model" });

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
	
	
	  // Bean use to search the views asked
	
	  @Bean
	  public ViewResolver viewResolver() {
	      InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	      viewResolver.setViewClass(JstlView.class);
	      viewResolver.setPrefix("/WEB-INF/views/");
	      viewResolver.setSuffix(".jsp");
	      return viewResolver;
	  }
	  
	  // I18N configuration 
	  
	  @Bean 
	  public MessageSource messageSource() {
		  ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		  messageSource.setBasenames("/Langue/message");
		  messageSource.setDefaultEncoding("ISO-8859-1");
		  return messageSource;
	  }
	 
	  
	  @Bean 
	  public LocaleResolver localResolver() {
		  SessionLocaleResolver slr = new SessionLocaleResolver();
		  slr.setDefaultLocale(Locale.FRENCH);
		  return slr;
	  }
	 
	  
	  @Override
	  public void addInterceptors(InterceptorRegistry registry) {
		  LocaleChangeInterceptor localeChangeInter = new LocaleChangeInterceptor();
		  localeChangeInter.setParamName("lang");
		  registry.addInterceptor(localeChangeInter);
	  }
	  
	  //To find ressources
	  
	  @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry
	          .addResourceHandler("/resources/**")
	          .addResourceLocations("/resources/");	
	    }
	  
	  // Datasource configuration Bean 
	  @Bean
	  public HikariDataSource getDataSource() {
		  	  HikariConfig config=new HikariConfig();
			  config.setJdbcUrl( "jdbc:mysql://127.0.0.1:3306/computer-database-db" );
	          config.setUsername( "admincdb" );
	          config.setPassword( "qwerty1234" );
	          config.setDriverClassName("com.mysql.cj.jdbc.Driver");
	          config.addDataSourceProperty( "cachePrepStmts" , "true" );
	          config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
	          config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
	          return new HikariDataSource( config );
	    }
	  
	  

}
