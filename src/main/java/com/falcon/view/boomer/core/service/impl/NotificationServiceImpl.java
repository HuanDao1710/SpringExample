package com.falcon.view.boomer.core.service.impl;

import com.falcon.view.boomer.core.auth.UserContext;
import com.falcon.view.boomer.core.constant.NotificationType;
import com.falcon.view.boomer.core.domain.dto.NotificationDTO;
import com.falcon.view.boomer.core.domain.dto.NotificationRequest;
import com.falcon.view.boomer.core.domain.dto.PageDTO;
import com.falcon.view.boomer.core.domain.entity.NotificationEntity;
import com.falcon.view.boomer.core.domain.entity.NotificationEntity_;
import com.falcon.view.boomer.core.domain.entity.SEOCampaignEntity;
import com.falcon.view.boomer.core.domain.entity.TrafficCampaignEntity;
import com.falcon.view.boomer.core.mapper.NotificationMapper;
import com.falcon.view.boomer.core.repository.NotificationRepository;
import com.falcon.view.boomer.core.service.LoggingService;
import com.falcon.view.boomer.core.service.NotificationService;
import com.falcon.view.boomer.core.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
	private final NotificationRepository notificationRepository;
	private final TokenService tokenService;
	private final LoggingService loggingService;
	private final NotificationMapper notificationMapper;

	@Resource(name = "userContext")
	private UserContext userContext;

	@Override
	public void notify(TrafficCampaignEntity entity, NotificationType type) {
		String message;
		switch (type) {
			case CAMPAIGN_COMPLETED:
				var token = tokenService.login().getAccessToken();
				long view = loggingService.countView(entity.getId(), null, token);
				message = String.format("Campaign with id %s is completed. This is obtain %s view", entity.getId(), view);
				break;
			case CAMPAIGN_PAUSED:
				message = String.format("Campaign with id %s is paused.", entity.getId());
				break;
			case CAMPAIGN_STARTED:
				message = String.format("Campaign with id %s is started.", entity.getId());
				break;
			case CAMPAIGN_RESUME:
				message = String.format("Campaign with id %s is resume.", entity.getId());
				break;
			default:
				return;
		}

		notificationRepository.save(NotificationEntity.builder()
				.objectLink(entity.getId())
				.message(message)
				.owner(entity.getOwner())
				.seen(false)
				.type(type)
				.build());
	}

	@Override
	public void notify(SEOCampaignEntity entity, NotificationType type) {
		String message;
		switch (type) {
			case CAMPAIGN_COMPLETED:
				var token = tokenService.login().getAccessToken();
				long view = loggingService.countView(entity.getId(), null, token);
				message = String.format("SEO Campaign with id %s is completed. This is obtain %s the number of seo", entity.getId(), view);
				break;
			case CAMPAIGN_PAUSED:
				message = String.format("SEO Campaign with id %s is paused.", entity.getId());
				break;
			case CAMPAIGN_STARTED:
				message = String.format("SEO Campaign with id %s is started.", entity.getId());
				break;
			case CAMPAIGN_RESUME:
				message = String.format("SEO Campaign with id %s is resume.", entity.getId());
				break;
			default:
				return;
		}

		notificationRepository.save(NotificationEntity.builder()
				.objectLink(entity.getId())
				.message(message)
				.owner(entity.getOwner())
				.seen(false)
				.type(type)
				.build());
	}

	@Override
	public PageDTO<NotificationDTO> search(NotificationRequest request) {
		Page<NotificationDTO> entityList = notificationRepository.findAll(createFilter(request), PageRequest.of(request.getPage(), request.getPageSize(), Sort.by("createdDate").descending()))
				.map(notificationMapper::toDTO);
		return PageDTO.of(entityList.getContent(), PageDTO.Meta.of(entityList.getTotalElements(), request.getPage(), request.getPageSize()));
	}

	private Specification<NotificationEntity> createFilter(NotificationRequest request) {
		Specification<NotificationEntity> specification = ((root, query, criteriaBuilder) ->
				criteriaBuilder.equal(root.get(NotificationEntity_.OWNER), userContext.getUser().getUserId()));

		if (StringUtils.isNotBlank(request.getQuery())) {
			specification = specification.and(((root, query, criteriaBuilder) ->
					criteriaBuilder.like(criteriaBuilder.lower(root.get(NotificationEntity_.MESSAGE)), "%" + request.getQuery().toLowerCase().trim() + "%")
			));
		}

		if (Objects.nonNull(request.getSeen())) {
			specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(NotificationEntity_.seen), request.getSeen())));
		}
		return specification;
	}

	@Override
	public long countBy(NotificationRequest request) {
		return notificationRepository.count(createFilter(request));
	}

	@Override
	@Transactional
	public void seen(long notificationId) {
		notificationRepository.updateSeen(notificationId, userContext.getUser().getUserId());
	}
}
