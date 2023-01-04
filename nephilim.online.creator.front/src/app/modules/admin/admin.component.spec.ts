import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AdminComponent} from './admin.component';
import {MockStore, provideMockStore} from "@ngrx/store/testing";
import * as degreeReducer from "./state/degree.reducers";
import * as incarnationEpochReducer from "./state/incarnation-epoch.reducers";
import {Degree, DegreeTypeEnum} from "../../model/degree.model";
import {MemoizedSelector, Store} from "@ngrx/store";
import * as AdminSelectors from "./state/admin.selectors";
import {MatTreeModule} from "@angular/material/tree";
import {MatIconModule} from "@angular/material/icon";
import {RouterTestingModule} from "@angular/router/testing";
import {EraEnum, OccultScienceTypeEnum} from "../../model/incarnation-epoch.model";
import {By} from "@angular/platform-browser";
import {RetrieveDegreesAction} from "./state/degree.actions";
import {RetrieveIncarnationEpochAction} from "./state/incarnation-epoch.actions";

const degreeValuedState = {
  degrees: [
    {
      "id": 1002,
      "name": "Hyperborée",
      "description": "",
      "type": DegreeTypeEnum.ESOTERIC_QUEST
    },
    {
      "id": 1201,
      "name": "Magie",
      "description": "",
      "type": DegreeTypeEnum.OCCULT_ART
    },
  ],
  loading: false,
};

const degreeLoadingState = {
  degrees: [],
  loading: true,
};

const degreeInitState = {
  degrees: [],
  loading: false,
};

const ieValuedState = {
  incarnationEpochs: [{
    "id": 1,
    "name": "La chute de l'atlantide",
    "description": "",
    "locations": ["Atlantis"],
    "magicEffects": [
      {
        "occultScienceType": OccultScienceTypeEnum.MAGIC,
        "quantity": 2
      }
    ],
    "cost": 0,
    "era": EraEnum.LOST_ERA,
    "degreeValues": []
  },
    {
      "id": 2,
      "name": "Le Déluge",
      "description": "",
      "locations": [],
      "timePeriod": {
        "startDate": new Date('Year 10000 BCE'),
        "endDate": new Date('Year 4000 BCE')
      },
      "magicEffects": [
        {
          "occultScienceType": OccultScienceTypeEnum.MAGIC,
          "quantity": 3
        }
      ],
      "cost": 2,
      "era": EraEnum.ELEMENTAL_WARS,
      "degreeValues": []
    },],
  loading: false,
};

const ieLoadingState = {
  incarnationEpochs: [],
  loading: true,
};

const ieInitState = {
  incarnationEpochs: [],
  loading: false,
};

describe('AdminComponent', () => {
  let component: AdminComponent;
  let fixture: ComponentFixture<AdminComponent>;
  let mockStore: MockStore<{ degrees: degreeReducer.State }>;
  let mockIncarnationEpochsSelector: MemoizedSelector<incarnationEpochReducer.State, incarnationEpochReducer.State>;
  let mockDegreesSelector: MemoizedSelector<{ degrees: readonly Degree[], loading: boolean }, { degrees: readonly Degree[], loading: boolean }>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        MatTreeModule,
        MatIconModule,
      ],
      declarations: [AdminComponent],
      providers: [provideMockStore()]
    })
      .compileComponents();

    fixture = TestBed.createComponent(AdminComponent);
    mockStore = <MockStore<{ degrees: degreeReducer.State }>>TestBed.inject(Store);
    mockDegreesSelector = mockStore.overrideSelector(AdminSelectors.selectDegreesPageViewModel, degreeInitState);
    mockIncarnationEpochsSelector = mockStore.overrideSelector(AdminSelectors.selectIncarnationEpochsPageViewModel, ieInitState);

    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should init and display degrees and incarnation epoch without values', () => {
    // the component should exist
    expect(component).toBeTruthy();
    // spy dispatch of store and rely on regular implementation
    const dispatchSpy = spyOn(mockStore, 'dispatch').and.callThrough();
    // #1 init component
    component.ngOnInit();
    fixture.detectChanges();
    expect(dispatchSpy).toHaveBeenCalledTimes(2)
    expect(dispatchSpy).toHaveBeenCalledWith(RetrieveDegreesAction());
    expect(dispatchSpy).toHaveBeenCalledWith(RetrieveIncarnationEpochAction());
    const visibleNodes = fixture.debugElement.queryAll(By.css("mat-tree-node"));
    expect(visibleNodes.length).toBe(2);
    const chevron = fixture.debugElement.queryAll(By.css("mat-icon.mat-icon-rtl-mirror"));
    expect(chevron.length).toBe(0);
    expect(component.loading).toBeFalsy();
  });
  it('should init and display degrees and incarnation epoch with values', () => {
    expect(component).toBeTruthy();
    const dispatchSpy = spyOn(mockStore, 'dispatch').and.callThrough();
    mockDegreesSelector.setResult(degreeValuedState);
    mockIncarnationEpochsSelector.setResult(ieValuedState);
    mockStore.refreshState();
    component.ngOnInit();
    fixture.detectChanges();
    expect(dispatchSpy).toHaveBeenCalledTimes(2)
    expect(dispatchSpy).toHaveBeenCalledWith(RetrieveDegreesAction());
    expect(dispatchSpy).toHaveBeenCalledWith(RetrieveIncarnationEpochAction());
    const visibleNodes = fixture.debugElement.queryAll(By.css("mat-tree-node"));
    expect(visibleNodes.length).toBe(2);
    const chevron = fixture.debugElement.queryAll(By.css("mat-icon.mat-icon-rtl-mirror"));
    expect(chevron.length).toBe(2);
    expect(component.loading).toBeFalsy();
  });
  it('should init and display loading', () => {
    expect(component).toBeTruthy();
    const dispatchSpy = spyOn(mockStore, 'dispatch').and.callThrough();
    mockDegreesSelector.setResult(degreeLoadingState);
    mockIncarnationEpochsSelector.setResult(ieLoadingState);
    mockStore.refreshState();
    component.ngOnInit();
    fixture.detectChanges();
    expect(dispatchSpy).toHaveBeenCalledTimes(2)
    expect(dispatchSpy).toHaveBeenCalledWith(RetrieveDegreesAction());
    expect(dispatchSpy).toHaveBeenCalledWith(RetrieveIncarnationEpochAction());
    expect(component.loading).toBeTruthy();
  });
});
