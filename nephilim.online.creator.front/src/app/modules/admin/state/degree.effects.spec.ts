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

import {Observable, of} from "rxjs";
import {Action} from "@ngrx/store";
import {TestBed} from "@angular/core/testing";
import {provideMockActions} from "@ngrx/effects/testing";
import {DegreeService} from "../../../services/degree.service";
import {DegreeEffects} from "./degree.effects";
import {Degree, DegreeTypeEnum} from "../../../model/degree.model";
import {RetrievedDegreesAction, RetrieveDegreesAction} from "./degree.actions";
import {cold, hot} from "jest-marbles";

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

describe('DegreeEffect', () => {
  let actions$ = new Observable<Action>;
  let service: DegreeService;
  let effects: DegreeEffects;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [
        DegreeEffects,
        provideMockActions(() => actions$),
        {
          provide: DegreeService,
          useFactory: () => ({getAll: jest.fn()})
        }
      ],
    });
    service = TestBed.inject(DegreeService);
    effects = TestBed.inject(DegreeEffects);
  });
  it('should return a cold observable of RetrievedDegreesAction action', () => {
    const action = RetrieveDegreesAction();
    const completion = RetrievedDegreesAction({degrees});
    jest.spyOn(service, 'getAll').mockImplementation(() => of(degrees));
    actions$ = hot('--a-', {a: action});
    const expected = cold('--(b)', {b: completion});
    expect(effects.loadEffects$).toBeObservable(expected);
  });
});
