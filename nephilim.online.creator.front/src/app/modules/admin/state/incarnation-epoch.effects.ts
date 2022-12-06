import {Injectable} from "@angular/core";
import {Actions} from "@ngrx/effects";
import {IncarnationEpochService} from "../../../services/incarnation-epoch.service";

@Injectable()
export class IncarnationEpochEffects {

  // TODO implement load effect
  loadEffects$: any;

  constructor(
    private actions$: Actions,
    private incarnationEpochService: IncarnationEpochService
  ) {
  }
}
