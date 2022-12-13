import {Component, OnInit} from '@angular/core';
import {MatTreeFlatDataSource, MatTreeFlattener} from "@angular/material/tree";
import {FlatTreeControl} from "@angular/cdk/tree";
import {Store} from "@ngrx/store";
import {Degree} from "../../model/degree.model";
import {RetrieveDegreesAction} from "./state/degree.actions";
import {selectDegreesPageViewModel, selectIncarnationEpochsPageViewModel} from "./state/admin.selectors";
import {IncarnationEpoch} from "../../model/incarnation-epoch.model";
import {RetrieveIncarnationEpochAction} from "./state/incarnation-epoch.actions";
import {combineLatest, map} from "rxjs";

/**
 * Admin data with nested structure.
 * Each node has a name and an optional list of children.
 */
interface AdminNode {
  name: string;
  children?: AdminNode[];
}

/** Flat node with expandable and level information */
interface FlatNode {
  expandable: boolean;
  name: string;
  level: number;
}

@Component({
  selector: 'noc-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  treeControl = new FlatTreeControl<FlatNode>(
    node => node.level,
    node => node.expandable,
  );
  dataSource: MatTreeFlatDataSource<AdminNode, FlatNode, FlatNode>;

  adminData$ = combineLatest(
    [this.store.select(selectDegreesPageViewModel),
      this.store.select(selectIncarnationEpochsPageViewModel)]
  ).pipe(
    map(([degreesState, incarnationEpochState]) => ({
      degrees: degreesState.degrees,
      degreeLoading: degreesState.loading,
      incarnationEpochs: incarnationEpochState.incarnationEpochs,
      incarnationEpochLoading: incarnationEpochState.loading
    }))
  );
  private loading: unknown;

  constructor(private store: Store) {
    this.dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);
    this.dataSource.data = [];

  }

  ngOnInit(): void {
    this.adminData$.subscribe(s => {
      this.loading = this.computeLoading(s);
      this.dataSource.data = this.turnStateToData(s);
    });
    this.store.dispatch(RetrieveDegreesAction());
    this.store.dispatch(RetrieveIncarnationEpochAction());
  }

  hasChild = (_: number, node: FlatNode) => node.expandable;

  private _transformer = (node: AdminNode, level: number) => {
    return {
      expandable: !!node.children && node.children.length > 0,
      name: node.name,
      level: level,
    };
  };

  treeFlattener = new MatTreeFlattener(
    this._transformer,
    node => node.level,
    node => node.expandable,
    node => node.children,
  );


  private turnDegreeToData(degrees: ReadonlyArray<Degree>) {
    //sort
    const sortedDegrees = degrees.map(d => d).sort((a, b) => a.type.localeCompare(b.type));
    const degreeChildren: AdminNode[] = [];

    //loop
    let type = '';
    let nodeChildren: AdminNode[] = []
    for (const degree of sortedDegrees) {
      if (type !== degree.type) {
        type = degree.type.toString();
        nodeChildren = []
        //nodeChildren.push({name: degree.name})
        const node = {name: degree.type.toString(), children: nodeChildren}
        degreeChildren.push(node);
      }
      nodeChildren.push({name: degree.name})

    }
    const degreeRoot = {name: 'Degree', children: degreeChildren}
    return degreeRoot;
  }

  private turnStateToData(s: { incarnationEpochs: unknown; incarnationEpochLoading: unknown; degreeLoading: unknown; degrees: unknown }) {
    const result: AdminNode[] = [];
    result.push(this.turnDegreeToData(<Degree[]>s.degrees));
    result.push(this.turnIncarnationEpochToData(<IncarnationEpoch[]>s.incarnationEpochs));
    return result;
  }

  private computeLoading(s: { incarnationEpochs: unknown; incarnationEpochLoading: unknown; degreeLoading: unknown; degrees: unknown }) {
    return s.degreeLoading || s.incarnationEpochLoading;
  }

  private turnIncarnationEpochToData(incarnationEpochs: IncarnationEpoch[]) {
    // TODO turn IncarnationEpoch[] into AdminNode []
    const ieChildren: AdminNode[] = [];
    const degreeRoot = {name: 'Incarnation Epoch', children: ieChildren}
    return degreeRoot;
  }
}
