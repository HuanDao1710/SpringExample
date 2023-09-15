package com.falcon.view.boomer.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface CampaignFlow<D> {
	D create(D dto);

	D update(D dto);

	void remove(String campaignId);

	void start(String campaignId) throws JsonProcessingException;

	void pause(String campaignId) throws JsonProcessingException;

	void resume(String campaignId) throws JsonProcessingException;

	void stop(String campaignId) throws JsonProcessingException;

	D get(String campaignId);
}
