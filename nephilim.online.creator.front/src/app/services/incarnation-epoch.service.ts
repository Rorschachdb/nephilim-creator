import {Injectable} from '@angular/core';
import {Observable, of} from "rxjs";
import {EraEnum, IncarnationEpoch, OccultScienceTypeEnum} from "../model/incarnation-epoch.model";

const data: IncarnationEpoch[] = [
  {
    "id": 1,
    "name": "La chute de l'atlantide",
    "description": "",
    "locations" : ["Atlantis"],
    "magicEffects" : [
      {
        "occultScienceType" : OccultScienceTypeEnum.MAGIC,
        "quantity" : 2
      }
    ],
    "cost" : 0,
    "era" : EraEnum.LOST_ERA,
    "degreeValues" : []
  },
  {
    "id": 2,
    "name": "Le Déluge",
    "description": "",
    "locations" : [],
    "timePeriod" : {
      "startDate" :  new Date('Year 10000 BCE'),
      "endDate" : new Date('Year 4000 BCE')},
    "magicEffects" :[
      {
        "occultScienceType" : OccultScienceTypeEnum.MAGIC,
        "quantity" : 3
      }
    ],
    "cost" : 2,
    "era" : EraEnum.ELEMENTAL_WARS,
    "degreeValues": []
  },
  {
    "id": 3,
    "name": "Les premiers dieux",
    "description": "",
    "locations" : ["Steppes pontiques"],
    "timePeriod" : {
      "startDate" :  new Date('Year 9000 BCE'),
      "endDate" : new Date('Year 4000 BCE')},
    "magicEffects" :[
      {
        "occultScienceType" : OccultScienceTypeEnum.MAGIC,
        "quantity" : 1
      }
    ],
    "cost" : 1,
    "era" : EraEnum.ELEMENTAL_WARS,
    "degreeValues": []
  },
  {
    "id": 4,
    "name": "Le premier peuple",
    "description": "",
    "locations" : ["Harrapa", "Mohenjo Daro"],
    "timePeriod" : {
      "startDate" :  new Date('Year 8000 BCE'),
      "endDate" : new Date('Year 4000 BCE')},
    "magicEffects" :[
      {
        "occultScienceType" : OccultScienceTypeEnum.MAGIC,
        "quantity" : 1
      }
    ],
    "cost" : 1,
    "era" : EraEnum.ELEMENTAL_WARS,
    "degreeValues": []
  },
  {
    "id": 5,
    "name": "La première cité",
    "description": "",
    "locations" : ["Henoch", "Nod", "Damas", "Jéricho"],
    "timePeriod" : {
      "startDate" :  new Date('Year 7000 BCE'),
      "endDate" : new Date('Year 4000 BCE')},
    "magicEffects" :[
      {
        "occultScienceType" : OccultScienceTypeEnum.MAGIC,
        "quantity" : 1
      }
    ],
    "cost" : 1,
    "era" : EraEnum.ELEMENTAL_WARS,
    "degreeValues": []
  },
  {
    "id": 6,
    "name": "Les premiers mégalithes",
    "description": "",
    "locations" : ["Europe"],
    "timePeriod" : {
      "startDate" :  new Date('Year 5000 BCE'),
      "endDate" : new Date('Year 4000 BCE')},
    "magicEffects" :[
      {
        "occultScienceType" : OccultScienceTypeEnum.MAGIC,
        "quantity" : 1
      }
    ],
    "cost" : 1,
    "era" : EraEnum.ELEMENTAL_WARS,
    "degreeValues": []
  },
  {
    "id": 7,
    "name": "Le sanctuaire des Thuata de Danaan",
    "description": "",
    "locations" : ["Tara", "Le Sidhe"],
    "timePeriod" : {
      "startDate" :  new Date('Year 4000 BCE'),
      "endDate" : new Date('Year 3000 BCE')},
    "magicEffects" :[
      {
        "occultScienceType" : OccultScienceTypeEnum.MAGIC,
        "quantity" : 1
      }
    ],
    "cost" : 1,
    "era" : EraEnum.SECRET_COMPACTS,
    "degreeValues": []
  },
  {
    "id": 7,
    "name": "Le Mahabharata",
    "description": "",
    "locations" : ["Hastinapur"],
    "timePeriod" : {
      "startDate" :  new Date('Year 3000 BCE'),
      "endDate" : new Date('Year 2000 BCE')},
    "magicEffects" :[
      {
        "occultScienceType" : OccultScienceTypeEnum.MAGIC,
        "quantity" : 1
      }
    ],
    "cost" : 1,
    "era" : EraEnum.SECRET_COMPACTS,
    "degreeValues": []
  },
]

@Injectable({
  providedIn: 'root'
})
export class IncarnationEpochService {

  constructor() {
  }

  //TODO implement stub method
  getAll(): Observable<IncarnationEpoch[]> {
    return of(data);
  }
}
