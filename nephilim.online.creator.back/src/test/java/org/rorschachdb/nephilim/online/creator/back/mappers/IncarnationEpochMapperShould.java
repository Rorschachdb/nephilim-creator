/*
 * nephilim.online.creator.back
 *
 * Copyright (c) 2022 by rorshach-corp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.rorschachdb.nephilim.online.creator.back.mappers;

import org.junit.jupiter.api.Test;
import org.rorschachdb.nephilim.online.creator.back.model.embedded.MagicEffect;
import org.rorschachdb.nephilim.online.creator.back.model.embedded.TimePeriod;
import org.rorschachdb.nephilim.online.creator.back.model.entities.IncarnationEpoch;
import org.rorschachdb.nephilim.online.creator.back.model.representation.IncarnationEpochDegreeValueRepresentation;
import org.rorschachdb.nephilim.online.creator.back.model.representation.IncarnationEpochRepresentation;
import org.rorschachdb.nephilim.online.creator.back.repositories.IncarnationEpochRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.rorschachdb.nephilim.online.creator.back.model.enums.EraEnum.LOST_ERA;
import static org.rorschachdb.nephilim.online.creator.back.model.enums.OccultScienceTypeEnum.MAGIC;

@SpringBootTest
@Transactional
class IncarnationEpochMapperShould {

    @Autowired
    private IncarnationEpochMapper mapper;
    @Autowired
    private IncarnationEpochRepository repository;

    @Test
    void convertNewRepresentationWithDegreeValuesIntoNewPersistableEntity() {
        final IncarnationEpochRepresentation epochR = IncarnationEpochRepresentation.builder()
                .cost(1)
                .era(LOST_ERA)
                .magicEffect(MagicEffect.builder().occultScience(MAGIC).quantity(2).build())
                .name("New Terrific IE")
                .timePeriod(TimePeriod.builder().startDate(LocalDate.MIN).build())
                .location("somewhere")
                .degreeValue(IncarnationEpochDegreeValueRepresentation
                        .builder()
                        .degreeId(1203l)
                        .level(2)
                        .build())
                .build();

        final IncarnationEpoch epoch = this.mapper.toEntity(epochR);
        final IncarnationEpoch created = this.repository.save(epoch);
        final IncarnationEpoch fromDb = this.repository.findById(created.getId()).orElse(null);
        assertThat(fromDb).isNotNull().extracting("name").isEqualTo("New Terrific IE");
        assertThat(fromDb.getDegreeValues()).hasSize(1).extracting("degree.name").containsExactly("Alchimie");
    }
}