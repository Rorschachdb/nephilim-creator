import {createAction, props} from "@ngrx/store";
import {IncarnationEpoch} from "../../../model/incarnation-epoch.model";

export const RetrieveIncarnationEpochAction = createAction('[IE List] Retrieve');
export const RetrievedIncarnationEpochAction = createAction('[IE List] Retrieved', props<{ incarnationEpochs: IncarnationEpoch[] }>());
export const RetrieveIncarnationEpochFailureAction = createAction('[IE List] Load Failure');
