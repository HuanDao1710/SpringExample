package com.falcon.view.boomer.core.mapper;

import com.falcon.view.boomer.core.constant.UserAgent;
import com.falcon.view.boomer.core.domain.dto.SEOCampaignDTO;
import com.falcon.view.boomer.core.domain.dto.SEOCampaignListDTO;
import com.falcon.view.boomer.core.domain.entity.SEOCampaignEntity;
import com.falcon.view.boomer.core.domain.entity.SEOCampaignUrlEntity;
import com.falcon.view.boomer.core.domain.entity.SEOKeyWordEntity;
import com.falcon.view.boomer.core.domain.entity.SEOUserAgentEntity;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.*;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SEOCampaignMapper {

	@Mapping(target = "id", source = "campaignId")
	@Mapping(target = "name", source = "campaignName")
	@Mapping(target = "userAgents", source = "userAgents", qualifiedByName = "mapUserAgents")
	@Mapping(target = "urls", source = "urls", qualifiedByName = "mapUrls")
	@Mapping(target = "keywords", source = "keywords", qualifiedByName = "mapKeywords")
	@Mapping(target = "countryTarget", qualifiedByName = "mapCountry")
	void update(@MappingTarget SEOCampaignEntity campaign, @Context SEOCampaignEntity campaign2, SEOCampaignDTO dto);


	@Mapping(source = "id", target = "campaignId")
	@Mapping(source = "name", target = "campaignName")
	@Mapping(target = "userAgents", source = "userAgents", qualifiedByName = "mapUserAgentsDTO")
	@Mapping(target = "urls", source = "urls", qualifiedByName = "mapUrlsDTO")
	@Mapping(target = "keywords", source = "keywords", qualifiedByName = "mapKeywordsDTO")
	@Mapping(source = "countryTarget", target = "countryTarget", qualifiedByName = "mapCountryTargetString")
	SEOCampaignDTO toDto(SEOCampaignEntity campaign);

	@Mapping(source = "id", target = "campaignId")
	@Mapping(source = "name", target = "campaignName")
	@Mapping(source = "keywords", target = "keywords", qualifiedByName = "mapKeywordsDTO")
	SEOCampaignListDTO toListDTO(SEOCampaignEntity campaign);


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

	@Named("mapUrlsDTO")
	default List<String> mapUrlsDTO(List<SEOCampaignUrlEntity> seoCampaignUrlEntities) {
		return seoCampaignUrlEntities.stream()
				.map(SEOCampaignUrlEntity::getUrl).collect(Collectors.toList());
	}

	@Named("mapUserAgentsDTO")
	default List<UserAgent> mapUserAgentDTO(List<SEOUserAgentEntity> seoUserAgentEntities) {
		return seoUserAgentEntities.stream()
				.map(SEOUserAgentEntity::getValue).collect(Collectors.toList());
	}

	@Named("mapKeywordsDTO")
	default List<String> mapKeywordsDTO(List<SEOKeyWordEntity> seoKeyWordEntities) {
		return seoKeyWordEntities.stream()
				.map(SEOKeyWordEntity::getKeyword).collect(Collectors.toList());
	}

	@Named("mapUserAgents")
	default List<SEOUserAgentEntity> mapUserAgent(List<UserAgent> userAgents, @Context SEOCampaignEntity campaign2) {
		return userAgents.stream().map(userAgent -> {
			var entity = new SEOUserAgentEntity();
			entity.setValue(userAgent);
			entity.setSeoCampaign(campaign2);
			return entity;
		}).collect(Collectors.toList());
	}

	@Named("mapUrls")
	default List<SEOCampaignUrlEntity> mapUrls(List<String> urls, @Context SEOCampaignEntity campaign2) {
		return urls.stream().map(url -> {
			var entity = new SEOCampaignUrlEntity();
			entity.setUrl(url.trim());
			entity.setSeoCampaign(campaign2);
			return entity;
		}).collect(Collectors.toList());
	}

	@Named("mapKeywords")
	default List<SEOKeyWordEntity> mapKeyword(List<String> keywords, @Context SEOCampaignEntity campaign2) {
		return keywords.stream().map(key -> {
			var entity = new SEOKeyWordEntity();
			entity.setKeyword(key.trim());
			entity.setSeoCampaign(campaign2);
			return entity;
		}).collect(Collectors.toList());
	}
}
