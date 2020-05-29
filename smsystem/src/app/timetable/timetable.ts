import { Event } from './event';
import { WeekDay } from 'app/models';

export class Timetable {
  constructor(
    public weekDays: WeekDay[] = [],
    public events?: Event[],
    public usingTwelveHour: boolean = true,
    public scope: TimeScope = {
        hourStart: 8,
        hourEnd: 17,
    },
    ) {}

  isValidHourRange(start: number, end: number) {
    return this.isValidHour(start) && this.isValidHour(end);
  }

  isValidHour(hour: number) {
    return this.isInHourRange(hour);
  }

  isInHourRange(number: number) {
    return number >= 0 && number < 24;
  }

  weekDayExistsIn(weekDay: WeekDay) {
    if (!this.weekDays) {
      throw new Error('Week Days can not be empty');
    }
    for (let i = 0; i < this.weekDays.length; i++) {
      if (this.weekDays[i].day === weekDay.day && this.weekDays[i].name === weekDay.name) {
        return true;
      }
    }
    return false;
  }

  isValidTimeRange(start: Date, end: Date) {
    const correctTypes = start instanceof Date && end instanceof Date;
    const correctOrder = start < end;
    return correctTypes && correctOrder;
  }

  getDurationHours(startHour: number, endHour: number) {
    return endHour >= startHour ? endHour - startHour : 24 + endHour - startHour;
  }

  setScope(start: number , end: number ): this {
    if (this.isValidHourRange(start, end)) {
      this.scope.hourStart = start;
      this.scope.hourEnd = end;
    } else {
      throw new RangeError('Timetable scope should consist of (start, end) in whole hours from 0 to 23');
    }
    return this;
  }

  addWeekDays(newWeekDays: WeekDay[]): this {
    newWeekDays.forEach((weekDay) => {
      if (!this.weekDayExistsIn(weekDay)) {
        this.weekDays.push(weekDay);
      } else {
        throw new Error('WeekDay already exists');
      }
    });
    return this;
  }

  addEvent(name: string, weekDay: WeekDay, start: Date, end: Date, options?: Object): this {
    this.events = [];
    if (!this.weekDayExistsIn(weekDay)) {
      throw new Error('Unknown weekDay');
    }
    if (!this.isValidTimeRange(start, end)) {
      throw new Error('Invalid time range: ' + JSON.stringify([start, end]));
    }
    this.events.push(new Event(name, weekDay, start, end, options));
    return this;
  }

  addEvents(events: Event[]): this {
    this.events = [];
    if (events && events.length > 0) {
      events.forEach((event, index) => {
        if (!this.weekDayExistsIn(event.weekDay)) {
          throw new Error('Unknown weekDay');
        }
        if (!this.isValidTimeRange(event.startTime, event.endTime)) {
          throw new Error('Invalid time range: ' + JSON.stringify([event.startTime, event.endTime]));
        }
        this.events.push(event);
      });
    }
    return this;
  }
}

export interface TimeScope {
  hourEnd: number;
  hourStart: number;
}



