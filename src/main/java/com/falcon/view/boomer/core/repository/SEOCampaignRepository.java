package com.falcon.view.boomer.core.repository;

import com.falcon.view.boomer.core.constant.CampaignStatus;
import com.falcon.view.boomer.core.domain.entity.SEOCampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SEOCampaignRepository extends
		JpaRepository<SEOCampaignEntity, String>,
		JpaSpecificationExecutor<SEOCampaignEntity> {
	@Modifying
	@Query("update SEOCampaignEntity c set c.status = ?1 where c.id = ?2")
	void updateStatus(CampaignStatus status, String campaignId);
}
