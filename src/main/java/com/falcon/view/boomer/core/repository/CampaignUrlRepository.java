package com.falcon.view.boomer.core.repository;

import com.falcon.view.boomer.core.domain.entity.TrafficCampaignUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignUrlRepository extends
		JpaRepository<TrafficCampaignUrlEntity, Long> {
}
