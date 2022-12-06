import {Degree} from "../../../model/degree.model";
import {RetrievedDegreesAction, RetrieveDegreeFailureAction, RetrieveDegreesAction} from "./degree.actions";
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
    on(RetrieveDegreesAction, (state) => ({
      ...state,
      loading: true,
    })),
    on(RetrievedDegreesAction, (state, {degrees}) => ({
      ...state,
      degrees,
      loading: false,
    })),
    on(RetrieveDegreeFailureAction, state => ({
      ...state,
      loading: false,
    }))
  )
})
