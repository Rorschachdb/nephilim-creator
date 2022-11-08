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

package org.rorschachdb.nephilim.online.creator.back.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rorschachdb.nephilim.online.creator.back.model.embedded.IncarnationEpochDegreeValueId;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Incarnation Epoch associative data with degree, holds a value field for the specific value to gain
 *
 * @Author rorshachdb
 */
@Entity
@Table(name = "AT_INCARNATION_EPOCH_DEGREE_VALUE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class IncarnationEpochDegreeValue implements Serializable {
    @EmbeddedId
    private IncarnationEpochDegreeValueId id;

    private Integer degreeValue;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("fkDegreeId")
    @JoinColumn(name = "FK_DEGREE_ID")
    private Degree degree;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("fkIncarnationEpochId")
    @JoinColumn(name = "FK_INCARNATION_EPOCH_ID")
    private IncarnationEpoch incarnationEpoch;
}
