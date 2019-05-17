package com.weather.rest;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableJpaAuditing
public class WeatherPredictRestApiApplication  extends SpringBootServletInitializer{

	
	 @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	        return builder.sources(WeatherPredictRestApiApplication.class);
	    }
	
	public static void main(String[] args) {
		SpringApplication.run(WeatherPredictRestApiApplication.class, args);
	}

	
	
}
