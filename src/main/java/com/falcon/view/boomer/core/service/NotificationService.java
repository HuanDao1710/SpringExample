package com.falcon.view.boomer.core.service;

import com.falcon.view.boomer.core.constant.NotificationType;
import com.falcon.view.boomer.core.domain.dto.NotificationDTO;
import com.falcon.view.boomer.core.domain.dto.NotificationRequest;
import com.falcon.view.boomer.core.domain.dto.PageDTO;
import com.falcon.view.boomer.core.domain.entity.SEOCampaignEntity;
import com.falcon.view.boomer.core.domain.entity.TrafficCampaignEntity;

public interface NotificationService {
	void notify(TrafficCampaignEntity entity, NotificationType type);

	void notify(SEOCampaignEntity entity, NotificationType type);

	PageDTO<NotificationDTO> search(NotificationRequest request);

	long countBy(NotificationRequest request);

	void seen(long notificationId);
}
