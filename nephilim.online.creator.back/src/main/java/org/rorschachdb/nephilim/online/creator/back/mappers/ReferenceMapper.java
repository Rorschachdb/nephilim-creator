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

import lombok.RequiredArgsConstructor;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

/**
 * @Author rorshachdb
 */
@Component
@RequiredArgsConstructor
public class ReferenceMapper {

    private final EntityManager entityManager;

    @ObjectFactory
    public <T> T map(final Long id, @TargetType final Class<T> type) {
        if (id == null) {
            return null;
        }
        return this.entityManager.getReference(type, id);
    }
}
