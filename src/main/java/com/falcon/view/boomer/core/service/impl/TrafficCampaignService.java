package com.falcon.view.boomer.core.service.impl;

import com.falcon.view.boomer.core.auth.UserContext;
import com.falcon.view.boomer.core.constant.CampaignStatus;
import com.falcon.view.boomer.core.constant.NotificationType;
import com.falcon.view.boomer.core.domain.dto.*;
import com.falcon.view.boomer.core.domain.entity.CampaignEntity_;
import com.falcon.view.boomer.core.domain.entity.TrafficCampaignEntity;
import com.falcon.view.boomer.core.domain.entity.TrafficCampaignEntity_;
import com.falcon.view.boomer.core.mapper.TrafficCampaignMapper;
import com.falcon.view.boomer.core.repository.CountryRepository;
import com.falcon.view.boomer.core.repository.TrafficCampaignRepository;
import com.falcon.view.boomer.core.service.AbstractCampaignFlow;
import com.falcon.view.boomer.core.service.KafkaService;
import com.falcon.view.boomer.core.service.NotificationService;
import com.falcon.view.boomer.core.service.TrafficCampaignServiceSearch;
import com.falcon.view.boomer.core.utils.CommonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Set;

@Service
public class TrafficCampaignService extends
		AbstractCampaignFlow<TrafficCampaignDTO, TrafficCampaignEntity> implements
		TrafficCampaignServiceSearch {
	private final TrafficCampaignRepository campaignRepository;
	private final TrafficCampaignMapper trafficCampaignMapper;
	private final KafkaService kafkaService;
	private final NotificationService notificationService;
	private final UserContext userContext;

	public TrafficCampaignService(TrafficCampaignRepository repository, CountryRepository countryRepository,
			UserContext userContext, TrafficCampaignMapper trafficCampaignMapper,
			KafkaService kafkaService, NotificationService notificationService) {
		super(repository, countryRepository, userContext, TrafficCampaignEntity::new, trafficCampaignMapper::toDto);
		this.campaignRepository = repository;
		this.trafficCampaignMapper = trafficCampaignMapper;
		this.kafkaService = kafkaService;
		this.notificationService = notificationService;
		this.userContext = userContext;
	}

	@Override
	protected void validatePre(TrafficCampaignDTO dto) {
		validateUrls(dto);
	}

	@Override
	protected void updateEntity(TrafficCampaignEntity entity, TrafficCampaignDTO dto) {
		trafficCampaignMapper.update(entity, entity, dto);
	}

	@Override
	protected void sendStartCampaign(TrafficCampaignEntity entity, long view) throws
			JsonProcessingException {
		kafkaService.sendStartCampaign(entity, view);
	}

	@Override
	protected void sendStopCampaign(TrafficCampaignEntity entity) throws
			JsonProcessingException {
		kafkaService.sendStopCampaign(entity);
	}

	@Override
	protected void notify(TrafficCampaignEntity entity, NotificationType type) {
		notificationService.notify(entity, type);
	}

	@Override
	public PageDTO<TrafficCampaignListDTO> search(SearchListCampaignRequest request) {
		Page<TrafficCampaignListDTO> campaignEntities = campaignRepository
				.findAll(createFilter(request, userContext.getUser().getUserId()), PageRequest.of(request.getPage(), request.getPageSize()))
				.map(trafficCampaignMapper::toListDTO);

		return PageDTO.of(
				campaignEntities.getContent(),
				PageDTO.Meta.of(campaignEntities.getTotalElements(), campaignEntities.getNumber(), campaignEntities.getSize())
		);
	}

	@Override
	public Set<CampaignLabelDTO> findAllCampaignId() {
		return campaignRepository.findAllCampaignIds(CampaignStatus.DELETED, userContext.getUser().getUserId());
	}

	@Override
	public Set<String> findAllUrl() {
		return campaignRepository.findAllUrl(CampaignStatus.DELETED.name(), userContext.getUser().getUserId());
	}

	private Specification<TrafficCampaignEntity> createFilter(SearchListCampaignRequest request, String owner) {
		Specification<TrafficCampaignEntity> specification = Specification
				.where(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(CampaignEntity_.OWNER), owner)));

		if (StringUtils.hasText(request.getQuery())) {
			String pattern = "%" + request.getQuery().toLowerCase().trim() + "%";
			Specification<TrafficCampaignEntity> specification1 = (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(TrafficCampaignEntity_.ID)), pattern);
			Specification<TrafficCampaignEntity> specification2 = (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(CampaignEntity_.NAME)), pattern);
			specification = specification.and(specification1.or(specification2));
		}

		if (!CollectionUtils.isEmpty(request.getStatuses())) {
			Specification<TrafficCampaignEntity> specification1 = (root, query, criteriaBuilder)
					-> root.get(CampaignEntity_.STATUS).in(request.getStatuses());
			specification = specification.and(specification1);
		}

		specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get(CampaignEntity_.STATUS), CampaignStatus.DELETED)));
		return specification;
	}

	private void validateUrls(TrafficCampaignDTO dto) {
		Assert.isTrue(dto.getUrls().size() <= 10, "Urls Level must to less than equal 10");
		int firstEmpty = -1;
		int lastNonEmpty = -1;
		for (int i = 0; i < dto.getUrls().size(); i++) {
			if (i == 0 && dto.getUrls().get(i).size() > 1) {
				throw new IllegalArgumentException("Size main url must to equal 1");
			}
			if (i > 0 && dto.getUrls().get(i).size() > 5) {
				throw new IllegalArgumentException("Size urls must to less than equal 5");
			}
			if (!CollectionUtils.isEmpty(dto.getUrls().get(i))) {
				lastNonEmpty = i;
			}
			if (CollectionUtils.isEmpty(dto.getUrls().get(i)) && firstEmpty == -1) {
				firstEmpty = i;
			}
			if (firstEmpty != -1 && lastNonEmpty > firstEmpty) {
				throw new IllegalArgumentException("Can not exists empty list between nonempty lists");
			}

			if (dto.getUrls().get(i).stream().anyMatch(item -> !CommonUtils.isValidUrl(item))) {
				throw new IllegalArgumentException("URL Invalid");
			}
		}
	}
}
