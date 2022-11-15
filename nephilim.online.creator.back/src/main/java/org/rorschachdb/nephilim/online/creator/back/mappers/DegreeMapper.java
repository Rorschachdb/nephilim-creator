package org.rorschachdb.nephilim.online.creator.back.mappers;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.rorschachdb.nephilim.online.creator.back.model.entities.Degree;
import org.rorschachdb.nephilim.online.creator.back.model.entities.IncarnationEpoch;
import org.rorschachdb.nephilim.online.creator.back.model.representation.DegreeRepresentation;
import org.rorschachdb.nephilim.online.creator.back.model.representation.IncarnationEpochRepresentation;

/**
 * Mapper for {@link IncarnationEpoch} to {@link IncarnationEpochRepresentation and vice versa}
 */
@Mapper(componentModel = "spring", uses = {ReferenceMapper.class}, builder = @Builder(disableBuilder = true))
public interface DegreeMapper extends EntityMapper<Degree, DegreeRepresentation> {

    public Degree toEntity(Long id);
}
