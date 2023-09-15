package com.falcon.view.boomer.core.domain.entity;

import com.falcon.view.boomer.core.constant.CampaignStatus;
import com.falcon.view.boomer.core.constant.Duration;
import com.falcon.view.boomer.core.constant.RunType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class CampaignEntity extends BaseEntity {
	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private CampaignStatus status;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "start_date", nullable = false)
	private Date startDate;
	@Column(name = "end_date", nullable = false)
	private Date endDate;
	@Column(name = "expect_total_view", nullable = false)
	private Long expectTotalView;
	@Column(name = "duration_view", nullable = false)
	@Enumerated(EnumType.STRING)
	private Duration durationView;
	@Column(name = "owner", nullable = false)
	private String owner;
	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
	private RunType type;
	@Column(name = "country_target")
	private String countryTarget;
	@Column(name = "number_hour")
	private Integer numberHour;
}
