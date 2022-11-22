import {createAction, props} from "@ngrx/store";
import {Degree} from "../../../model/degree.model";

export const AddDegree = createAction('[Degree List] Add', props<{ degree: Degree }>());
export const RemoveDegree = createAction('[Degree List] Remove', props<{ degree: Degree }>());
export const retrieveType = '[Degree List] Retrieve';
export const RetrieveDegrees = createAction(retrieveType);
export const retrievedType = '[Degree List] Retrieved';
export const RetrievedDegrees = createAction(retrievedType, props<{ degrees: Degree[] }>());
export const RetrieveFailure = createAction('[Degree List] Load Failure');
