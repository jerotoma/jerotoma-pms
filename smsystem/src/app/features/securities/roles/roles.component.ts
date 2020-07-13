import { Component, OnInit, ViewChild } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import {NbDialogService } from '@nebular/theme';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

import { RoleService } from 'app/services';
import { Role } from 'app/models';

import { RoleAssignationComponent} from './assign/role-assignation.component';

@Component({
  selector: 'app-roles',
  templateUrl: './roles.component.html',
  styleUrls: ['./roles.component.scss'],
})
export class RolesComponent implements OnInit {

  role: Role;
  roles: Role[];
  title: string = 'List of Roles';
  hidePageSize: boolean = false;
  isLoading: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'name', 'displayName', 'createdOn', 'action'];
  dataSource: MatTableDataSource<Role> = new MatTableDataSource<Role>();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(
    private roleService: RoleService,
    private dialogService: NbDialogService) { }

  ngOnInit() {
    this.loadRoles();
  }

  loadRoles() {
    this.isLoading = true;
    this.roleService.loadRoles()
      .subscribe((roles: Role[]) => {
        this.isLoading = false;
        if (roles) {
          this.dataSource = new MatTableDataSource<Role>(roles);
        }
      });
  }

  asignRole(role: Role) {
    this.dialogService.open(RoleAssignationComponent, {
      context: {
        title: 'Assign Role',
        action: 'assignRole',
        role: role,
      },
    }).onClose.subscribe(_data => {
      this.loadRoles();
    });
  }


  asignRoles() {
    this.dialogService.open(RoleAssignationComponent, {
      context: {
        title: 'Assign Roles',
        action: 'assignRoles',
        role: null,
      },
    }).onClose.subscribe(_data => {
      this.loadRoles();
    });
  }

}
