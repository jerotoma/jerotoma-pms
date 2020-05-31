import { Timetable } from './timetable';
import { WeekDay } from 'app/models';
import { Event } from './event';


export class TimetableRenderer {

  scopeDurationHours: number;
  container: HTMLElement;
  hasURL: boolean = false;
  prettyHour: string;
  hasAdditionalClass: boolean = false;
  hasDataAttributes: boolean = false;
  hasClickHandler: boolean = false;

  constructor(public timetable: Timetable) {}

  checkContainerPrecondition() {
    if (this.container === null) {
      throw new Error('Timetable container not found');
    }
  }
  appendTimetableAside() {
    const asideNode = this.container.appendChild(document.createElement('aside'));
    const asideULNode = asideNode.appendChild(document.createElement('ul'));
    this.appendRowHeaders(asideULNode);
  }
  appendRowHeaders(ulNode: HTMLElement) {
    for (let k = 0; k < this.timetable.weekDays.length; k++) {
      const liNode = ulNode.appendChild(document.createElement('li'));
      const spanNode = liNode.appendChild(document.createElement('span'));
      spanNode.className = 'row-heading';
      spanNode.textContent = this.timetable.weekDays[k].name;
    }
  }
  appendTimetableSection() {
    const sectionNode = this.container.appendChild(document.createElement('section'));
    const timeNode = sectionNode.appendChild(document.createElement('time'));
    const headerNode = this.appendColumnHeaders(timeNode);
    const width = headerNode.scrollWidth + 'px';
    headerNode.style.width = width;
    this.appendTimeRows(timeNode, width);
  }
  appendColumnHeaders(node: HTMLElement) {
    const headerNode = node.appendChild(document.createElement('header'));
    headerNode.className = 'syncscroll';
    headerNode.setAttribute('name', 'scrollheader');
    const headerULNode = headerNode.appendChild(document.createElement('ul'));
    let completed = false;
    let looped = false;
    for (let hour = this.timetable.scope.hourStart; !completed;) {
      const liNode = headerULNode.appendChild(document.createElement('li'));
      const spanNode = liNode.appendChild(document.createElement('span'));
      spanNode.className = 'time-label';
      spanNode.textContent = this.prettyFormatHour(hour, this.timetable.usingTwelveHour);

      if (hour === this.timetable.scope.hourEnd && (this.timetable.scope.hourStart !== this.timetable.scope.hourEnd || looped)) {
        completed = true;
      }
      if (++hour === 24) {
        hour = 0;
        looped = true;
      }
    }
    return headerNode;
  }
  appendTimeRows(node: HTMLElement, width: string) {
    const ulNode = node.appendChild(document.createElement('ul'));
    ulNode.style.width = width;
    ulNode.className = 'room-timeline';
    for (let k = 0; k < this.timetable.weekDays.length; k++) {
      const liNode = ulNode.appendChild(document.createElement('li'));
      this.appendWeekDayEvents(this.timetable.weekDays[k], liNode);
    }
  }

  appendWeekDayEvents(weekDay: WeekDay, node: HTMLElement) {
    for (let k = 0; k < this.timetable.events.length; k++) {
      const event = this.timetable.events[k];
      if (event.weekDay.name === weekDay.name) {
        this.appendEvent(event, node);
      }
    }
  }
  appendEvent(event: Event, node: HTMLElement) {
    const hasOptions = event.options !== undefined;
     if (hasOptions) {
      this.hasURL = event.options.url !== undefined;
      this.hasAdditionalClass = event.options.class !== undefined;
      this.hasDataAttributes = event.options.data !== undefined;
      this.hasClickHandler = event.options.onClick !== undefined;
    }

    const elementType = this.hasURL ? 'a' : 'div';
    const eventDivNode = node.appendChild(document.createElement('div'));
    const eventDivOrAnchorNode = eventDivNode.appendChild(document.createElement(elementType));
    const smallNode = eventDivOrAnchorNode.appendChild(document.createElement('small'));
    eventDivNode.className = 'event-container';
    eventDivNode.title = event.name;

    if (this.hasURL) {
      eventDivOrAnchorNode.setAttribute('href', event.options.url);
    }

    if (this.hasDataAttributes && event.options.data) {
      Object.entries(event.options.data).forEach((value: any, key: any) => {
        eventDivNode.setAttribute('data-' + key, value);
      });
    }

    if (this.hasClickHandler) {
      eventDivNode.addEventListener('click', (e) => {
        event.options.onClick(event.name, this.timetable, e);
      });
    }

    eventDivNode.className = this.hasAdditionalClass ? 'time-entry ' + event.options.class : 'time-entry';
    eventDivNode.style.width = this.computeEventBlockWidth(event);
    eventDivNode.style.left = this.computeEventBlockOffset(event);
    const eventTitle = '<h4 class="event-title">' + event.name + '</h4>';
    let eventContent = '';
    if (event.content) {
      eventContent = '<div class="event-content">' + event.content + '</div>';
    }
    smallNode.innerHTML = eventTitle + eventContent;

    this.hasURL = false;
    this.hasAdditionalClass = false;
    this.hasDataAttributes = false;
    this.hasClickHandler = false;
  }
  computeEventBlockWidth(event: Event) {
    const start = event.startTime;
    const end = event.endTime;
    const durationHours = this.computeDurationInHours(start, end);
    return ((durationHours / this.scopeDurationHours) * 100) + '%';
  }
  computeDurationInHours(start: Date, end: Date) {
    return (end.getTime() - start.getTime()) / 1000 / 60 / 60;
  }
  computeEventBlockOffset(event: Event) {
    const scopeStartHours = this.timetable.scope.hourStart;
    const eventStartHours = event.startTime.getHours() + (event.startTime.getMinutes() / 60);
    const hoursBeforeEvent =  this.timetable.getDurationHours(scopeStartHours, eventStartHours);
    return ((hoursBeforeEvent / this.scopeDurationHours) * 100) + '%';
  }

  emptyNode(node: HTMLElement) {
    while (node.firstChild) {
      node.removeChild(node.firstChild);
    }
  }

  prettyFormatHour(hour: number, usingTwelveHour: boolean) {
    if (usingTwelveHour) {
            const period = hour >= 12 ? 'PM' : 'AM';
            this.prettyHour = ((hour + 11) % 12 + 1) + ':00' + period;
    } else {
      const prefix = hour < 10 ? '0' : '';
      this.prettyHour = prefix + hour + ':00';
    }
    return this.prettyHour;
  }

  draw(selector: string) {
    this.scopeDurationHours = this.timetable.getDurationHours(this.timetable.scope.hourStart, this.timetable.scope.hourEnd);
    this.container = document.querySelector(selector);
    this.checkContainerPrecondition();
    this.emptyNode(this.container);
    this.appendTimetableAside();
    this.appendTimetableSection();
    // this.syncscroll.reset();
  }
}
