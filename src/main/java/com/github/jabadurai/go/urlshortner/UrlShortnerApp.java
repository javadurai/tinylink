package com.github.jabadurai.go.urlshortner;

import com.github.jabadurai.go.urlshortner.config.AuditorAwareImpl;
import com.github.jabadurai.go.urlshortner.entities.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Optional;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.github.jabadurai.go.urlshortner"})
@EnableJpaRepositories(basePackages="com.github.jabadurai.go.urlshortner.repositories")
@EnableTransactionManagement
@EntityScan(basePackages="com.github.jabadurai.go.urlshortner.entities")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class UrlShortnerApp {

	@Bean
	public AuditorAware<String> auditorAware(){
		return new AuditorAwareImpl();
	}


	public static void main(String[] args) {
		SpringApplication.run(UrlShortnerApp.class, args);
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource
				= new ReloadableResourceBundleMessageSource();

		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public LocalValidatorFactoryBean getValidator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}

}
