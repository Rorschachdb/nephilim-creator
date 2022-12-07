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

//TODO to test
export interface IncarnationEpoch {
  id: number,
  name: string,
  description: string,
  locations: string [],
  magicEffects: MagicEffect[],
  cost: number,
  timePeriod?: TimePeriod,
  era: EraEnum,
  degreeValues: IncarnationEpochDegreeValue[]
}

export interface MagicEffect {
  occultScienceType: OccultScienceTypeEnum,
  quantity: number
}

export interface TimePeriod {
  startDate: Date,
  endDate: Date
}

export interface IncarnationEpochDegreeValue {
  degreeId: number,
  level: number
}

export enum EraEnum {
  LOST_ERA = 'LOST_ERA',
  ELEMENTAL_WARS ='ELEMENTAL_WARS',
  SECRET_COMPACTS = 'SECRET_COMPACTS'
}

export enum OccultScienceTypeEnum {
  MAGIC= 'MAGIC',
  KABBALAH = 'KABBALAH',
  ALCHEMY = 'ALCHEMY'
}
export const EraEnumOrder: {[key in EraEnum]:number} = {
  [EraEnum.LOST_ERA]:0,
  [EraEnum.ELEMENTAL_WARS]:1,
  [EraEnum.SECRET_COMPACTS]:2
}
