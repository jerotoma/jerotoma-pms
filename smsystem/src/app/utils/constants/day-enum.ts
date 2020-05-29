import { WeekDay } from 'app/models';

export enum WEEK_DAY {
  MONDAY = 1,
  TUESDAY = 2,
  WEDNESDAY = 3,
  THURSDAY = 4,
  FRIDAY = 5,
  SATURDAY = 6,
  SUNDAY = 7,
}

export const DAYS = [
  new WeekDay(WEEK_DAY.MONDAY, 'Monday'),
  new WeekDay(WEEK_DAY.TUESDAY, 'Tuesday'),
  new WeekDay(WEEK_DAY.WEDNESDAY, 'Wednesday'),
  new WeekDay(WEEK_DAY.THURSDAY, 'Thursday'),
  new WeekDay(WEEK_DAY.FRIDAY, 'Friday'),
  new WeekDay(WEEK_DAY.SATURDAY, 'Saturday'),
  new WeekDay(WEEK_DAY.SUNDAY, 'Sunday'),
];
