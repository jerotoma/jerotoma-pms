import { Day } from 'app/models';

export enum DAY {
  MONDAY = 1,
  TUESDAY = 2,
  WEDNESDAY = 3,
  THURSDAY = 4,
  FRIDAY = 5,
  SATURDAY = 6,
  SUNDAY = 7,
}

export const DAYS = [
  new Day(DAY.MONDAY, 'Monday'),
  new Day(DAY.TUESDAY, 'Tuesday'),
  new Day(DAY.WEDNESDAY, 'Wednesday'),
  new Day(DAY.THURSDAY, 'Thursday'),
  new Day(DAY.FRIDAY, 'Friday'),
  new Day(DAY.SATURDAY, 'Saturday'),
  new Day(DAY.SUNDAY, 'Sunday'),
];
