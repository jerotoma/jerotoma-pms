import * as moment from 'moment';

export function DateFormatter(date: Date, format: string = 'YYYY/MM/DD', isStrict: boolean = true): moment.Moment {
  return moment(date, format, isStrict);
}

export function StringDateFormatter(date: string, format = 'YYYY/MM/DD', isStrict: boolean = true): moment.Moment {
  return moment(date, format, isStrict);
}
