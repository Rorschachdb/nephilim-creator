
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

package org.rorschachdb.nephilim.online.creator.back.model.embedded;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Time period embeded in {@link org.rorschachdb.nephilim.online.creator.back.model.entities.IncarnationEpoch}
 * If only startDate is set Time Period is considered a punctual event. Loste era as neither start nor end date
 *
 * @author rorshachdb
 */
@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimePeriod implements Serializable {

    private LocalDate startDate;
    private LocalDate endDate;
}
