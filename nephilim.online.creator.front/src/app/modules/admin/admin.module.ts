import {isDevMode, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AdminRoutingModule} from './admin-routing.module';
import {AdminComponent} from './admin.component';
import {MatTreeModule} from "@angular/material/tree";
import {MatIconModule} from "@angular/material/icon";
import {DegreeEffects} from "./state/degree.effects";
import {EffectsModule} from "@ngrx/effects";
import {StoreModule} from "@ngrx/store";
import {degreesFeature} from "./state/degree.reducers";
import {incarnationEpochFeature} from "./state/incarnation-epoch.reducers";
import {IncarnationEpochEffects} from "./state/incarnation-epoch.effects";
import {StoreDevtoolsModule} from "@ngrx/store-devtools";


@NgModule({
  declarations: [
    AdminComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    MatTreeModule,
    MatIconModule,
    EffectsModule.forFeature([DegreeEffects, IncarnationEpochEffects]),
    StoreModule.forFeature(degreesFeature),
    StoreModule.forFeature(incarnationEpochFeature),
    StoreDevtoolsModule.instrument({
      maxAge: 25, // Retains last 25 states
      logOnly: !isDevMode(), // Restrict extension to log-only mode
      autoPause: true, // Pauses recording actions and state changes when the extension window is not open
      trace: false, //  If set to true, will include stack trace for every dispatched action, so you can see it in trace tab jumping directly to that part of code
      traceLimit: 75, // maximum stack trace frames to be stored (in case trace option was provided as true)
    }),
  ]
})
export class AdminModule {
}
