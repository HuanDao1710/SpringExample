package com.falcon.view.boomer.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TrafficCampaignDTO extends CampaignDTO {
	@NotEmpty(groups = {Update.class, Create.class})
	private List<@NotNull(groups = {Update.class, Create.class}) List<@NotNull(groups = {Update.class, Create.class}) String>> urls;
}
