package com.artx.artx.common.config;

import com.artx.artx.common.filter.JwtAuthenticationFilter;
import com.artx.artx.common.jwt.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(it -> it.disable());
		http.sessionManagement(it -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(it -> {
//					it.requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll();
//					it.requestMatchers(HttpMethod.POST, "/api/users").permitAll();
//					it.requestMatchers("/api/payments/**").permitAll();
					it.anyRequest().permitAll();
				})
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		http.exceptionHandling(it -> {
			it.authenticationEntryPoint(jwtAuthenticationEntryPoint);
		});


		/**
		 JwtAuthenticationFilter에서 검증되어 SecurityContextHolder의 Context에 인증 정보가 저장되면
		 UsernamePasswordAuthenticationFilter를 통과할 수 있음
		 **/

		return http.build();
	}

}
