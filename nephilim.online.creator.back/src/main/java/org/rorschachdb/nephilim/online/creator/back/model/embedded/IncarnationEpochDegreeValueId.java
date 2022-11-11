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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Embeded id for degree association with incarnation epoch
 *
 * @Author rorshachdb
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class IncarnationEpochDegreeValueId implements Serializable {

    private Long fkDegreeId;

    private Long fkIncarnationEpochId;

    public Long getFkDegreeId() {
        return this.fkDegreeId;
    }

    public void setFkDegreeId(final Long fkDegreeId) {
        this.fkDegreeId = fkDegreeId;
    }

    public Long getFkIncarnationEpochId() {
        return this.fkIncarnationEpochId;
    }

    public void setFkIncarnationEpochId(final Long fkIncarnationEpochId) {
        this.fkIncarnationEpochId = fkIncarnationEpochId;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof IncarnationEpochDegreeValueId)) {
            return false;
        }
        final IncarnationEpochDegreeValueId other = (IncarnationEpochDegreeValueId) o;
        if (!other.canEqual((Object) this)) {
            return false;
        }
        final Object this$fkDegreeId = this.getFkDegreeId();
        final Object other$fkDegreeId = other.getFkDegreeId();
        if (this$fkDegreeId == null ? other$fkDegreeId != null : !this$fkDegreeId.equals(other$fkDegreeId)) {
            return false;
        }
        final Object this$fkIncarnationEpochId = this.getFkIncarnationEpochId();
        final Object other$fkIncarnationEpochId = other.getFkIncarnationEpochId();
        if (this$fkIncarnationEpochId == null ? other$fkIncarnationEpochId != null : !this$fkIncarnationEpochId.equals(other$fkIncarnationEpochId)) {
            return false;
        }
        return true;
    }

    private boolean canEqual(final Object other) {
        return other instanceof IncarnationEpochDegreeValueId;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $fkDegreeId = this.getFkDegreeId();
        result = result * PRIME + ($fkDegreeId == null ? 43 : $fkDegreeId.hashCode());
        final Object $fkIncarnationEpochId = this.getFkIncarnationEpochId();
        result = result * PRIME + ($fkIncarnationEpochId == null ? 43 : $fkIncarnationEpochId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "IncarnationEpochDegreeValueId(fkDegreeId=" + this.getFkDegreeId() + ", fkIncarnationEpochId=" + this.getFkIncarnationEpochId() + ")";
    }
}
