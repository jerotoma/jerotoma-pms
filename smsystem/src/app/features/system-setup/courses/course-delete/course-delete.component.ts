import { Component, OnInit} from '@angular/core';

import { NbDialogRef } from '@nebular/theme';
import { CourseService,  ModalService } from 'app/services';
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
    private courseService: CourseService,
    private modalService: ModalService,
    protected ref: NbDialogRef<CourseDeleteComponent>) {}
  ngOnInit() {
    this.confirmed = this.action === 'delete';
  }

  deleteCourse() {
    if (this.confirmed) {
    this.courseService.deleteCourse(parseInt(this.courseId, 10))
      .subscribe((result: any ) => {
        if (result) {
         this.modalService.openSnackBar('Course has been deleted', 'success');
        }
      });
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
