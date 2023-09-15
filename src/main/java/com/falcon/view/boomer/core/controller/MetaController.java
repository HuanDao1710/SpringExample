package com.falcon.view.boomer.core.controller;

import com.falcon.view.boomer.core.domain.dto.CountryDTO;
import com.falcon.view.boomer.core.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MetaController {
	private final CountryService countryService;

	@GetMapping("/country")
	public List<CountryDTO> getCountry() {
		return countryService.getCountry();
	}
}
