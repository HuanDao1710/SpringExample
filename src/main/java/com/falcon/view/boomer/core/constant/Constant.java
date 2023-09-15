package com.falcon.view.boomer.core.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constant {
	public static final long MIN_DURATION = 60 * 1000L; // 1 day
	public static final long MIN_DURATION_RESUME = 60 * 1000L;

	@UtilityClass
	public static class EntityID {
		public static final String TRAFFIC_ID = "campaign";
		public static final String SEO_ID = "seo_campaign";
	}
}
