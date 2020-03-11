package de.emp2020;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class Emp2020Application {
	
	@Bean
	public RestTemplate createRestTemplate ()
	{
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(Emp2020Application.class, args);
	}

}
