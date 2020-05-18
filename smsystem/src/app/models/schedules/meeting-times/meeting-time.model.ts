import { Time, WorkDay } from 'app/models';

export interface MeetingTime {
  id: number;
  time: string;
  workDayId: number;
  workDay: WorkDay;
  startTime: Time;
  endTime: Time;
}
