package com.falcon.view.boomer.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
public class PageDTO<T> {
	private List<T> data;
	private Meta meta;

	@Data
	@AllArgsConstructor(staticName = "of")
	public static class Meta {
		private long total;
		private long page;
		private int pageSize;
	}
}
