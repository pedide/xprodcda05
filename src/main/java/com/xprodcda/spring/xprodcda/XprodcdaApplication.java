package com.xprodcda.spring.xprodcda;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication

public class XprodcdaApplication {

	public static void main(String[] args){
		SpringApplication.run(XprodcdaApplication.class, args);		
		
		}
	@Bean
	public CorsFilter corsFilter() {
		
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200")); //http://localhost:4200
		//corsConfiguration.setAllowedOrigins(Arrays.asList(
		//		"localhost:4200","http://localhost:4200"));
		//corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", 
				"Access-Control-Allow-Origin", 
				"Content-Type",
				"Accept", 
				"Jwt-Token", 
				"Authorization",
				"Origin, Accept", 
				"X-Requested-With",
				"Access-Control-Request-Method",
				"Access-Control-Request-Headers"));
		corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", 
				"Content-Type",
				"Accept", "Jwt-Token",
				"Authorization",
				"Access-Control-Allow-Origin", 
				"Access-Control-Allow-Origin", 
				"Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
		corsConfiguration.setMaxAge(3600L);   //cache à3600 secondes
		//corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
	
	
	@Bean 
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
