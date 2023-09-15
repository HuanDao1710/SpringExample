package com.falcon.view.boomer.core.auth;

import com.falcon.view.boomer.core.exception.ViewBoomerException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@ConfigurationProperties(prefix = "auth")
@Getter
@Setter
public class AuthProperties {
	private Token accessToken;


	@PostConstruct
	void init() {
		if (accessToken == null) {
			throw new ViewBoomerException("Missing config accessToken or refreshToken");
		}
	}

	@Getter
	@Setter
	public static class Token {
		private String secret;
		private Long expiresIn;
	}
}
