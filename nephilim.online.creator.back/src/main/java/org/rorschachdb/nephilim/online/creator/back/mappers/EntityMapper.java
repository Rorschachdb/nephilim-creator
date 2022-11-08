package org.rorschachdb.nephilim.online.creator.back.mappers;

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
     * maps to entity
     *
     * @param representation source
     * @return target
     */
    E toEntity(R representation);
}
