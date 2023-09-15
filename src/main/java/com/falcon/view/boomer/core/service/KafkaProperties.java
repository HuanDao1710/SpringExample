package com.falcon.view.boomer.core.service;

import com.falcon.view.boomer.core.exception.ViewBoomerException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@ConfigurationProperties(prefix = "kafka")
@Getter
@Setter
public class KafkaProperties {
	private Kafka startCampaign;
	private Kafka stopCampaign;
	private Kafka resumeTask;
	private Kafka callbackTask;


	@PostConstruct
	void validate() {
		if (startCampaign == null || stopCampaign == null || resumeTask == null || callbackTask == null) {
			throw new ViewBoomerException("Kafka info: startCampaign and stopCampaign must be not null");
		}
	}

	@Getter
	@Setter
	public static class Kafka {
		private String topic;
		private String group;
	}
}
