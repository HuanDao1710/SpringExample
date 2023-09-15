package com.falcon.view.boomer.core.service.impl;

import com.falcon.view.boomer.core.domain.dto.CountryDTO;
import com.falcon.view.boomer.core.domain.entity.CountryEntity;
import com.falcon.view.boomer.core.repository.CountryRepository;
import com.falcon.view.boomer.core.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
	private final CountryRepository countryRepository;

	@Override
	public List<CountryDTO> getCountry() {
		List<CountryEntity> countries = countryRepository.findAllOrderByNamelowerCaseAsc();
		return countries
				.stream()
				.map(entity -> CountryDTO.of(entity.getName(), entity.getShortName()))
				.collect(Collectors.toList());
	}
}
