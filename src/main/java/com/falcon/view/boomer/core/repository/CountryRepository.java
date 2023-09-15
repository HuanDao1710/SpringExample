package com.falcon.view.boomer.core.repository;

import com.falcon.view.boomer.core.domain.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
	long countByShortNameIn(Set<String> country);

	@Query("SELECT c FROM CountryEntity c ORDER BY LOWER(c.name) ASC")
	List<CountryEntity> findAllOrderByNamelowerCaseAsc();
}
