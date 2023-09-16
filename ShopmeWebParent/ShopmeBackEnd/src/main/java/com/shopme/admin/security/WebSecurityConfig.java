package com.shopme.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
							.requestMatchers("/css/**").permitAll()
							.requestMatchers("/images/**").permitAll()
							.requestMatchers("/users/**").hasAuthority("Admin")
							.requestMatchers("/categories/**").hasAnyAuthority("Admin","Editor")
							.anyRequest().authenticated()
							
							)
					.formLogin(login->login
							.loginPage("/login")
							.usernameParameter("email")
							.permitAll()
							)
					
					.logout(logout->logout.permitAll())
					.rememberMe(me -> me//kích hoạt nhớ tài khoản
	                        //tạo một key mặc định cho ứng dụng trong cookie
	                        .key("bWluaHRob2FpJTQwZ21haWxxd2UuY29tOjE2ODc0OTQ0NzEwNTA6U0hBMjU2OjdiNTk5NTliOTU1MDUxOTgwOTJjMzFhYzMwMGIyNDc0MTA5ZjQ0YWE5YmJlZmE0ZWExZDIyZDRjOWYyMjlkMjA")
	                        .tokenValiditySeconds(7 * 24 * 60 * 60))//thây đổi thời gian hết hạn của key 
					;
							

		return httpSecurity.build();
		
	
	}
}
