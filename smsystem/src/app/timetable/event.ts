export class Event {
  constructor(
    public name: any,
    public weekDay: any,
    public startTime: Date,
    public endTime: Date,
    public content?: any,
    public options?: any) {
    this.options = options ? options : undefined;
  }

  setName(name: string) {
    this.name = name;
  }

  setStartTime(startTime: Date) {
    this.startTime = startTime;
  }

  setEndTime(endTime: Date) {
    this.endTime = endTime;
  }

  setOptions(options: Object) {
    this.options = options;
  }
  setContent(content: any) {
    this.content = content;
  }
}
