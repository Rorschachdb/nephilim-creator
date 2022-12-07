import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {IncarnationEpochService} from "../../../services/incarnation-epoch.service";
import * as IncarnationEpochActions from "./incarnation-epoch.actions";
import {catchError, map, of, switchMap} from "rxjs";

@Injectable()
export class IncarnationEpochEffects {

  //TODO implement load effect
  loadEffects$ = createEffect(() => this.actions$.pipe(
      ofType(IncarnationEpochActions.RetrieveIncarnationEpochAction),
      switchMap(() => this.incarnationEpochService.getAll()
        .pipe(
          map(incarnationEpochs => IncarnationEpochActions.RetrievedIncarnationEpochAction({incarnationEpochs: incarnationEpochs})),
          catchError(() => of(IncarnationEpochActions.RetrieveIncarnationEpochFailureAction()))
        ))
    )
  );

  constructor(
    private actions$: Actions,
    private incarnationEpochService: IncarnationEpochService
  ) {
  }
}
