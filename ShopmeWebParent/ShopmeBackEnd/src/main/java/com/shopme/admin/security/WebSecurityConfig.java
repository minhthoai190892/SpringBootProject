package com.shopme.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
	@Bean
	public PasswordEncoder   passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	 UserDetailsService userDetailsService() {
		return new ShopmeUserDetailsService();
	}
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.
					authorizeHttpRequests(config->config
							.requestMatchers("/assest/**").permitAll()
							.anyRequest().authenticated()
							
							)
					.formLogin(login->login
							.loginPage("/login")
							.usernameParameter("email")
							.permitAll()
							)
					;
							

		return httpSecurity.build();
		
	
	}
}
