package com.falcon.view.boomer.core.domain.dto;

import com.falcon.view.boomer.core.constant.CampaignStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SEOCampaignListDTO {
	private String campaignId;
	private String campaignName;
	private CampaignStatus status;
	private long expectTotalView;
	private Date startDate;
	private Date endDate;
	private List<String> keywords;
}
