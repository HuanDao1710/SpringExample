package com.falcon.view.boomer.core.domain.entity;

import com.falcon.view.boomer.core.constant.NotificationType;
import lombok.*;

import javax.persistence.*;

@Table(name = "notification")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class 	NotificationEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "message", updatable = false, nullable = false)
	private String message;
	@Column(name = "seen", nullable = false)
	private boolean seen;
	@Column(name = "object_link", updatable = false)
	private String objectLink;
	@Column(name = "type", nullable = false, updatable = false)
	@Enumerated(value = EnumType.STRING)
	private NotificationType type;
	@Column(name = "owner", nullable = false, updatable = false)
	private String owner;
}
