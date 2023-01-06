import {TestBed} from '@angular/core/testing';

import {DegreeService} from './degree.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {HttpClient} from "@angular/common/http";
import {Degree, DegreeImpl, DegreeTypeEnum} from "../model/degree.model";
import {environment} from "../../environments/environment";
import {URI_CONSTANTS} from "./uri-constants";

describe('DegreeService', () => {
  let service: DegreeService;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    // Inject the http service and test controller for each test
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
    service = TestBed.inject(DegreeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  it('should retrieve data', () => {
    // test data
    const testData: Degree[] = <Degree[]>[
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
    ];
    // call getAll
    service.getAll()
      // verify expected result
      .subscribe(degrees => expect(degrees).toEqual(testData.map(DegreeImpl.of)));
    // get request from test controller
    const request = httpTestingController.expectOne(environment.restUri + URI_CONSTANTS.degreeUri);
    // verify request expectations
    expect(request.request.method).toEqual('GET');
    // flush request body
    request.flush({content: testData});
    // expect controller to be cleared of request
    httpTestingController.verify();
  });
  it('should handle error', () => {
    // test data
    const testData: Degree[] = <Degree[]>[
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
    ];
    // call getAll
    service.getAll()
      // verify expected result
      .subscribe({
        next: degrees => fail('Should have fail'),
        error: (error: Error) => expect(error.message).toEqual('Something bad happened; please try again later.'),
      });
    // get request from test controller
    const request = httpTestingController.expectOne(environment.restUri + URI_CONSTANTS.degreeUri);
    // verify request expectations
    expect(request.request.method).toEqual('GET');
    // flush request body
    request.flush("it doesn't work", {status: 404, statusText: 'Not found'});
    // expect controller to be cleared of request
    httpTestingController.verify();
  });
});
