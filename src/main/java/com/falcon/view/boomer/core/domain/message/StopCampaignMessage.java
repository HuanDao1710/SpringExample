package com.falcon.view.boomer.core.domain.message;

import com.falcon.view.boomer.core.constant.CampaignType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StopCampaignMessage {
	private CampaignType type;
	private String campaignId;
}
