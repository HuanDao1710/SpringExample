package com.falcon.view.boomer.core.repository;

import com.falcon.view.boomer.core.domain.entity.TrafficCampaignUrlDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignUrlDetailRepository extends
		JpaRepository<TrafficCampaignUrlDetailEntity, Long> {
}
