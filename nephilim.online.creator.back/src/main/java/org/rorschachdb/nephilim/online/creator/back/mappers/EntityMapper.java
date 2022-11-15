package org.rorschachdb.nephilim.online.creator.back.mappers;

import java.util.List;
import java.util.Set;

/**
 * the reference interface to map an entity to a representation
 *
 * @param <E> entity
 * @param <R> representation
 */
public interface EntityMapper<E, R> {
    /**
     * maps to representation
     *
     * @param entity source
     * @return target
     */
    R toRepresentation(E entity);

    /**
     * maps to representation
     *
     * @param entities source
     * @return target
     */
    Set<R> toRepresentations(Set<E> entities);

    /**
     * maps to representation
     *
     * @param entities source
     * @return target
     */
    List<R> toRepresentations(List<E> entities);

    /**
     * maps to entity
     *
     * @param representation source
     * @return target
     */
    E toEntity(R representation);

    /**
     * maps to entity
     *
     * @param representations source
     * @return target
     */
    Set<E> toEntity(Set<R> representations);

    /**
     * maps to entity
     *
     * @param representations source
     * @return target
     */
    List<E> toEntity(List<R> representations);
}
