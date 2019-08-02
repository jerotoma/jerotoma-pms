import {Component, Input, OnDestroy} from '@angular/core';

@Component({
  selector: 'app-user-table',
  styleUrls: ['./user-table.component.scss'],
  templateUrl: './user-table.component.html',
})
export class UserTableComponent implements OnDestroy {

  @Input('columns') tableColumns: string[] = [];
  @Input('rows') tableRows = [];

  constructor() {}
  ngOnDestroy() {}
}
