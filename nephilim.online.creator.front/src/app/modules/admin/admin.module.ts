import {NgModule} from '@angular/core';
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


@NgModule({
  declarations: [
    AdminComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    MatTreeModule,
    MatIconModule,
    EffectsModule.forFeature([DegreeEffects]),
    StoreModule.forFeature(degreesFeature),
    StoreModule.forFeature(incarnationEpochFeature),
  ]
})
export class AdminModule {
}
