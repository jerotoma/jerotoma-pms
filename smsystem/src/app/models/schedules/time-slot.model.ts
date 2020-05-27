import { Time, ClassView } from 'app/models';
import { WEEK_DAY } from 'app/utils';


export class TimeSlot {
  constructor (
    public day: WEEK_DAY,
    public startTime: Time,
    public endTime: Time,
    public classView?: ClassView,
    ) {}
}
