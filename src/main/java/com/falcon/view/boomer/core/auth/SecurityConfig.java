package com.falcon.view.boomer.core.auth;

import com.falcon.view.boomer.core.auth.jwt.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	private final UnsuccessfulAuthenticationHandler unsuccessfulAuthenticationHandler;
	private final AuthenticationEntryPoint authenticationEntryPoint;
	@Resource(name = "userContext")
	private UserContext userContext;

	@Bean
	CorsConfigurationSource corsConfiguration() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(List.of("*"));
		corsConfiguration.setAllowedMethods(List.of(HttpMethod.GET.name(),
				HttpMethod.POST.name(),
				HttpMethod.PUT.name(),
				HttpMethod.DELETE.name(),
				HttpMethod.OPTIONS.name()));
		corsConfiguration.setAllowedHeaders(List.of("*"));

		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return urlBasedCorsConfigurationSource;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http,
			AuthenticationManager authenticationManager) throws
			Exception {
		http.csrf().disable();
		http.cors().configurationSource(corsConfiguration());
		http.authorizeRequests()
//				.antMatchers("/contactMessage").permitAll()
				.anyRequest().permitAll();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(new JWTAuthenticationFilter(authenticationManager, userContext, unsuccessfulAuthenticationHandler), UsernamePasswordAuthenticationFilter.class);
		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
		return http.build();
	}
}
