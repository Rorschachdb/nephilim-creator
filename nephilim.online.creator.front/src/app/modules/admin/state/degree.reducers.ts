import {Degree} from "../../../model/degree.model";
import {RetrievedDegreesAction, RetrieveDegreeFailureAction, RetrieveDegreesAction} from "./degree.actions";
import {createFeature, createReducer, on} from "@ngrx/store";

export interface State {
  degrees: ReadonlyArray<Degree>;
  loading: boolean;
}

export const initialState: State = {
  degrees: [],
  loading: false,
}

export const degreesFeature = createFeature({
  name: 'degrees',
  reducer: createReducer(
    initialState,
    on(RetrieveDegreesAction, (state) => {
      return {
        ...state,
        loading: true,
      };
    }),
    on(RetrievedDegreesAction, (state, {degrees}) => {
      return {
        ...state,
        degrees,
        loading: false,
      }
    }),
    on(RetrieveDegreeFailureAction, state => {
      return {
        ...state,
        loading: false,
      };
    })
  )
})
