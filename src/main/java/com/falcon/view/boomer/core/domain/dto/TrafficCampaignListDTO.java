package com.falcon.view.boomer.core.domain.dto;

import com.falcon.view.boomer.core.constant.CampaignStatus;
import com.falcon.view.boomer.core.constant.RunType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrafficCampaignListDTO {
	private String campaignId;
	private String campaignName;
	private CampaignStatus status;
	private long expectTotalView;
	private Date startDate;
	private Date endDate;
	private RunType type;
}
