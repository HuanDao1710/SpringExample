package com.falcon.view.boomer.core.mapper;

import com.falcon.view.boomer.core.domain.dto.ContactMessageDTO;
import com.falcon.view.boomer.core.domain.entity.ContactMessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContactMessageMapper {
    ContactMessageMapper INSTANCE = Mappers.getMapper(ContactMessageMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    ContactMessageEntity toEntity(ContactMessageDTO dto);

    ContactMessageDTO toDTO(ContactMessageEntity entity);
}
