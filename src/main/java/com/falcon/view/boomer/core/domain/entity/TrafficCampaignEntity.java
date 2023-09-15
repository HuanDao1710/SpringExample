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
@Table(name = "campaign")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrafficCampaignEntity extends CampaignEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "campaign_id")
	@GenericGenerator(
			name = "campaign_id",
			strategy = "com.falcon.view.boomer.core.repository.GenericIdGenerator",
			parameters = {
					@org.hibernate.annotations.Parameter(name = "prefix", value = Constant.EntityID.TRAFFIC_ID),
					@org.hibernate.annotations.Parameter(name = "sequence", value = "campaign_id_sequence"),
					@org.hibernate.annotations.Parameter(name = "number_character", value = "10")
			}
	)
	@Column(name = "campaign_id")
	private String id;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "campaign")
	private List<TrafficCampaignUrlEntity> urls;
}
