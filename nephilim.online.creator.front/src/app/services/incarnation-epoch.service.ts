import {Injectable} from '@angular/core';
import {Observable, of} from "rxjs";
import {IncarnationEpoch} from "../model/incarnation-epoch.model";

@Injectable({
  providedIn: 'root'
})
export class IncarnationEpochService {

  constructor() {
  }

  // TODO implement stub method
  getAll(): Observable<IncarnationEpoch[]> {
    return of();

  }
}
