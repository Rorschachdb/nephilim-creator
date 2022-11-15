package org.rorschachdb.nephilim.online.creator.back.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.rorschachdb.nephilim.online.creator.back.model.entities.IncarnationEpoch;
import org.rorschachdb.nephilim.online.creator.back.model.entities.IncarnationEpochDegreeValue;
import org.rorschachdb.nephilim.online.creator.back.model.representation.IncarnationEpochDegreeValueRepresentation;
import org.rorschachdb.nephilim.online.creator.back.model.representation.IncarnationEpochRepresentation;

/**
 * Mapper for {@link IncarnationEpoch} to {@link IncarnationEpochRepresentation and vice versa}
 */
@Mapper(componentModel = "spring", uses = {DegreeMapper.class})
public interface IncarnationEpochDegreeValueMapper extends EntityMapper<IncarnationEpochDegreeValue, IncarnationEpochDegreeValueRepresentation> {

    @Override
    @Mapping(target = "degreeId", source = "id.fkDegreeId")
    IncarnationEpochDegreeValueRepresentation toRepresentation(IncarnationEpochDegreeValue entity);

    @Override
    @Mapping(target = "id.fkDegreeId", source = "degreeId")
    @Mapping(target = "degree", source = "degreeId")
    IncarnationEpochDegreeValue toEntity(IncarnationEpochDegreeValueRepresentation representation);
}
