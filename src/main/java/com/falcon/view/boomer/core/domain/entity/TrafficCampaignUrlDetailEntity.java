package com.falcon.view.boomer.core.domain.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "campaign_url_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrafficCampaignUrlDetailEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "campaign_url_detail_id")
	private Long id;

	@Column(name = "value", nullable = false)
	private String value;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "campaign_url_id", referencedColumnName = "campaign_url_id", nullable = false)
	private TrafficCampaignUrlEntity campaignUrl;
}
