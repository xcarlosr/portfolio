package br.com.gerencia.portfolio;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//@OpenAPIDefinition
@SpringBootApplication
public class PortfolioApplication extends SpringBootServletInitializer {

	@Override
	public SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(PortfolioApplication.class);
	}
	
	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT-03:00"));
		SpringApplication.run(PortfolioApplication.class, args);
	}

}
