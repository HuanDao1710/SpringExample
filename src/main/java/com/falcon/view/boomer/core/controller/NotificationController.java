package com.falcon.view.boomer.core.controller;

import com.falcon.view.boomer.core.domain.dto.NotificationDTO;
import com.falcon.view.boomer.core.domain.dto.NotificationRequest;
import com.falcon.view.boomer.core.domain.dto.PageDTO;
import com.falcon.view.boomer.core.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {
	private final NotificationService notificationService;


	@GetMapping
	public PageDTO<NotificationDTO> search(@Valid NotificationRequest request) {
		return notificationService.search(request);
	}

	@GetMapping("/count")
	public Long countNotify(NotificationRequest request) {
		return notificationService.countBy(request);
	}

	@PostMapping
	public void seenNotification(@RequestParam Long notificationId) {
		notificationService.seen(notificationId);
	}
}
