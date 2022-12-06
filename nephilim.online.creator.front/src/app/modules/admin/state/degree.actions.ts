import {createAction, props} from "@ngrx/store";
import {Degree} from "../../../model/degree.model";

export const AddDegreeAction = createAction('[Degree List] Add', props<{ degree: Degree }>());
export const RemoveDegreeAction = createAction('[Degree List] Remove', props<{ degree: Degree }>());
export const RetrieveDegreesAction = createAction('[Degree List] Retrieve');
export const RetrievedDegreesAction = createAction('[Degree List] Retrieved', props<{ degrees: Degree[] }>());
export const RetrieveDegreeFailureAction = createAction('[Degree List] Load Failure');
