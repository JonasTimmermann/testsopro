package de.emp2020.securityConfig;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.JdbcUserDetailsManager;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


/** 
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Autowired
public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
// configure AuthenticationManager so that it knows from where to load
// user for matching credentials
// Use BCryptPasswordEncoder
auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
}
@Bean
public PasswordEncoder passwordEncoder() {
return new BCryptPasswordEncoder();
}
@Bean
@Override
public AuthenticationManager authenticationManagerBean() throws Exception {
return super.authenticationManagerBean();
}
@Override
protected void configure(HttpSecurity httpSecurity) throws Exception {
// We don't need CSRF for this example
httpSecurity.csrf().disable()
// dont authenticate this particular request
.authorizeRequests().antMatchers("/authenticate").
permitAll().antMatchers(HttpMethod.OPTIONS, "/**")
.permitAll().
// all other requests need to be authenticated
anyRequest().authenticated().and().
// make sure we use stateless session; session won't be used to
// store user's state.
exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
// Add a filter to validate the tokens with every request
httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
}
}

**/






//@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@EnableWebSecurity
public class EmployeeSecurityConfiguration extends WebSecurityConfigurerAdapter {

/** 
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private UserDetailsService jwtUserDetailsService;
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
**/

	@Autowired
	DataSource dataSource;

	// Enable jdbc authentication
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource);
	}

	@Bean
	public JdbcUserDetailsManager jdbcUserDetailsManager() throws Exception {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
		jdbcUserDetailsManager.setDataSource(dataSource);
		return jdbcUserDetailsManager;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}


	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
		configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/**  working local
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/register").permitAll().antMatchers("/welcome").permitAll().antMatchers("/alerts/**").permitAll()
				.antMatchers("/app/**").permitAll()
				.antMatchers("/hello").hasAnyRole("ADMIN").antMatchers("/getEmployees").hasAnyRole("USER", "ADMIN")
				.antMatchers("/moin").hasRole("ADMIN")
				.antMatchers("/hello2").hasRole("USER")
				.antMatchers("/addNewEmployee").hasAnyRole("ADMIN").anyRequest().authenticated().and().httpBasic(); 
**/


				//http.csrf().disable().antMatcher("/**")
				http.csrf().disable().cors().configurationSource(corsConfigurationSource()).and().antMatcher("/**")
		.authorizeRequests().antMatchers("/test").permitAll().antMatchers("/helloworld/get").permitAll()
		.antMatchers("/welcome").hasRole("ADMIN")
		.antMatchers("hello").hasRole("USER")
		.anyRequest()
				.authenticated().and()
				.httpBasic();


				//.and().formLogin()
				//.loginPage("/login").permitAll().and().logout().permitAll();

/** 

		http.csrf().disable().cors().configurationSource(corsConfigurationSource()).and().antMatcher("/**").authorizeRequests().antMatchers("/fragen").permitAll().antMatchers("/fragen/{id}").permitAll()
		.antMatchers("/type/{formType}").permitAll().antMatchers("/category/all").permitAll()
		.antMatchers("/type/{formType}/category/{category}").permitAll().antMatchers("/type/{form_id}/category/{category_id}/findstart").permitAll()
		.antMatchers("/category/{category}").permitAll().antMatchers("/frage/{id}/choices").permitAll()
		.antMatchers("/filled/forms/all").permitAll().antMatchers("/filled/forms/{form_id}").permitAll()
		.antMatchers("/filled/forms/add").permitAll().antMatchers("/filled/forms/{form_id}/update").permitAll() 
		.antMatchers("/filled/forms/{form_id}/delete").permitAll().antMatchers("/filled/forms/{form_id}").permitAll()
		.antMatchers("/forms/all").permitAll().antMatchers("/downloadFile/{fileId:.+}").permitAll()
		.antMatchers("/uploadFile/{answerId}").permitAll().antMatchers("/uploadMultipleFiles/{answerId}").permitAll()
		.antMatchers("/uploadFile/{id}/{answerId}/update").permitAll().antMatchers("/forms/{form_id}/question/{question_id}/answers/add").permitAll()
		.antMatchers("/forms/{form_id}/question/{question_id}/answers/{answers_id}/edit").permitAll().antMatchers("/forms/{form_id}/question/{question_id}/answers").permitAll()
		.antMatchers("/forms/{form_id}/answers/all/delete").permitAll().antMatchers("/forms/{form_id}/answers/all/add").permitAll()
		.antMatchers("/forms/{form_id}/answers/all/edit").permitAll().antMatchers("/form/{form_id}/category/{category_id}/findstart").permitAll()
		.antMatchers("/findAllStarts").permitAll()
		//.antMatchers("/api/v1/basicauth").permitAll()
		.anyRequest()
				.authenticated().and()
				.httpBasic();
				**/
	}
	 
	@Autowired
	 public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
		authenticationMgr.inMemoryAuthentication().withUser("admin").password("admin").authorities("ROLE_USER")
		.and().withUser("jonas").password("password").authorities("ROLE_ADMIN");
	 }
	// @Autowired
	// public void configureGlobal(AuthenticationManagerBuilder authenticationMgr)
	// throws Exception {
	// authenticationMgr.inMemoryAuthentication().withUser("admin").password("admin").authorities("ROLE_USER").and()
	// .withUser("javainuse").password("javainuse").authorities("ROLE_USER",
	// "ROLE_ADMIN");
	// }

}