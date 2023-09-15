package com.falcon.view.boomer.core.repository;

import com.falcon.view.boomer.core.domain.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends
		JpaRepository<NotificationEntity, Long>,
		JpaSpecificationExecutor<NotificationEntity> {

	@Modifying
	@Query("update NotificationEntity s set s.seen = true where s.id = ?1 and s.owner = ?2")
	void updateSeen(Long id, String owner);
}
