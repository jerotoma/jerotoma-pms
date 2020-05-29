import * as moment from 'moment';
import { WEEK_DAY } from 'app/utils/constants';
import { Time } from 'app/models';

export function DateFormatter(date: Date, format: string = 'YYYY/MM/DD', isStrict: boolean = true): moment.Moment {
  if (!date) {
    return null;
  }
  return moment(date, format, isStrict);
}

export function StringDateFormatter(date: string, format: string = 'YYYY/MM/DD', isStrict: boolean = true): moment.Moment {
  if (!date) {
    return null;
  }
  return moment(date, format, isStrict);
}

export function getWeekDay(enumWeekDay: number) {
  let dayName = '';
  switch (enumWeekDay) {
    case WEEK_DAY.FRIDAY :
      dayName = 'Friday';
      break;
    case WEEK_DAY.THURSDAY :
      dayName = 'Thursday';
      break;
    case WEEK_DAY.WEDNESDAY :
      dayName = 'Wednesday';
      break;
    case WEEK_DAY.TUESDAY:
      dayName = 'Tuesday';
      break;
    case WEEK_DAY.MONDAY :
      dayName = 'Monday';
      break;
    case WEEK_DAY.SUNDAY :
      dayName = 'Sunday';
      break;
    case WEEK_DAY.SATURDAY :
      dayName = 'Saturday';
      break;
  }
  return dayName;
}

export function  getDateTime(time: Time) {
  const currentDate = new Date();
  return new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDay(), time.hour, time.minute);
}
