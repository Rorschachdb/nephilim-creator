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

import org.rorschachdb.nephilim.online.creator.back.model.entities.IncarnationEpoch;
import org.rorschachdb.nephilim.online.creator.back.model.entities.IncarnationEpochDegreeValue;
import org.rorschachdb.nephilim.online.creator.back.model.representation.IncarnationEpochRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @Author rorshachdb
 */
public abstract class IncarnationEpochMapperDecorator implements IncarnationEpochMapper {
    @Autowired
    @Qualifier("delegate")
    private IncarnationEpochMapper delegate;

    @Override
    public IncarnationEpoch toEntity(final IncarnationEpochRepresentation representation) {
        final IncarnationEpoch result = this.delegate.toEntity(representation);
        if (result.getDegreeValues() != null) {
            for (final IncarnationEpochDegreeValue iedv : result.getDegreeValues()) {
                iedv.setIncarnationEpoch(result);
            }
        }
        return result;
    }
}
