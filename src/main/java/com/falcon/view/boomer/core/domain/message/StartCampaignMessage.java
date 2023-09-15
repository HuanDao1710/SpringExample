package com.falcon.view.boomer.core.domain.message;

import com.falcon.view.boomer.core.constant.CampaignType;
import com.falcon.view.boomer.core.constant.Duration;
import com.falcon.view.boomer.core.constant.RunType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StartCampaignMessage {
	private String campaignId;
	private RunType runType;
	private Set<String> countryTarget;
	private String owner;
	private Date startDate;
	private Date endDate;
	private Duration durationView;
	private Long expectTotalView;

	private CampaignType type;

	private Map<String, Object> moreParams;
}
