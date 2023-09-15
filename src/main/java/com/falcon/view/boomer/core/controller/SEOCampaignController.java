package com.falcon.view.boomer.core.controller;

import com.falcon.view.boomer.core.domain.dto.*;
import com.falcon.view.boomer.core.service.impl.SEOCampaignService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/campaign/seo")
@RequiredArgsConstructor
public class SEOCampaignController {

	private final SEOCampaignService service;

	@PostMapping
	@PreAuthorize("@securityExpression.hasAnyPermission('CREATE_SEO_CAMPAIGN')")
	public SEOCampaignDTO create(@RequestBody @Validated(Create.class) SEOCampaignDTO campaignDTO) {
		return service.create(campaignDTO);
	}

	@PutMapping
	@PreAuthorize("@securityExpression.hasAnyPermission('UPDATE_SEO_CAMPAIGN')")
	public SEOCampaignDTO update(@RequestBody @Validated(Update.class) SEOCampaignDTO campaignDTO) {
		return service.update(campaignDTO);
	}

	@GetMapping
	@PreAuthorize("@securityExpression.hasAnyPermission('VIEW_SEO_CAMPAIGN')")
	public SEOCampaignDTO get(@RequestParam String campaignId) {
		return service.get(campaignId);
	}

	@DeleteMapping("/{campaignId}")
	@PreAuthorize("@securityExpression.hasAnyPermission('REMOVE_SEO_CAMPAIGN')")
	public ResponseEntity<Object> remove(@PathVariable String campaignId) {
		service.remove(campaignId);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/action/start/{campaignId}")
	@PreAuthorize("@securityExpression.hasAnyPermission('START_SEO_CAMPAIGN')")
	public ResponseEntity<Object> start(@PathVariable String campaignId) throws
			JsonProcessingException {
		service.start(campaignId);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/action/pause/{campaignId}")
	@PreAuthorize("@securityExpression.hasAnyPermission('PAUSE_SEO_CAMPAIGN')")
	public ResponseEntity<Object> pause(@PathVariable String campaignId) throws
			JsonProcessingException {
		service.pause(campaignId);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/action/resume/{campaignId}")
	@PreAuthorize("@securityExpression.hasAnyPermission('RESUME_SEO_CAMPAIGN')")
	public ResponseEntity<Object> resume(@PathVariable String campaignId) throws
			JsonProcessingException {
		service.resume(campaignId);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/action/stop/{campaignId}")
	@PreAuthorize("@securityExpression.hasAnyPermission('STOP_SEO_CAMPAIGN')")
	public ResponseEntity<Object> stop(@PathVariable String campaignId) throws
			JsonProcessingException {
		service.stop(campaignId);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/search")
	@PreAuthorize("@securityExpression.hasAnyPermission('SEARCH_SEO_CAMPAIGN')")
	public PageDTO<SEOCampaignListDTO> search(@Valid SearchListCampaignRequest request) {
		return service.search(request);
	}
}
