package org.rorschachdb.nephilim.online.creator.back.mappers;

import org.mapstruct.Mapper;
import org.rorschachdb.nephilim.online.creator.back.model.entities.IncarnationEpoch;
import org.rorschachdb.nephilim.online.creator.back.model.representation.IncarnationEpochRepresentation;

/**
 * Mapper for {@link IncarnationEpoch} to {@link IncarnationEpochRepresentation and vice versa}
 */
@Mapper(componentModel = "spring")
public interface IncarnationEpochMapper extends EntityMapper<IncarnationEpoch, IncarnationEpochRepresentation> {
}
