import * as moment from 'moment';

export function DateFormatter(date: Date, format = 'YYYY/MM/DD'): moment.Moment {
  return moment(date, format, true);
}
