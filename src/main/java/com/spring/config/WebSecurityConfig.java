package com.spring.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.context.annotation.PropertySource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
//import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.spring.config.JwtAuthenticationEntryPoint;
//import com.spring.config.JwtAuthenticationFilter;
import com.spring.loginterceptor.LoggingFilter;
 
import com.spring.model.ConfigMaster;
import com.spring.model.ConfigSlave;
import com.spring.model.WishList;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.transaction.Transactional;

@Configuration
@EnableTransactionManagement
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource(value = "application.properties", ignoreResourceNotFound = true)

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

   

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.cors().and().csrf().disable().
               authorizeRequests()
                .antMatchers( "/wish/*","/signup").permitAll()
                //.antMatchers("/post/getPostDetailsById").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .addFilterBefore(loggingFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }
   
  
    public void integrate(
            Metadata metadata,
            SessionFactoryImplementor sessionFactory,
            SessionFactoryServiceRegistry serviceRegistry) {

        metadata.getSqlFunctionMap().put("group_concat", new StandardSQLFunction( "group_concat", StandardBasicTypes.STRING)); 

    }

    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) {
    LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
         
   // sessionBuilder.addAnnotatedClasses(ConfigMaster.class);
    //sessionBuilder.addAnnotatedClasses(ConfigSlave.class);
    sessionBuilder.addAnnotatedClasses(WishList.class);
    
             return sessionBuilder.buildSessionFactory();
    } 
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create().driverClassName("com.mysql.jdbc.Driver")
                .username("lms").password("password123")
                .url("jdbc:mysql://localhost:3306/lms_wishlist?allowPublicKeyRetrieval=true&useSSL=false").build();
    }
    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
    HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
    return transactionManager;
    }
    private Properties additionalProperties() {
    	Properties properties = new Properties();
    	properties.put("hibernate.show_sql", "true");
    	properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
    	return properties;
    	}
    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
       LocalContainerEntityManagerFactoryBean em1
         = new LocalContainerEntityManagerFactoryBean();
       em1.setDataSource(dataSource());
       em1.setPackagesToScan(new String[] { "com.spring.model" });
  
       JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
       em1.setJpaVendorAdapter(vendorAdapter);
       em1.setJpaProperties(additionalProperties());
  
       return em1;
    }
@Bean
   public LoggingFilter loggingFilterBean() throws Exception {
      return new LoggingFilter();
    }

    

}
