package com.falcon.view.boomer.core.auth;

import com.falcon.view.boomer.core.auth.jwt.JWTAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class AuthenticationConfig {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration,
			AuthenticationManagerBuilder authenticationManagerBuilder, JWTHelper jwtHelper) throws
			Exception {
		authenticationManagerBuilder.authenticationProvider(new JWTAuthenticationProvider(jwtHelper));
		return authenticationConfiguration.getAuthenticationManager();
	}


	@Bean(value = "userContext")
	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	UserContext userContext() {
		return new UserContext();
	}
}
