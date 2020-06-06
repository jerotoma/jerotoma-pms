import { Component, OnInit, Output, Input, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { AcademicYear, SystemConfig } from 'app/models';
import { AcademicYearService, SystemConfigService } from 'app/services';
import { QueryParam, APP_CONSTANTS } from 'app/utils';


@Component({
  selector: 'app-current-academic-year',
  templateUrl: 'current-academic-year.component.html',
  styleUrls: ['current-academic-year.component.scss'],
})
export class CurrentAcademicYearComponent implements OnInit {

  @Input('allowUpdate') allowUpdate: boolean = false;
  @Input('title') title: string = 'Current Academic Year';
  @Output() onChange: EventEmitter<AcademicYear> = new EventEmitter();

  academicYear: AcademicYear;
  academicYears: AcademicYear[];
  currentAcademicYearForm: FormGroup;
  currentAcademicYear: string = APP_CONSTANTS.currentAcademicYear;

  constructor(
    private formBuilder: FormBuilder,
    private academicYearService: AcademicYearService,
    private systemConfigService: SystemConfigService) {

  }

  ngOnInit() {
      this.loadForm();
      this.loadAcademicYears();
  }

  loadForm() {
    this.currentAcademicYearForm = this.formBuilder.group({
      academicYearId: [null, Validators.required],
    });
    this.onFormChanges();
  }

  onFormChanges(): void {
    this.currentAcademicYearForm.get('academicYearId').valueChanges.subscribe((academicYearId: number) => {
      if (academicYearId) {
        const systemConfig: SystemConfig = {
            id: null,
            name: this.currentAcademicYear,
            value: '' + academicYearId,
        };
        if (this.allowUpdate && systemConfig) {
          this.updateSystemConfigChange(systemConfig);
        }
        this.setCurrentAcademicYear(academicYearId);
      }
    });
  }

  updateSystemConfigChange(systemConfig: SystemConfig) {
    this.systemConfigService.updateSystemConfig(systemConfig)
    .subscribe((data: SystemConfig ) => {
      if (data) {
        this.currentAcademicYearForm.patchValue({
          academicYearId: parseInt(data.value, 10),
        }, {emitEvent: false});
        this.setCurrentAcademicYear(parseInt(data.value, 10));
      }
    }, error => {
    });
  }

  loadAcademicYears() {
    this.academicYearService.getAcademicYears().subscribe((academicYears: AcademicYear[] ) => {
        if (academicYears) {
          this.academicYears = academicYears;
          this.loadSystemConfigChange(this.currentAcademicYear);
        }
      });
  }

  loadSystemConfigChange(uniqueKey: string) {
    this.systemConfigService.findSystemConfigByKey(uniqueKey)
    .subscribe((data: SystemConfig ) => {
      if (data) {
        this.currentAcademicYearForm.patchValue({
          academicYearId: parseInt(data.value, 10),
        }, {emitEvent: false});
        this.setCurrentAcademicYear(parseInt(data.value, 10));
      }
    }, error => {

    });
  }

  setCurrentAcademicYear(academicYearId: number) {
    this.academicYear = this.academicYears.find((academicYear) => academicYear.id === academicYearId);
    this.onChange.emit(this.academicYear);
  }
}
