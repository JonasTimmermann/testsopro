package de.emp2020.securityConfig;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{

@Override
public void addCorsMappings(CorsRegistry corsRegistry){
	corsRegistry.addMapping("/**")
	.allowedOrigins("*")
    .allowedMethods("*")
    .maxAge(3600L)
	.allowedHeaders("*")
	.exposedHeaders("Authorization")
	.allowCredentials(true);
}

}
