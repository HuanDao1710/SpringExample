package com.falcon.view.boomer.core.mapper;

import com.falcon.view.boomer.core.domain.dto.NotificationDTO;
import com.falcon.view.boomer.core.domain.entity.NotificationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
	@Mapping(target = "notificationId", source = "id")
	NotificationDTO toDTO(NotificationEntity entity);
}
