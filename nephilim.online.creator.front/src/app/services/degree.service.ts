import {Injectable} from '@angular/core';
import {Degree, DegreeImpl, DegreeTypeEnum} from "../model/degree.model";
import {catchError, map, Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {URI_CONSTANTS} from "./uri-constants";
import {ServiceUtil} from "./service-util";

const dataOld = [
  {
    name: "degree",
    children: [
      {
        name: 'esoteric quest', children: [
          {name: "sentier d'or"},
          {name: "jerusalem celeste"}
        ]
      },
      {
        name: 'esoteric knowledge', children: [
          {name: 'Kaïm'}, {name: 'Nephilim'}
        ]
      }
    ]
  },
  {
    name: "incarnation epoch", children: [
      {name: "Atlantis"},
      {name: "Deluge"}
    ]
  }
];

const data: Degree[] = <Degree[]>[
  {
    "id": 1001,
    "name": "Le sentier d'or",
    "description": "",
    "type": DegreeTypeEnum.ESOTERIC_QUEST
  },
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
  {
    "id": 1202,
    "name": "Kabbale",
    "description": "",
    "type": DegreeTypeEnum.OCCULT_ART
  },
  {
    "id": 1203,
    "name": "Alchimie",
    "description": "",
    "type": DegreeTypeEnum.OCCULT_ART
  },
  {
    "id": 1301,
    "name": "Kaïm",
    "description": "",
    "type": DegreeTypeEnum.ESOTERIC_KNOWLEDGE
  },
  {
    "id": 1302,
    "name": "Champs Magiques",
    "description": "",
    "type": DegreeTypeEnum.ESOTERIC_KNOWLEDGE
  },
  {
    "id": 1303,
    "name": "Nephilim",
    "description": "",
    "type": DegreeTypeEnum.ESOTERIC_KNOWLEDGE
  },
  {
    "id": 1304,
    "name": "Orichalque",
    "description": "",
    "type": DegreeTypeEnum.ESOTERIC_KNOWLEDGE
  },
  {
    "id": 1305,
    "name": "Mystères",
    "description": "",
    "type": DegreeTypeEnum.ESOTERIC_KNOWLEDGE
  },
  {
    "id": 1306,
    "name": "Histoire Invisible",
    "description": "",
    "type": DegreeTypeEnum.ESOTERIC_KNOWLEDGE
  },
  {
    "id": 1307,
    "name": "666",
    "description": "",
    "type": DegreeTypeEnum.ESOTERIC_KNOWLEDGE
  },
  {
    "id": 2001,
    "name": "Héros",
    "description": "",
    "type": DegreeTypeEnum.SIMULACRUM
  },
  {
    "id": 2002,
    "name": "Sage",
    "description": "",
    "type": DegreeTypeEnum.SIMULACRUM
  },
  {
    "id": 2003,
    "name": "Messagère",
    "description": "",
    "type": DegreeTypeEnum.SIMULACRUM
  },
  {
    "id": 2004,
    "name": "Décepteur",
    "description": "",
    "type": DegreeTypeEnum.SIMULACRUM
  },
  {
    "id": 2005,
    "name": "Soigneur",
    "description": "",
    "type": DegreeTypeEnum.SIMULACRUM
  },
  {
    "id": 2006,
    "name": "Oracle",
    "description": "",
    "type": DegreeTypeEnum.SIMULACRUM
  },
  {
    "id": 2007,
    "name": "Matriarche",
    "description": "",
    "type": DegreeTypeEnum.SIMULACRUM
  },
  {
    "id": 2008,
    "name": "Navigateur",
    "description": "",
    "type": DegreeTypeEnum.SIMULACRUM
  },
  {
    "id": 2009,
    "name": "Meurtrier",
    "description": "",
    "type": DegreeTypeEnum.SIMULACRUM
  },
  {
    "id": 2010,
    "name": "Charpentier",
    "description": "",
    "type": DegreeTypeEnum.SIMULACRUM
  }
]

@Injectable({
  providedIn: 'root'
})
export class DegreeService {

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Degree[]> {
    // call to service
    return this.http.get(environment.restUri + URI_CONSTANTS.degreeUri)
      // create observable of Degree[]
      .pipe(
        map((payload: any) => (<Degree[]>payload.content).map(d => DegreeImpl.of(d))),
        catchError(ServiceUtil.handleError)
      )
  }

}
