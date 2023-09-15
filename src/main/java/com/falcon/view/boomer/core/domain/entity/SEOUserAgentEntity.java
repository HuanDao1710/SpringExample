package com.falcon.view.boomer.core.domain.entity;

import com.falcon.view.boomer.core.constant.UserAgent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "seo_user_agent")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SEOUserAgentEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "value")
	@Enumerated(EnumType.STRING)
	private UserAgent value;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seo_campaign_id", referencedColumnName = "seo_campaign_id", nullable = false)
	private SEOCampaignEntity seoCampaign;
}
