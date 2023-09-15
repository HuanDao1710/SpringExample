package com.falcon.view.boomer.core.domain.entity;

import com.falcon.view.boomer.core.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "seo_campaign")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SEOCampaignEntity extends CampaignEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seo_campaign_id")
	@GenericGenerator(
			name = "seo_campaign_id",
			strategy = "com.falcon.view.boomer.core.repository.GenericIdGenerator",
			parameters = {
					@org.hibernate.annotations.Parameter(name = "prefix", value = Constant.EntityID.SEO_ID),
					@org.hibernate.annotations.Parameter(name = "sequence", value = "seo_campaign_id_sequence"),
					@org.hibernate.annotations.Parameter(name = "number_character", value = "10")
			}
	)
	@Column(name = "seo_campaign_id")
	private String id;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "seoCampaign")
	private List<SEOUserAgentEntity> userAgents;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(targetEntity = SEOCampaignUrlEntity.class, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "seoCampaign")
	private List<SEOCampaignUrlEntity> urls;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(targetEntity = SEOKeyWordEntity.class, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "seoCampaign")
	private List<SEOKeyWordEntity> keywords;
}
