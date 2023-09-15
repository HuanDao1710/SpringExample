package com.falcon.view.boomer.core.repository;

import com.falcon.view.boomer.core.domain.entity.ContactMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<ContactMessageEntity, Long> {

}
