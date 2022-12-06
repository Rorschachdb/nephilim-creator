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

import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import * as DegreeActions from "./degree.actions";
import {DegreeService} from "../../../services/degree.service";
import {catchError, map, mergeMap, of} from "rxjs";

@Injectable()
export class DegreeEffects {

  loadEffects$ = createEffect(() => this.actions$.pipe(
      ofType(DegreeActions.RetrieveDegreesAction),
      mergeMap(() => this.degreeService.getAll()
        .pipe(
          map(degrees => DegreeActions.RetrievedDegreesAction({degrees: degrees})),
          catchError(() => of(DegreeActions.RetrieveDegreeFailureAction()))
        ))
    )
  );

  constructor(
    private actions$: Actions,
    private degreeService: DegreeService
  ) {
  }
}
