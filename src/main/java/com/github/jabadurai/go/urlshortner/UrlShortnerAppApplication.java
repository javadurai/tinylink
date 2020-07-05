package com.github.jabadurai.go.urlshortner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.github.jabadurai.go.urlshortner"})
@EnableJpaRepositories(basePackages="com.github.jabadurai.go.urlshortner.repositories")
@EnableTransactionManagement
@EntityScan(basePackages="com.github.jabadurai.go.urlshortner.entities")
public class UrlShortnerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShortnerAppApplication.class, args);
	}

}
