import {Degree} from "../../../model/degree.model";
import {RetrievedDegrees, RetrieveDegrees, RetrieveFailure} from "./degree.actions";
import {createFeature, createReducer, on} from "@ngrx/store";

interface State {
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
    on(RetrieveDegrees, (state) => ({
      ...state,
      loading: true,
    })),
    on(RetrievedDegrees, (state, {degrees}) => ({
      ...state,
      degrees,
      loading: false,
    })),
    on(RetrieveFailure, state => ({
      ...state,
      loading: false,
    }))
  )
})
