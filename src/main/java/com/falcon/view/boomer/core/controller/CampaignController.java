package com.falcon.view.boomer.core.controller;

import com.falcon.view.boomer.core.domain.dto.*;
import com.falcon.view.boomer.core.service.impl.TrafficCampaignService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/v1/campaign")
@RequiredArgsConstructor
public class CampaignController {
	private final TrafficCampaignService campaignService;

	@PostMapping
	@PreAuthorize("@securityExpression.hasAnyPermission('CREATE_CAMPAIGN')")
	public CampaignDTO create(@RequestBody @Validated(Create.class) TrafficCampaignDTO campaignDTO) {
		return campaignService.create(campaignDTO);
	}

	@PutMapping
	@PreAuthorize("@securityExpression.hasAnyPermission('UPDATE_CAMPAIGN')")
	public CampaignDTO update(@RequestBody @Validated(Update.class) TrafficCampaignDTO campaignDTO) {
		return campaignService.update(campaignDTO);
	}

	@GetMapping
	@PreAuthorize("@securityExpression.hasAnyPermission('VIEW_CAMPAIGN')")
	public CampaignDTO get(@RequestParam String campaignId) {
		return campaignService.get(campaignId);
	}

	@GetMapping("/search")
	@PreAuthorize("@securityExpression.hasAnyPermission('SEARCH_CAMPAIGN')")
	public PageDTO<TrafficCampaignListDTO> search(@Valid SearchListCampaignRequest request) {
		return campaignService.search(request);
	}

	@GetMapping("/search/campaignId")
	@PreAuthorize("@securityExpression.hasAnyPermission('SEARCH_CAMPAIGN')")
	public Set<CampaignLabelDTO> searchAllCampaignId() {
		return campaignService.findAllCampaignId();
	}

	@GetMapping("/search/urls")
	@PreAuthorize("@securityExpression.hasAnyPermission('SEARCH_CAMPAIGN')")
	public Set<String> searchAllUrls() {
		return campaignService.findAllUrl();
	}

	@DeleteMapping("/{campaignId}")
	@PreAuthorize("@securityExpression.hasAnyPermission('REMOVE_CAMPAIGN')")
	public ResponseEntity<Object> remove(@PathVariable String campaignId) {
		campaignService.remove(campaignId);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/action/start/{campaignId}")
	@PreAuthorize("@securityExpression.hasAnyPermission('START_CAMPAIGN')")
	public ResponseEntity<Object> start(@PathVariable String campaignId) throws
			JsonProcessingException {
		campaignService.start(campaignId);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/action/pause/{campaignId}")
	@PreAuthorize("@securityExpression.hasAnyPermission('PAUSE_CAMPAIGN')")
	public ResponseEntity<Object> pause(@PathVariable String campaignId) throws
			JsonProcessingException {
		campaignService.pause(campaignId);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/action/resume/{campaignId}")
	@PreAuthorize("@securityExpression.hasAnyPermission('RESUME_CAMPAIGN')")
	public ResponseEntity<Object> resume(@PathVariable String campaignId) throws
			JsonProcessingException {
		campaignService.resume(campaignId);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/action/stop/{campaignId}")
//	@PreAuthorize("@securityExpression.hasAnyPermission('STOP_CAMPAIGN')")
	public ResponseEntity<Object> stop(@PathVariable String campaignId, HttpServletRequest request) throws
			JsonProcessingException {
//		campaignService.stop(campaignId);
		return ResponseEntity.ok(campaignId);
	}
}
