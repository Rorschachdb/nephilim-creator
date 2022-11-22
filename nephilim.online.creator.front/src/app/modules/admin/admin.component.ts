import {Component, OnInit} from '@angular/core';
import {MatTreeFlatDataSource, MatTreeFlattener} from "@angular/material/tree";
import {FlatTreeControl} from "@angular/cdk/tree";
import {Store} from "@ngrx/store";
import {selectDegreesPageViewModel} from "./state/degree.selectors";
import {Degree} from "../../model/degree.model";
import {RetrieveDegrees} from "./state/degree.actions";

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

  degrees$ = this.store.select(selectDegreesPageViewModel)

  constructor(private store: Store) {
    this.dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);
    this.dataSource.data = [];

  }

  ngOnInit(): void {
    this.degrees$.subscribe(s => this.dataSource.data = this.turnDegreeToData(s.degrees));
    this.store.dispatch(RetrieveDegrees());
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
    const result: AdminNode[] = [];
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
    result.push(degreeRoot);
    return result;
  }
}
