package com.technicalguide;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummarizedContentRepository extends JpaRepository<SummarizedContent, Long> {

	boolean existsByUniqueId(String uniqueId);
}