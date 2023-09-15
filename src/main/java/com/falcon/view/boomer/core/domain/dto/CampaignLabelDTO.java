package com.falcon.view.boomer.core.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CampaignLabelDTO {
	private String id;
	private String name;

	public CampaignLabelDTO(String id, String name) {
		this.id = id;
		this.name = name;
	}
}
