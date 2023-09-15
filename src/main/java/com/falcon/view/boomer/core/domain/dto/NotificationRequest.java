package com.falcon.view.boomer.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class NotificationRequest {
	private Boolean seen;
	private String query;
	@Min(0)
	private int page;
	@Min(5)
	private int pageSize;
}
