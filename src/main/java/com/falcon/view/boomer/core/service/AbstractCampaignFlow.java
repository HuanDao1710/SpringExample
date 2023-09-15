package com.falcon.view.boomer.core.service;

import com.falcon.view.boomer.core.auth.UserContext;
import com.falcon.view.boomer.core.constant.CampaignStatus;
import com.falcon.view.boomer.core.constant.Duration;
import com.falcon.view.boomer.core.constant.NotificationType;
import com.falcon.view.boomer.core.constant.RunType;
import com.falcon.view.boomer.core.domain.dto.CampaignDTO;
import com.falcon.view.boomer.core.domain.entity.CampaignEntity;
import com.falcon.view.boomer.core.exception.ViewBoomerException;
import com.falcon.view.boomer.core.repository.CountryRepository;
import com.falcon.view.boomer.core.utils.CommonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

@RequiredArgsConstructor
public abstract class AbstractCampaignFlow<D extends CampaignDTO, E extends CampaignEntity> {
	private final JpaRepository<E, String> repository;
	private final CountryRepository countryRepository;
	private final UserContext userContext;
	private final Supplier<E> createNew;
	private final Function<E, D> mapDTO;
	private final Supplier<ViewBoomerException> notFoundCampaign = this::notFoundCampaign;

	@Transactional
	public D create(D dto) {
		dto.setStatus(CampaignStatus.CREATING);
		dto.setDurationView(Duration.SECOND_60);
		extractCampaignDate(dto);
		validateCampaignDate(dto.getStartDate(), dto.getEndDate());
		validatePre(dto);
		validateCountry(dto.getCountryTarget());
		E entity = createNew.get();
		entity.setOwner(userContext.getUser().getUserId());
		this.updateEntity(entity, dto);
		E resultSave = repository.save(entity);
		return mapDTO.apply(resultSave);
	}

	protected abstract void validatePre(D dto);

	@Transactional
	public D update(D dto) {
		dto.setDurationView(Duration.SECOND_60);
		extractCampaignDate(dto);
		validateCampaignDate(dto.getStartDate(), dto.getEndDate());
		validatePre(dto);
		validateCountry(dto.getCountryTarget());
		E entity = repository.findById(dto.getCampaignId())
				.orElseThrow(notFoundCampaign);
		validateOwner(entity);
		Assert.isTrue(entity.getStatus().equals(dto.getStatus()), "Status is invalid");
		Assert.isTrue(CampaignStatus.CREATING.equals(entity.getStatus()), "Campaign is not allowed update");

		this.updateEntity(entity, dto);
		E resultSave = repository.save(entity);
		return mapDTO.apply(resultSave);
	}

	@Transactional
	public void remove(String campaignId) {
		E entity = repository.findById(campaignId)
				.orElseThrow(notFoundCampaign);
		validateOwner(entity);
		Assert.isTrue(CampaignStatus.STATUS_CAN_REMOVE.contains(entity.getStatus()),
				"Campaign is not allowed delete");
		entity.setStatus(CampaignStatus.DELETED);
		repository.save(entity);
	}

	@Transactional
	public void start(String campaignId) throws JsonProcessingException {
		E entity = repository.findById(campaignId)
				.orElseThrow(notFoundCampaign);
		validateOwner(entity);
		Assert.isTrue(CampaignStatus.CREATING.equals(entity.getStatus()), "Campaign is not allowed start");
		validateCampaignDate(entity.getStartDate(), entity.getEndDate());
		entity.setStatus(CampaignStatus.STARTED);
		repository.save(entity);
		sendStartCampaign(entity, entity.getExpectTotalView());
		notify(entity, NotificationType.CAMPAIGN_STARTED);
	}

	public void pause(String campaignId) throws JsonProcessingException {
		E entity = repository.findById(campaignId)
				.orElseThrow(notFoundCampaign);
		validateOwner(entity);
		Assert.isTrue(CampaignStatus.STARTED.equals(entity.getStatus()), "Campaign is not allowed pause");
		this.sendStopCampaign(entity);
		notify(entity, NotificationType.CAMPAIGN_PAUSED);
		entity.setStatus(CampaignStatus.PAUSED);
		repository.save(entity);
	}

	@Transactional
	public void resume(String campaignId) throws JsonProcessingException {
		E entity = repository.findById(campaignId)
				.orElseThrow(notFoundCampaign);
		validateOwner(entity);
		Assert.isTrue(CampaignStatus.PAUSED.equals(entity.getStatus()), "Campaign is not allowed resume");
		entity.setStatus(CampaignStatus.STARTED);
		repository.save(entity);
		long remainView = CommonUtils.extractRemainView(entity.getStartDate(), entity.getEndDate(), new Date(), entity.getExpectTotalView());
		this.sendStartCampaign(entity, remainView);
		notify(entity, NotificationType.CAMPAIGN_RESUME);
	}

	@Transactional
	public void stop(String campaignId) throws JsonProcessingException {
		E entity = repository.findById(campaignId)
				.orElseThrow(notFoundCampaign);
		validateOwner(entity);
		Assert.isTrue(CampaignStatus.STATUS_CAN_STOP.contains(entity.getStatus()), "Campaign is not allowed stop");

		if (CampaignStatus.STARTED.equals(entity.getStatus())) {
			this.sendStopCampaign(entity);
		}

		entity.setStatus(CampaignStatus.STOPPED);
		repository.save(entity);
	}

	public D get(String campaignId) {
		E entity = repository.findById(campaignId)
				.orElseThrow(notFoundCampaign);
		validateOwner(entity);
		Assert.isTrue(!CampaignStatus.DELETED.equals(entity.getStatus()), "Record is removed");
		return mapDTO.apply(entity);
	}


	protected abstract void updateEntity(E entity, D dto);

	protected abstract void sendStartCampaign(E entity, long view) throws
			JsonProcessingException;

	protected abstract void sendStopCampaign(E entity) throws
			JsonProcessingException;

	protected abstract void notify(E entity, NotificationType type);


	private ViewBoomerException notFoundCampaign() {
		return new ViewBoomerException("Campaign is not found");
	}

	private void validateCountry(Set<String> country) {
		if (CollectionUtils.isEmpty(country)) return;
		long current = countryRepository.countByShortNameIn(country);
		Assert.isTrue(country.size() == current, "Country Invalid");
	}

	private void validateOwner(E entity) {
		Assert.isTrue(entity.getOwner().equals(userContext.getUser().getUserId()), "You are not allowed operation with this campaign");
	}

	private void validateCampaignDate(Date startDate, Date endDate) {
		Assert.notNull(startDate, "Start date must be not null");
		Assert.notNull(endDate, "End Date must be not null");
		Assert.isTrue(System.currentTimeMillis() - startDate.getTime() <= 30000, "Start date must be after current time");
	}

	private void extractCampaignDate(D dto) {
		if (RunType.NOW.equals(dto.getType())) {
			dto.setStartDate(new Date());
		}
		dto.setEndDate(Date.from(dto.getStartDate().toInstant().plus(dto.getNumberHour(), ChronoUnit.HOURS)));
	}
}
