package com.falcon.view.boomer.core.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "seo_keyword")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SEOKeyWordEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "keyword", nullable = false)
	private String keyword;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seo_campaign_id", referencedColumnName = "seo_campaign_id", nullable = false)
	private SEOCampaignEntity seoCampaign;

}
