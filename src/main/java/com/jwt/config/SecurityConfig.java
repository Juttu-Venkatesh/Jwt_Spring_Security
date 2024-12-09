package com.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jwt.filter.JwtAuthenticationFilter;
import com.jwt.service.UserDetailsImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final UserDetailsImpl userDetailsService;
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	
	public SecurityConfig(UserDetailsImpl userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.userDetailsService = userDetailsService;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(
						req -> req.requestMatchers("/login/**","/register/**")
						.permitAll()
						.requestMatchers("/user/**").hasAnyAuthority("USER","ADMIN")
						.requestMatchers("/admin/**").hasAuthority("ADMIN")
						.anyRequest()
						.authenticated()
			).userDetailsService(userDetailsService)
			 .sessionManagement(session -> session
					 .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			 .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			 .build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
}
