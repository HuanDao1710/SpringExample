package com.falcon.view.boomer.core.repository;

import com.falcon.view.boomer.core.constant.CampaignStatus;
import com.falcon.view.boomer.core.domain.dto.CampaignLabelDTO;
import com.falcon.view.boomer.core.domain.entity.CampaignEntity;
import com.falcon.view.boomer.core.domain.entity.TrafficCampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface TrafficCampaignRepository extends
		JpaRepository<TrafficCampaignEntity, String>,
		JpaSpecificationExecutor<TrafficCampaignEntity> {
	List<CampaignEntity> findByIdIn(List<String> ids);

	@Modifying
	@Query("update TrafficCampaignEntity c set c.status = ?1 where c.id = ?2")
	void updateStatus(CampaignStatus status, String campaignId);

	@Modifying
	@Query("update TrafficCampaignEntity c set c.status = ?1 where c.id = ?2")
	@Transactional(propagation = Propagation.NEVER)
	void updateStatusNonTransaction(CampaignStatus status, String campaignId);

	@Query("select new com.falcon.view.boomer.core.domain.dto.CampaignLabelDTO(c.id, c.name) from TrafficCampaignEntity c where c.status <> ?1 and c.owner = ?2")
	Set<CampaignLabelDTO> findAllCampaignIds(CampaignStatus exclude, String owner);

	@Query(value = "select distinct ud.value from (select * from campaign ce where ce.status <> ?1 and ce.owner = ?2) c " +
			"inner join campaign_url u on c.campaign_id = u.campaign_id " +
			"inner join campaign_url_detail ud on u.campaign_url_id = ud.campaign_url_id", nativeQuery = true)
	Set<String> findAllUrl(String exclude, String owner);
}
