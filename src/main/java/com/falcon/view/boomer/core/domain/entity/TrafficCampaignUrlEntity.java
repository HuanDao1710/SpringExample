package com.falcon.view.boomer.core.domain.entity;


import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "campaign_url")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrafficCampaignUrlEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "campaign_url_id")
	private Long id;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "campaignUrl")
	private List<TrafficCampaignUrlDetailEntity> urlDetails;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "campaign_id", referencedColumnName = "campaign_id", nullable = false)
	private TrafficCampaignEntity campaign;
}
