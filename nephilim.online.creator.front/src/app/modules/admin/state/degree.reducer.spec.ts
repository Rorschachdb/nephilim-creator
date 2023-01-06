/*
 * nephilim.online.creator.back
 *
 * Copyright (c) 2023 by rorshach-corp
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

import {degreesFeature, initialState} from "./degree.reducers";
import {RetrievedDegreesAction, RetrieveDegreeFailureAction, RetrieveDegreesAction} from "./degree.actions";
import {Degree, DegreeTypeEnum} from "../../../model/degree.model";

const degrees: Degree[] = <Degree[]>[
  {
    "id": 1002,
    "name": "Hyperborée",
    "description": "",
    "type": DegreeTypeEnum.ESOTERIC_QUEST
  },
  {
    "id": 1201,
    "name": "Magie",
    "description": "",
    "type": DegreeTypeEnum.OCCULT_ART
  },
];

describe('DegreeReducer', () => {
  it('should have initial state', () => {
    let action = {type: 'foo'};
    expect(degreesFeature.reducer(undefined, action)).toEqual(initialState)
  });
  it('should handle RetrieveDegreesAction', () => {
    let action = RetrieveDegreesAction();
    expect(degreesFeature.reducer(initialState, action)).toEqual({...initialState, loading: true})
  });
  it('should handle RetrievedDegreesAction', () => {
    let action = RetrievedDegreesAction({degrees});
    expect(degreesFeature.reducer({...initialState, loading: true}, action)).toEqual({
      ...initialState,
      degrees,
      loading: false
    })
  });
  it('should handle RetrieveDegreeFailureAction', () => {
    let action = RetrieveDegreeFailureAction({message: 'error'});
    expect(degreesFeature.reducer({...initialState, loading: true}, action)).toEqual({
      ...initialState,
      message: 'error',
      loading: false
    })
  });
});
