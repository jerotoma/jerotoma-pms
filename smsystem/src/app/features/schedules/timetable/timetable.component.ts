import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormBuilder, Validators, FormGroup, FormArray, FormControl } from '@angular/forms';
import {
  TimeScope,
  Timetable,
  Event,
  TimetableRenderer,
} from 'app/timetable';
import {
  ClassView,
  TimeSlot,
  WeekDay,
  AcademicYear,
  SystemConfig,
  AcademicLevel,
  Program,
  } from 'app/models';
import {
  SystemConfigService,
  ClassService,
  ProgramService,
  AcademicLevelService,
  AcademicYearService,
  ModalService,
} from 'app/services';
import { APP_CONSTANTS, DAYS, getWeekDay, getDateTime, OPEN_CLOSE_ANIMATION } from 'app/utils';

@Component({
  selector: 'app-timetable',
  animations: OPEN_CLOSE_ANIMATION,
  templateUrl: './timetable.component.html',
  styleUrls: ['./timetable.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class TimetableComponent implements OnInit {
  timeSlot: TimeSlot;
  timeSlots: TimeSlot[];
  events: Event[] = [];
  event: Event;
  timetableForm: FormGroup;
  isLoading: boolean;
  timetable: Timetable;
  timeTableRenderer: TimetableRenderer;
  timeScope: TimeScope;

  academicYearId: number;
  programId: number;
  academicLevelId: number;
  academicYear: AcademicYear;
  academicYears: AcademicYear[];
  academicLevels: AcademicLevel[];
  programs: Program[];
  currentAcademicYear: string = APP_CONSTANTS.currentAcademicYear;

  constructor(
    private systemConfigService: SystemConfigService,
    private classService: ClassService,
    private modalService: ModalService,
    private academicYearService: AcademicYearService,
    private programService: ProgramService,
    private academicLevelService: AcademicLevelService,
    private formBuilder: FormBuilder,
  ) { }

  ngOnInit() {
    this.loadForm();
    this.loadAcademicYears();
    this.loadPrograms();
  }

  onSubmit() {
    if (!this.timetableForm.valid) {
      this.modalService.openSnackBar('Academic Year or Level must have a value to continue', 'info');
      return;
    }
    const param = {
      programId: this.timetableForm.get('programId').value,
      academicLevelId: this.timetableForm.get('academicLevelId').value,
      academicYearId: this.timetableForm.get('academicYearId').value,
    };
    this.loadClasses(param.programId, param.academicLevelId, param.academicYearId);
  }

  loadAcademicLevelsByProgramId(programId: number) {
    this.academicLevels = [];
    this.academicLevelService.loadAcademicLevelsByProgramId(programId)
    .subscribe((academicLevels: AcademicLevel[] ) => {
      if (academicLevels) {
        this.academicLevels = academicLevels;
      }
    });
  }

  loadPrograms() {
    this.programService.loadProgramList()
      .subscribe((programs: Program[]) => {
        if (programs) {
          this.programs = programs;
        }
      });
  }

  loadAcademicYears() {
    this.academicYearService.getAcademicYears()
    .subscribe((academicYears: AcademicYear[]) => {
      if (academicYears) {
        this.academicYears = academicYears;
        this.loadTimeTableByCurrentAcademicYear(this.currentAcademicYear);
      }
    });
  }

  drawTimetable(events: Event[]) {
    this.timetable =  new Timetable();
    this.timetable.setScope(8, 16);
    this.timetable.addWeekDays(DAYS);
    this.timetable.addEvents(events);
    this.timeTableRenderer = new TimetableRenderer(this.timetable);
    this.timeTableRenderer.draw('.timetable');
  }

  loadClasses(programId: number, academicLevelId: number, academicYearId: number) {
    this.isLoading = true;
    this.classService.loadJClassesByParams(programId, academicLevelId, academicYearId)
      .subscribe((mClasses: ClassView[]) => {
        this.isLoading = false;
        this.events = [];
        if (mClasses && mClasses.length > 0) {
          mClasses.forEach( (mClass, index) => {
            const startTime =  getDateTime(mClass.meetingTime.startTime);
            const endTime = getDateTime(mClass.meetingTime.endTime);
            const courseName = mClass.course.name + ' (' + mClass.course.code + ')';
            const weekDay = new WeekDay(mClass.meetingTime.workDay.dayId, getWeekDay(mClass.meetingTime.workDay.dayId));
            const content = '<p> Room: ' + mClass.room.name + ' (' + mClass.room.code + ')</p><p> Teacher: ' + mClass.teacher.fullName + '</p><p> Time: ' + mClass.meetingTime.time + '</p>';
            this.events.push(new Event(courseName, weekDay, startTime, endTime, content));
          });
        }
        this.drawTimetable(this.events);
      }, error => {
        this.isLoading = false;
      });
  }

  loadForm() {
    this.timetableForm = this.formBuilder.group({
      academicYearId: ['', Validators.required],
      academicLevelId: ['', Validators.required],
      programId: ['', Validators.required],
    });
    this.onChanges();
  }

  onChanges() {
    this.timetableForm.get('programId').valueChanges.subscribe(programId => {
      if (programId) {
        this.programId = programId;
        this.academicLevelId = null;
        this.timetableForm.patchValue({
            academicLevelId: null,
        });
        this.loadAcademicLevelsByProgramId(programId);
      }
    });
    this.timetableForm.get('academicYearId').valueChanges.subscribe((academicYearId: number) => {
      if (academicYearId != null) {
        this.academicYears.forEach(academicYear => {
          if (academicYear.id === academicYearId) {
            this.academicYear = academicYear;
          }
        });
      }
    });
  }

  loadTimeTableByCurrentAcademicYear(uniqueKey: string) {
    this.systemConfigService.findSystemConfigByKey(uniqueKey)
    .subscribe((data: SystemConfig ) => {
      if (data) {
        this.timetableForm.patchValue({
          academicYearId: parseInt(data.value, 10),
      });
      }
    }, error => {

    });
  }

}
