package com.example.demo.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories(
        entityManagerFactoryRef = "demoEntityManagerFactory",
        transactionManagerRef = "demoTransactionManager",
        basePackages = {"com.example.demo"})
@EnableJpaAuditing
public class PersistenceConfig {

    @Autowired
    private Environment env;

    @Bean(name = "demoDataSource")
    @ConfigurationProperties(prefix = "demo.spring.datasource")
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));

        return dataSource;
    }

    @Bean(name = "demoEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean detectEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                             @Qualifier("demoDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.example.demo")
                .build();
    }

    @Primary
    @Bean(name = "demoTransactionManager")
    public PlatformTransactionManager detectTransactionManager(
            @Qualifier("demoEntityManagerFactory") EntityManagerFactory
                    entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
