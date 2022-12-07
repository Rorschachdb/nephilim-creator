import {createFeature, createReducer, on} from "@ngrx/store";
import {IncarnationEpoch} from "../../../model/incarnation-epoch.model";
import {RetrievedIncarnationEpochAction, RetrieveIncarnationEpochAction} from "./incarnation-epoch.actions";

export interface State {
  incarnationEpochs: ReadonlyArray<IncarnationEpoch>;
  loading: boolean;
}

export const initialState: State = {
  incarnationEpochs: [],
  loading: false,
}

export const incarnationEpochFeature = createFeature({
  name: 'incarnation_epoch',
  reducer: createReducer(
    initialState,
    on(RetrieveIncarnationEpochAction, (state) => ({
      ...state,
      loading: true,
    })),
    on(RetrievedIncarnationEpochAction, (state, {incarnationEpochs}) => ({
      ...state,
      incarnationEpochs,
      loading: false,
    })),
    on(RetrieveIncarnationEpochAction, state => ({
      ...state,
      loading: false,
    }))
  )
})
