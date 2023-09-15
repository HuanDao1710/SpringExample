package com.falcon.view.boomer.core.service;

import com.falcon.view.boomer.core.constant.CampaignType;
import com.falcon.view.boomer.core.domain.dto.CampaignDTO;
import com.falcon.view.boomer.core.domain.dto.SEOCampaignDTO;
import com.falcon.view.boomer.core.domain.dto.TrafficCampaignDTO;
import com.falcon.view.boomer.core.domain.entity.SEOCampaignEntity;
import com.falcon.view.boomer.core.domain.entity.TrafficCampaignEntity;
import com.falcon.view.boomer.core.domain.message.StartCampaignMessage;
import com.falcon.view.boomer.core.domain.message.StopCampaignMessage;
import com.falcon.view.boomer.core.mapper.SEOCampaignMapper;
import com.falcon.view.boomer.core.mapper.TrafficCampaignMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KafkaService {
	private final ObjectMapper objectMapper;
	private final TrafficCampaignMapper campaignMapper;
	private final SEOCampaignMapper seoCampaignMapper;
	private final KafkaTemplate<String, Object> kafkaTemplate;
	private final KafkaProperties kafkaProperties;

	public void sendStartCampaign(TrafficCampaignEntity entity, long expectTotalView) throws
			JsonProcessingException {
		TrafficCampaignDTO campaignDTO = campaignMapper.toDto(entity);
		Map<String, Object> more = Map.of("urls", campaignDTO.getUrls());
		StartCampaignMessage startCampaignMessage = buildStartMessage(campaignDTO, expectTotalView, entity.getOwner(), CampaignType.TRAFFIC, more);

		String message = objectMapper.writeValueAsString(startCampaignMessage);
		kafkaTemplate.send(kafkaProperties.getStartCampaign().getTopic(), message);
	}

	public StartCampaignMessage buildStartMessage(CampaignDTO campaignDTO, long expectTotalView, String owner, CampaignType type, Map<String, Object> more) {
		return StartCampaignMessage
				.builder()
				.campaignId(campaignDTO.getCampaignId())
				.startDate(campaignDTO.getStartDate())
				.endDate(campaignDTO.getEndDate())
				.durationView(campaignDTO.getDurationView())
				.expectTotalView(expectTotalView)
				.owner(owner)
				.runType(campaignDTO.getType())
				.countryTarget(campaignDTO.getCountryTarget())
				.type(type)
				.moreParams(more)
				.build();
	}

	public void sendStopCampaign(TrafficCampaignEntity campaign) throws
			JsonProcessingException {
		StopCampaignMessage stopCampaignMessage = StopCampaignMessage
				.builder()
				.type(CampaignType.TRAFFIC)
				.campaignId(campaign.getId())
				.build();
		String message = objectMapper.writeValueAsString(stopCampaignMessage);
		kafkaTemplate.send(kafkaProperties.getStopCampaign().getTopic(), message);
	}

	public void sendStartCampaign(SEOCampaignEntity entity, long expectTotalView) throws
			JsonProcessingException {
		SEOCampaignDTO campaignDTO = seoCampaignMapper.toDto(entity);
		Map<String, Object> more = Map.of("urls", campaignDTO.getUrls(), "keywords", campaignDTO.getKeywords(), "userAgents", campaignDTO.getUserAgents());
		StartCampaignMessage startCampaignMessage = buildStartMessage(campaignDTO, expectTotalView, entity.getOwner(), CampaignType.SEO, more);

		String message = objectMapper.writeValueAsString(startCampaignMessage);
		kafkaTemplate.send(kafkaProperties.getStartCampaign().getTopic(), message);
	}

	public void sendStopCampaign(SEOCampaignEntity campaign) throws
			JsonProcessingException {
		StopCampaignMessage stopCampaignMessage = StopCampaignMessage
				.builder()
				.type(CampaignType.SEO)
				.campaignId(campaign.getId())
				.build();
		String message = objectMapper.writeValueAsString(stopCampaignMessage);
		kafkaTemplate.send(kafkaProperties.getStopCampaign().getTopic(), message);
	}
}
