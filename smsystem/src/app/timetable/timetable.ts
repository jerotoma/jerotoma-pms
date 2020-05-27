export class Timetable {
  constructor(
    public locations: Array<any> = [],
    public events: Array<any> = [],
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

  locationExistsIn(location: string) {
    if (!this.locations) {
      throw new Error('Locations can not be empty');
    }
    return this.locations.indexOf(location) !== -1;
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

  addLocations(newLocations: string[]): this {
    newLocations.forEach((loc) => {
      if (!this.locationExistsIn(loc)) {
        this.locations.push(loc);
      } else {
        throw new Error('Location already exists');
      }
    });
    return this;
  }

  addEvent(name: string, location: string, start: Date, end: Date, options: Object): this {
    if (!this.locationExistsIn(location)) {
      throw new Error('Unknown location');
    }
    if (!this.isValidTimeRange(start, end)) {
      throw new Error('Invalid time range: ' + JSON.stringify([start, end]));
    }
    this.events.push({
      name: name,
      location: location,
      startDate: start,
      endDate: end,
      options: options ? options : undefined,
    });
    return this;
  }

}

export interface TimeScope {
  hourEnd: number;
  hourStart: number;
}
