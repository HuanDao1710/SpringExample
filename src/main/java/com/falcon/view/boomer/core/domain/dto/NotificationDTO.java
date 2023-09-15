package com.falcon.view.boomer.core.domain.dto;

import com.falcon.view.boomer.core.constant.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
	private long notificationId;
	private String message;
	private boolean seen;
	private String objectLink;
	private NotificationType type;
	private Date createdDate;
}
