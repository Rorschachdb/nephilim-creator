package org.rorschachdb.nephilim.online.creator.back.repositories;

import org.rorschachdb.nephilim.online.creator.back.model.entities.IncarnationEpoch;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Incarnation Epoch repository used for standard access operation
 */
public interface IncarnationEpochRepository extends JpaRepository<IncarnationEpoch, Long> {
}
