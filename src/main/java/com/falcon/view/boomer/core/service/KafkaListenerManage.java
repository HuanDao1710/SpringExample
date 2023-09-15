package com.falcon.view.boomer.core.service;

import com.falcon.view.boomer.core.constant.CampaignStatus;
import com.falcon.view.boomer.core.constant.CampaignType;
import com.falcon.view.boomer.core.constant.Constant;
import com.falcon.view.boomer.core.constant.NotificationType;
import com.falcon.view.boomer.core.domain.entity.CampaignEntity;
import com.falcon.view.boomer.core.domain.entity.SEOCampaignEntity;
import com.falcon.view.boomer.core.domain.entity.TrafficCampaignEntity;
import com.falcon.view.boomer.core.domain.message.SuccessfulMessage;
import com.falcon.view.boomer.core.repository.SEOCampaignRepository;
import com.falcon.view.boomer.core.repository.TrafficCampaignRepository;
import com.falcon.view.boomer.core.utils.CommonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaListenerManage {
	private final TrafficCampaignRepository campaignRepository;
	private final SEOCampaignRepository seoCampaignRepository;
	private final KafkaService kafkaService;
	private final TokenService tokenService;
	private final LoggingService loggingService;
	private final NotificationService notificationService;
	private final ObjectMapper objectMapper;

	@KafkaListener(topics = "${kafka.resume-task.topic}", groupId = "${kafka.resume-task.group}")
	public void handleResumeTask(String campaignId) throws
			JsonProcessingException {
		if (campaignId.startsWith(Constant.EntityID.SEO_ID)) {
			SEOCampaignEntity seoCampaign = seoCampaignRepository.findById(campaignId)
					.orElseThrow();
			long remainView = CommonUtils.extractRemainView(seoCampaign.getStartDate(), seoCampaign.getEndDate(), new Date(), seoCampaign.getExpectTotalView());
			kafkaService.sendStartCampaign(seoCampaign, remainView);
			return;
		}
		TrafficCampaignEntity entity = campaignRepository.findById(campaignId)
				.orElseThrow();
		long remainView = CommonUtils.extractRemainView(entity.getStartDate(), entity.getEndDate(), new Date(), entity.getExpectTotalView());
		kafkaService.sendStartCampaign(entity, remainView);
	}

	@KafkaListener(topics = "${kafka.callback-task.topic}", groupId = "${kafka.callback-task.group}")
	@Transactional
	public void handleCallbackTask(String message) throws
			JsonProcessingException {
		SuccessfulMessage successfulMessage = objectMapper.readValue(message, SuccessfulMessage.class);
		log.info("Success task campaignId {}", successfulMessage.getCampaignId());
		if (CampaignType.SEO.equals(successfulMessage.getType())) {
			seoCampaignRepository.updateStatus(CampaignStatus.COMPLETED, successfulMessage.getCampaignId());
			notificationService.notify(seoCampaignRepository.findById(successfulMessage.getCampaignId()).orElseThrow(), NotificationType.CAMPAIGN_COMPLETED);
			return;
		}
		campaignRepository.updateStatus(CampaignStatus.COMPLETED, successfulMessage.getCampaignId());
		notificationService.notify(campaignRepository.findById(successfulMessage.getCampaignId()).orElseThrow(), NotificationType.CAMPAIGN_COMPLETED);
	}
}
