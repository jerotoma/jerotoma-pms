import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

import { Position } from 'app/models/positions/position.model';

/**
 * @title Table with pagination
 */
@Component({
  selector: 'app-positions-view',
  styleUrls: ['positions-view.component.scss'],
  templateUrl: 'positions-view.component.html',
})
export class PositionsViewComponent implements OnInit {
  title: string = 'List of Positions';

  displayedColumns: string[] = ['id', 'name', 'code', 'description', 'action'];
  dataSource = new MatTableDataSource<Position>(ELEMENT_DATA);

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  open(){

  }
}

const ELEMENT_DATA:  Position[] = [
  {id: 1, name: 'Hydrogen', description: '', code: 'H'},
  {id: 2, name: 'Helium', description: '', code: 'He'},
  {id: 3, name: 'Lithium', description: '', code: 'Li'},
  {id: 4, name: 'Beryllium', description: '', code: 'Be'},
  {id: 5, name: 'Boron', description: '', code: 'B'},
  {id: 6, name: 'Carbon', description: '', code: 'C'},
  {id: 7, name: 'Nitrogen', description: '', code: 'N'},
  {id: 8, name: 'Oxygen', description: '', code: 'O'},
  {id: 9, name: 'Fluorine', description: '', code: 'F'},
  {id: 10, name: 'Neon', description: '', code: 'Ne'},
  {id: 11, name: 'Sodium', description: '', code: 'Na'},
  {id: 12, name: 'Magnesium', description: '', code: 'Mg'},
  {id: 13, name: 'Aluminum', description: '', code: 'Al'},
  {id: 14, name: 'Silicon', description: '', code: 'Si'},
  {id: 15, name: 'Phosphorus', description: '', code: 'P'},
  {id: 16, name: 'Sulfur', description: '', code: 'S'},
  {id: 17, name: 'Chlorine', description: '', code: 'Cl'},
  {id: 18, name: 'Argon', description: '', code: 'Ar'},
  {id: 19, name: 'Potassium', description: '', code: 'K'},
  {id: 20, name: 'Calcium', description: '', code: 'Ca'},
];
