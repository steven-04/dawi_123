package com.project.consorcio;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.project.consorcio.security.UsuarioDetailsService;

//clase de configuración para Spring Security
@Configuration
//habilitar seguridad
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public BCryptPasswordEncoder encriptar() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.authorizeHttpRequests((requests) -> requests
			.requestMatchers("/sesion/**","resources/**").permitAll()
			.requestMatchers("/medicamento/**","/requerimiento/**").authenticated()
		)
		.formLogin((form) -> form
			.loginPage("/sesion/login").defaultSuccessUrl("/sesion/intranet")
			.permitAll()
		)
		.logout((logout) -> logout.permitAll());

	return http.build();
	}
	
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UsuarioDetailsService();
	}
	@Bean
	public AuthenticationProvider authenticationProvider() {
		//crear objeto de DaoAuthenticationProvider
		DaoAuthenticationProvider dao=new DaoAuthenticationProvider();
		//setear
		dao.setUserDetailsService(userDetailsService());
		dao.setPasswordEncoder(encriptar());
		return dao;
	}
	
	
	
}





