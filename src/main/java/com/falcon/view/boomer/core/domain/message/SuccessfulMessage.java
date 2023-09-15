package com.falcon.view.boomer.core.domain.message;

import com.falcon.view.boomer.core.constant.CampaignType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class SuccessfulMessage {
	private String campaignId;
	private CampaignType type;
}
