package com.falcon.view.boomer.core.repository;

import com.falcon.view.boomer.core.domain.dto.SearchListCampaignRequest;
import com.falcon.view.boomer.core.domain.dto.TrafficCampaignListDTO;
import com.falcon.view.boomer.core.domain.entity.TrafficCampaignEntity;
import com.falcon.view.boomer.core.domain.entity.TrafficCampaignEntity_;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomCampaignRepository {
	private final EntityManager entityManager;

	public List<TrafficCampaignListDTO> search(SearchListCampaignRequest request, String owner) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<TrafficCampaignListDTO> query = cb.createQuery(TrafficCampaignListDTO.class);
		Root<TrafficCampaignEntity> root = query.from(TrafficCampaignEntity.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(cb.equal(root.get(TrafficCampaignEntity_.OWNER), owner));
		if (StringUtils.isNotBlank(request.getQuery())) {
			String pattern = "%" + request.getQuery().toLowerCase().trim() + "%";
			Predicate predicate = cb.or(
					cb.like(cb.lower(root.get(TrafficCampaignEntity_.ID)), pattern),
					cb.like(root.get(TrafficCampaignEntity_.NAME), pattern)
			);
			predicateList.add(predicate);
		}

		if (!CollectionUtils.isEmpty(request.getStatuses())) {
			Predicate predicate = cb.in(root.get(TrafficCampaignEntity_.STATUS).in(request.getStatuses()));
			predicateList.add(predicate);
		}

		query.where(predicateList.toArray(Predicate[]::new));
		return entityManager.createQuery(query)
				.setFirstResult(request.getPage() * request.getPageSize())
				.setMaxResults(request.getPageSize())
				.getResultList();
	}
}
