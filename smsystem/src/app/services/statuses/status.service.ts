import { Injectable } from '@angular/core';
import { CompletionStatus } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class StatusService {

  constructor() { }

  getCompletionStatusClass(completionStatus: string): string {
    if (CompletionStatus.IN_PROGRESS === completionStatus) {
      return 'info';
    } else if (CompletionStatus.COMPLETED === completionStatus) {
      return 'success';
    } else if (CompletionStatus.NOT_STARTED === completionStatus) {
      return 'warning';
    }
    return 'danger';
  }
}
