package com.example.demo.securityConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	@Bean
	
	UserDetailsService userDetail() {
		
		InMemoryUserDetailsManager userDetailmanager= new InMemoryUserDetailsManager();
		UserDetails adminuser=User.withUsername("admin").password("{noop}12345").build();
		
		userDetailmanager.createUser(adminuser);
		return userDetailmanager;
		
		
	}
	
	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrfConfig->csrfConfig.disable())
		.authorizeHttpRequests(auth->auth.requestMatchers("/api/admin/products/**")
				.authenticated()
		.requestMatchers("/api/products/**")
		.permitAll())
		.httpBasic();
		return http.build();
		
	}

}
