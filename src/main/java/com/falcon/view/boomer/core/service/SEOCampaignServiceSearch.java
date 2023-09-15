package com.falcon.view.boomer.core.service;

import com.falcon.view.boomer.core.domain.dto.PageDTO;
import com.falcon.view.boomer.core.domain.dto.SEOCampaignListDTO;
import com.falcon.view.boomer.core.domain.dto.SearchListCampaignRequest;

public interface SEOCampaignServiceSearch {
	PageDTO<SEOCampaignListDTO> search(SearchListCampaignRequest request);
}
