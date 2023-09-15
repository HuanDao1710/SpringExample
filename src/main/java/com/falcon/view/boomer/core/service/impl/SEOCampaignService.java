package com.falcon.view.boomer.core.service.impl;

import com.falcon.view.boomer.core.auth.UserContext;
import com.falcon.view.boomer.core.constant.CampaignStatus;
import com.falcon.view.boomer.core.constant.NotificationType;
import com.falcon.view.boomer.core.domain.dto.PageDTO;
import com.falcon.view.boomer.core.domain.dto.SEOCampaignDTO;
import com.falcon.view.boomer.core.domain.dto.SEOCampaignListDTO;
import com.falcon.view.boomer.core.domain.dto.SearchListCampaignRequest;
import com.falcon.view.boomer.core.domain.entity.CampaignEntity_;
import com.falcon.view.boomer.core.domain.entity.SEOCampaignEntity;
import com.falcon.view.boomer.core.domain.entity.SEOCampaignEntity_;
import com.falcon.view.boomer.core.exception.ViewBoomerException;
import com.falcon.view.boomer.core.mapper.SEOCampaignMapper;
import com.falcon.view.boomer.core.repository.CountryRepository;
import com.falcon.view.boomer.core.repository.SEOCampaignRepository;
import com.falcon.view.boomer.core.service.AbstractCampaignFlow;
import com.falcon.view.boomer.core.service.KafkaService;
import com.falcon.view.boomer.core.service.NotificationService;
import com.falcon.view.boomer.core.service.SEOCampaignServiceSearch;
import com.falcon.view.boomer.core.utils.CommonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.function.Predicate;

@Service
public class SEOCampaignService extends
		AbstractCampaignFlow<SEOCampaignDTO, SEOCampaignEntity> implements
		SEOCampaignServiceSearch {
	private final SEOCampaignMapper seoCampaignMapper;
	private final NotificationService notificationService;
	private final KafkaService kafkaService;
	private final SEOCampaignRepository seoCampaignRepository;
	private final UserContext userContext;

	public SEOCampaignService(SEOCampaignRepository repository, CountryRepository countryRepository,
			UserContext userContext, SEOCampaignMapper seoCampaignMapper, NotificationService notificationService, KafkaService kafkaService) {
		super(repository, countryRepository, userContext, SEOCampaignEntity::new, seoCampaignMapper::toDto);
		this.seoCampaignMapper = seoCampaignMapper;
		this.notificationService = notificationService;
		this.kafkaService = kafkaService;
		this.seoCampaignRepository = repository;
		this.userContext = userContext;
	}

	@Override
	public PageDTO<SEOCampaignListDTO> search(SearchListCampaignRequest request) {
		Page<SEOCampaignListDTO> campaignEntities = seoCampaignRepository
				.findAll(createFilter(request, userContext.getUser().getUserId()), PageRequest.of(request.getPage(), request.getPageSize()))
				.map(seoCampaignMapper::toListDTO);

		return PageDTO.of(
				campaignEntities.getContent(),
				PageDTO.Meta.of(campaignEntities.getTotalElements(), campaignEntities.getNumber(), campaignEntities.getSize())
		);
	}

	private Specification<SEOCampaignEntity> createFilter(SearchListCampaignRequest request, String owner) {
		Specification<SEOCampaignEntity> specification = Specification
				.where(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(CampaignEntity_.OWNER), owner)));

		if (StringUtils.hasText(request.getQuery())) {
			String pattern = "%" + request.getQuery().toLowerCase().trim() + "%";
			Specification<SEOCampaignEntity> specification1 = (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(SEOCampaignEntity_.ID)), pattern);
			Specification<SEOCampaignEntity> specification2 = (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(CampaignEntity_.NAME)), pattern);
			specification = specification.and(specification1.or(specification2));
		}

		if (!CollectionUtils.isEmpty(request.getStatuses())) {
			Specification<SEOCampaignEntity> specification1 = (root, query, criteriaBuilder)
					-> root.get(CampaignEntity_.STATUS).in(request.getStatuses());
			specification = specification.and(specification1);
		}

		specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get(CampaignEntity_.STATUS), CampaignStatus.DELETED)));
		return specification;
	}

	@Override
	protected void validatePre(SEOCampaignDTO dto) {
		if (dto.getUrls().stream().anyMatch(Predicate.not(CommonUtils::isValidUrl))) {
			throw new ViewBoomerException("URL Invalid Format");
		}
		if (dto.getUrls().size() > 5) {
			throw new ViewBoomerException("The number of urls must be less than equal 5");
		}
		if (dto.getKeywords().size() > 10) {
			throw new ViewBoomerException("The number of keywords must be less than equal 10");
		}
		if (dto.getCountryTarget().size() > 5) {
			throw new ViewBoomerException("The number of countries must be less than equal 5");
		}
	}

	@Override
	protected void updateEntity(SEOCampaignEntity entity, SEOCampaignDTO dto) {
		seoCampaignMapper.update(entity, entity, dto);
	}

	@Override
	protected void sendStartCampaign(SEOCampaignEntity entity, long view) throws
			JsonProcessingException {
		kafkaService.sendStartCampaign(entity, view);
	}

	@Override
	protected void sendStopCampaign(SEOCampaignEntity entity) throws
			JsonProcessingException {
		kafkaService.sendStopCampaign(entity);
	}

	@Override
	protected void notify(SEOCampaignEntity entity, NotificationType type) {
		notificationService.notify(entity, type);
	}
}
