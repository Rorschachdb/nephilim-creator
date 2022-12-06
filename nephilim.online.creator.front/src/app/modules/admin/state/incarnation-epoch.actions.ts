import {createAction, props} from "@ngrx/store";
import {IncarnationEpoch} from "../../../model/incarnation-epoch.model";

export const RetrieveIncarnationEpochAction = createAction('[Degree List] Retrieve');
export const RetrievedIncarnationEpochAction = createAction('[Degree List] Retrieved', props<{ incarnationEpochs: IncarnationEpoch[] }>());
export const RetrieveIncarnationEpochFailureAction = createAction('[Degree List] Load Failure');
