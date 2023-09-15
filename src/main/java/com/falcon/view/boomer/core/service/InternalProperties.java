package com.falcon.view.boomer.core.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@ConfigurationProperties(prefix = "internal")
@Getter
@Setter
public class InternalProperties {
	private String countViewUri;
	private String loginUri;
	private String username;
	private String password;

	@PostConstruct
	void validate() {
		if (countViewUri == null || loginUri == null) {
			throw new IllegalArgumentException("Internal properties invalid");
		}
	}
}
