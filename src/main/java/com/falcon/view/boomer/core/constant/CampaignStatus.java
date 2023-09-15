package com.falcon.view.boomer.core.constant;

import java.util.Set;

public enum CampaignStatus {
	CREATING, STARTED, PAUSED, STOPPED, DELETED, COMPLETED;

	public static final Set<CampaignStatus> STATUS_CAN_REMOVE = Set.of(CREATING, STOPPED, COMPLETED);
	public static final Set<CampaignStatus> STATUS_CAN_STOP = Set.of(PAUSED, STARTED);
}
