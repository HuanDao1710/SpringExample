package com.falcon.view.boomer.core.domain.dto;

import com.falcon.view.boomer.core.constant.UserAgent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(callSuper = true)
public class SEOCampaignDTO extends CampaignDTO {
	@NotEmpty(groups = {Update.class, Create.class})
	private List<@NotBlank(groups = {Update.class, Create.class}) String> urls;
	@NotEmpty(groups = {Update.class, Create.class})
	private List<@NotBlank(groups = {Update.class, Create.class}) String> keywords;
	@NotEmpty(groups = {Update.class, Create.class})
	private List<@NotNull(groups = {Update.class, Create.class}) UserAgent> userAgents;
}
