import { Day } from 'app/models';

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
  new Day(WEEK_DAY.MONDAY, 'Monday'),
  new Day(WEEK_DAY.TUESDAY, 'Tuesday'),
  new Day(WEEK_DAY.WEDNESDAY, 'Wednesday'),
  new Day(WEEK_DAY.THURSDAY, 'Thursday'),
  new Day(WEEK_DAY.FRIDAY, 'Friday'),
  new Day(WEEK_DAY.SATURDAY, 'Saturday'),
  new Day(WEEK_DAY.SUNDAY, 'Sunday'),
];
