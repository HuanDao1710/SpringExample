package com.falcon.view.boomer.core.mapper;

import com.falcon.view.boomer.core.domain.dto.TrafficCampaignDTO;
import com.falcon.view.boomer.core.domain.dto.TrafficCampaignListDTO;
import com.falcon.view.boomer.core.domain.entity.TrafficCampaignEntity;
import com.falcon.view.boomer.core.domain.entity.TrafficCampaignUrlDetailEntity;
import com.falcon.view.boomer.core.domain.entity.TrafficCampaignUrlEntity;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.*;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TrafficCampaignMapper {

	@Mapping(target = "id", source = "campaignId")
	@Mapping(target = "name", source = "campaignName")
	@Mapping(target = "urls", source = "urls", qualifiedByName = "mapUrls")
	@Mapping(target = "countryTarget", qualifiedByName = "mapCountry")
	void update(@MappingTarget TrafficCampaignEntity campaign, @Context TrafficCampaignEntity campaign2, TrafficCampaignDTO dto);


	@Mapping(source = "id", target = "campaignId")
	@Mapping(source = "name", target = "campaignName")
	@Mapping(source = "urls", target = "urls", qualifiedByName = "mapUrlsString")
	@Mapping(source = "countryTarget", target = "countryTarget", qualifiedByName = "mapCountryTargetString")
	TrafficCampaignDTO toDto(TrafficCampaignEntity campaign);


	@Mapping(source = "id", target = "campaignId")
	@Mapping(source = "name", target = "campaignName")
	TrafficCampaignListDTO toListDTO(TrafficCampaignEntity campaign);

	@Named("mapCountry")
	default String mapCountry(Set<String> countryTarget) {
		if (CollectionUtils.isEmpty(countryTarget)) return null;
		return String.join(",", countryTarget);
	}

	@Named("mapCountryTargetString")
	default Set<String> mapCountryTarget(String country) {
		if (StringUtils.isBlank(country)) return Collections.emptySet();
		return Set.of(country.split(","));
	}

	@Named("mapUrlsString")
	default List<List<String>> mapUrlsString(List<TrafficCampaignUrlEntity> campaignUrlEntities) {
		return campaignUrlEntities.stream()
				.map(i -> i.getUrlDetails().stream()
						.map(TrafficCampaignUrlDetailEntity::getValue)
						.collect(Collectors.toList()))
				.collect(Collectors.toList());
	}

	@Named("mapUrls")
	default List<TrafficCampaignUrlEntity> mapUrls(List<List<String>> urls, @Context TrafficCampaignEntity campaign2) {
		return urls.stream()
				.map(item -> {
					TrafficCampaignUrlEntity campaignUrl = TrafficCampaignUrlEntity.builder().campaign(campaign2).build();
					List<TrafficCampaignUrlDetailEntity> details = item.stream()
							.map(i -> TrafficCampaignUrlDetailEntity.builder().value(i.trim()).campaignUrl(campaignUrl).build())
							.collect(Collectors.toList());
					campaignUrl.setUrlDetails(details);
					return campaignUrl;
				}).collect(Collectors.toList());
	}
}
