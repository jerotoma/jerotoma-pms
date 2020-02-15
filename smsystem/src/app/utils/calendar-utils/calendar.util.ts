import * as moment from 'moment';

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
