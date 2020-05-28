import { Timetable } from './timetable';


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
    for (let k = 0; k < this.timetable.locations.length; k++) {
      const liNode = ulNode.appendChild(document.createElement('li'));
      const spanNode = liNode.appendChild(document.createElement('span'));
      spanNode.className = 'row-heading';
      spanNode.textContent = this.timetable.locations[k];
    }
  }
  appendTimetableSection() {
    const sectionNode = this.container.appendChild(document.createElement('section'));
    const headerNode = this.appendColumnHeaders(sectionNode);
    const timeNode = sectionNode.appendChild(document.createElement('time'));
    timeNode.className = 'syncscroll';
    timeNode.setAttribute('name', 'scrollheader');
    const width = headerNode.scrollWidth + 'px';
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
    for (let k = 0; k < this.timetable.locations.length; k++) {
      const liNode = ulNode.appendChild(document.createElement('li'));
      this.appendLocationEvents(this.timetable.locations[k], liNode);
    }
  }

  appendLocationEvents(location: string, node: HTMLElement) {
    for (let k = 0; k < this.timetable.events.length; k++) {
      const event = this.timetable.events[k];
      if (event.location === location) {
        this.appendEvent(event, node);
      }
    }
  }
  appendEvent(event: any, node: HTMLElement) {
    const hasOptions = event.options !== undefined;
     if (hasOptions) {
      this.hasURL = event.options.url !== undefined;
      this.hasAdditionalClass = event.options.class !== undefined;
      this.hasDataAttributes = event.options.data !== undefined;
      this.hasClickHandler = event.options.onClick !== undefined;
    }

    const elementType = this.hasURL ? 'a' : 'span';
    const eventNode = node.appendChild(document.createElement(elementType));
    const smallNode = eventNode.appendChild(document.createElement('small'));
    eventNode.title = event.name;

    if (this.hasURL) {
      eventNode.setAttribute('href', event.options.url);
    }

    if (this.hasDataAttributes && event.options.data) {
      Object.entries(event.options.data).forEach((value: any, key: any) => {
        eventNode.setAttribute('data-' + key, value);
      });
    }

    if (this.hasClickHandler) {
      eventNode.addEventListener('click', (e) => {
        event.options.onClick(event, this.timetable, e);
      });
    }

    eventNode.className = this.hasAdditionalClass ? 'time-entry ' + event.options.class : 'time-entry';
    eventNode.style.width = this.computeEventBlockWidth(event);
    eventNode.style.left = this.computeEventBlockOffset(event);
    smallNode.textContent = event.name;

    this.hasURL = false;
    this.hasAdditionalClass = false;
    this.hasDataAttributes = false;
    this.hasClickHandler = false;
  }
  computeEventBlockWidth(event) {
    const start = event.startDate;
    const end = event.endDate;
    const durationHours = this.computeDurationInHours(start, end);
    return durationHours / this.scopeDurationHours * 100 + '%';
  }
  computeDurationInHours(start: Date, end: Date) {
    return (end.getTime() - start.getTime()) / 1000 / 60 / 60;
  }
  computeEventBlockOffset(event: any) {
    const scopeStartHours = this.timetable.scope.hourStart;
    const eventStartHours = event.startDate.getHours() + (event.startDate.getMinutes() / 60);
    const hoursBeforeEvent =  this.timetable.getDurationHours(scopeStartHours, eventStartHours);
    return hoursBeforeEvent / this.scopeDurationHours * 100 + '%';
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
