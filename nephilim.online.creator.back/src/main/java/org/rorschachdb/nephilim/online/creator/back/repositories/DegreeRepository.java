package org.rorschachdb.nephilim.online.creator.back.repositories;

import org.rorschachdb.nephilim.online.creator.back.model.Degree;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Degree repository used for standard access operation
 */
public interface DegreeRepository extends JpaRepository<Degree, Long> {
}
