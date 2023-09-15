package com.falcon.view.boomer.core.domain.dto;

import com.falcon.view.boomer.core.constant.CampaignStatus;
import com.falcon.view.boomer.core.constant.Duration;
import com.falcon.view.boomer.core.constant.RunType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public abstract class CampaignDTO {
	@NotNull(groups = {Update.class})
	@Null(groups = {Create.class})
	private String campaignId;
	private CampaignStatus status;
	@NotBlank(groups = {Update.class, Create.class})
	private String campaignName;

	private Date startDate;
	@NotNull(groups = {Update.class, Create.class})
	@Min(value = 1, groups = {Update.class, Create.class})
	private Integer numberHour;
	private Date endDate;

	@NotNull(groups = {Update.class, Create.class})
	private Set<String> countryTarget;
	@NotNull(groups = {Update.class, Create.class})
	private Long expectTotalView;
	private Duration durationView;
	@NotNull(groups = {Update.class, Create.class})
	private RunType type;
}
