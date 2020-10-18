import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { PageEvent } from '@angular/material/paginator';
import {NbDialogService } from '@nebular/theme';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

import { CompletionOrderCreateComponent } from '../completion-order-create/completion-order-create.component';
import { CompletionOrderDeleteComponent } from '../completion-order-delete/completion-order-delete.component';
import { CompletionOrder, ResponseWrapper } from 'app/models';
import { CompletionOrderService } from 'app/services';
import { APP_ACTION_TYPE } from 'app/utils';
/**
 * @title Table with pagination
 */
@Component({
  selector: 'app-courses-view',
  styleUrls: ['completion-orders-view.component.scss'],
  templateUrl: 'completion-orders-view.component.html',
})
export class CompletionOrdersViewComponent implements OnInit {

  title: string = 'List of CompletionOrders';
  course: CompletionOrder;
  hidePageSize: boolean = false;
  isLoading: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'name', 'completionOrder', 'createdOn', 'action'];
  dataSource: MatTableDataSource<CompletionOrder> = new MatTableDataSource<CompletionOrder>();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(
    private courseService: CompletionOrderService,
    private dialogService: NbDialogService,
    ) {
  }
  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.loadCompletionOrders();
  }

  open() {
    this.dialogService.open(CompletionOrderCreateComponent, {
      context: {
        title: 'Add New Completion Order',
        action: APP_ACTION_TYPE.create,
      },
    }).onClose.subscribe(_data => {
      this.loadCompletionOrders();
    });
  }

  loadCompletionOrders() {
    this.isLoading = true;
    this.courseService.getCompletionOrders()
      .subscribe((completionOrders: CompletionOrder[]) => {
        this.isLoading = false;
        if (completionOrders) {
          this.dataSource = new MatTableDataSource<CompletionOrder>(completionOrders);
        }
      });
  }
  edit(completionOrder: CompletionOrder) {
    this.dialogService.open(CompletionOrderCreateComponent, {
      context: {
        title: 'Edit CompletionOrder',
        action: APP_ACTION_TYPE.edit,
        completionOrder: completionOrder,
      },
    }).onClose.subscribe(_data => {
      this.loadCompletionOrders();
    });
  }
  delete(completionOrder: CompletionOrder) {
    this.dialogService.open(CompletionOrderDeleteComponent, {
      context: {
        action: APP_ACTION_TYPE.delete,
        completionOrder: completionOrder,
      },
    }).onClose.subscribe(_data => {
      this.loadCompletionOrders();
    });
  }
}
