import { Component, OnInit} from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDialogRef } from '@nebular/theme';
import { CourseService } from 'app/services';
import { ShowMessage } from 'app/models/messages/show-message.model';

@Component({
  selector: 'app-course-delete',
  templateUrl: 'course-delete.component.html',
  styleUrls: ['course-delete.component.scss'],
})
export class CourseDeleteComponent implements OnInit {
  courseId: string = '0';
  title: string = '';
  action: string = '';
  name: string = '';
  confirmed: boolean = false;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };

  constructor(
    private courseService:  CourseService,
    protected ref: NbDialogRef<CourseDeleteComponent>) {}
  ngOnInit() {
    this.confirmed = this.action === 'delete';
  }

  deleteCourse() {
    if (this.confirmed) {
    this.courseService.deleteCourse(parseInt(this.courseId, 10))
      .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
        const resp = result;
        const data = resp.body;
        const status = resp.status;
        if (status !== null && status === 200) {
          this.dismiss();
        } else {
          this.showMessage.success = false;
          this.showMessage.error = true;
          this.showMessage.message = data  ? data.message : '';
        }
      }, error => {
        this.showMessage.error = true;
        this.showMessage.success = false;
        this.showMessage.message = error ? error.error.message : '';
      }
);
    }
  }

  dismiss() {
    this.ref.close();
  }
  onConfirmed() {
    this.confirmed = true;
    this.deleteCourse();
  }

}
