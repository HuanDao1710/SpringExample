package com.falcon.view.boomer.core.service;

import com.falcon.view.boomer.core.domain.dto.CampaignLabelDTO;
import com.falcon.view.boomer.core.domain.dto.PageDTO;
import com.falcon.view.boomer.core.domain.dto.SearchListCampaignRequest;
import com.falcon.view.boomer.core.domain.dto.TrafficCampaignListDTO;

import java.util.Set;

public interface TrafficCampaignServiceSearch {
	PageDTO<TrafficCampaignListDTO> search(SearchListCampaignRequest request);

	Set<CampaignLabelDTO> findAllCampaignId();

	Set<String> findAllUrl();
}
