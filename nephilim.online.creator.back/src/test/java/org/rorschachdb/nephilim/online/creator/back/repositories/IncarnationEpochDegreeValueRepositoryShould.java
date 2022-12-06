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

package org.rorschachdb.nephilim.online.creator.back.repositories;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.rorschachdb.nephilim.online.creator.back.model.embedded.IncarnationEpochDegreeValueId;
import org.rorschachdb.nephilim.online.creator.back.model.entities.Degree;
import org.rorschachdb.nephilim.online.creator.back.model.entities.IncarnationEpoch;
import org.rorschachdb.nephilim.online.creator.back.model.entities.IncarnationEpochDegreeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class IncarnationEpochDegreeValueRepositoryShould {

    @Autowired
    EntityManager em;

    @Autowired
    IncarnationEpochDegreeValueRepository repository;

    @Test
    void createRelation() {
        // GIVEN
        final Degree alchimie = this.em.createQuery("select d from Degree d where d.id = 1203", Degree.class).getSingleResult();
        final IncarnationEpoch atlantis = this.em.createQuery("select i from IncarnationEpoch i where i.id = 1", IncarnationEpoch.class).getSingleResult();

        // WHEN
        final IncarnationEpochDegreeValue dv = atlantis.addDegree(alchimie, 3);

        final IncarnationEpochDegreeValue created = this.repository.save(dv);
        final IncarnationEpochDegreeValue fromDb = this.repository.findById(new IncarnationEpochDegreeValueId(1203l, 1l)).orElse(null);
        // THEN
        assertThat(fromDb)
                .isNotNull()
                .extracting("incarnationEpoch.name", "degree.name", "level")
                .containsExactly("La chute de l'atlantide", "Alchimie", 3);

    }

    @Test()
    void failOnGreater() {
        // GIVEN
        final Degree alchimie = this.em.createQuery("select d from Degree d where d.id = 1203", Degree.class).getSingleResult();
        final IncarnationEpoch deluge = this.em.createQuery("select i from IncarnationEpoch i where i.id = 2", IncarnationEpoch.class).getSingleResult();

        // WHEN
        final IncarnationEpochDegreeValue dv = deluge.addDegree(alchimie, 10);

        final ConstraintViolationException thrown = catchThrowableOfType(() -> {
//            this.repository.save(dv);
            this.em.flush();
        }, ConstraintViolationException.class);
        // THEN
        assertThat(thrown).isNotNull();
        final Set<ConstraintViolation<?>> violations = thrown.getConstraintViolations();
        assertThat(violations).isNotEmpty().hasSize(1);
        assertThat(violations).extracting("messageTemplate")
                .contains("{jakarta.validation.constraints.Max.message}");

    }

    @Test
    void failOnLower() {
        // GIVEN
        final Degree alchimie = this.em.createQuery("select d from Degree d where d.id = 1203", Degree.class).getSingleResult();
        final IncarnationEpoch deluge = this.em.createQuery("select i from IncarnationEpoch i where i.id = 2", IncarnationEpoch.class).getSingleResult();

        // WHEN
        final IncarnationEpochDegreeValue dv = deluge.addDegree(alchimie, 0);

        final ConstraintViolationException thrown = catchThrowableOfType(() -> {
//            this.repository.save(dv);
            this.em.flush();
        }, ConstraintViolationException.class);
        // THEN
        assertThat(thrown).isNotNull();
        final Set<ConstraintViolation<?>> violations = thrown.getConstraintViolations();
        assertThat(violations).isNotEmpty().hasSize(1);
        assertThat(violations).extracting("messageTemplate")
                .contains("{jakarta.validation.constraints.Min.message}");

    }

}