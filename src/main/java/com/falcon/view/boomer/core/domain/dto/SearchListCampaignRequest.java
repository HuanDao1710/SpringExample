package com.falcon.view.boomer.core.domain.dto;

import com.falcon.view.boomer.core.constant.CampaignStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchListCampaignRequest {
	private String query;
	@Min(0)
	private int page;
	@Min(5)
	private int pageSize;
	private List<CampaignStatus> statuses;
}
