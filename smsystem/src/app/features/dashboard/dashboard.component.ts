import {Component, OnDestroy, OnInit} from '@angular/core';
import { NbThemeService } from '@nebular/theme';
import { takeWhile } from 'rxjs/operators' ;
import { SolarData } from '../../@core/data/solar';

import { DashboardService } from 'app/services';
import { DashboardCounter, ResponseWrapper } from 'app/models';
import { QueryParam } from 'app/utils';

interface CounterCardSettings {
  title: string;
  count?: number;
  iconClass: string;
  type: string;
}

@Component({
  selector: 'app-dashboard',
  styleUrls: ['./dashboard.component.scss'],
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent implements OnInit, OnDestroy {

  private alive = true;

  param: QueryParam = {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: 'teacher',
  };

  solarValue: number;
  teacherCounterCard: CounterCardSettings = {
    title: 'Teachers',
    iconClass: 'people-outline',
    type: 'primary',
    count: 56,
  };
  studentCounterCard: CounterCardSettings = {
    title: 'Students',
    iconClass: 'people-outline',
    type: 'success',
    count: 230,
  };
  parentCounterCard: CounterCardSettings = {
    title: 'Parents',
    iconClass: 'people-outline',
    type: 'info',
    count: 200,
  };
  staffCounterCard: CounterCardSettings = {
    title: 'Staffs',
    iconClass: 'people-outline',
    type: 'warning',
    count: 20,
  };

  counterCards: CounterCardSettings[];

  commonStatusCardsSet: CounterCardSettings[] = [
    this.teacherCounterCard,
    this.studentCounterCard,
    this.parentCounterCard,
    this.staffCounterCard,
  ];

  counterCardsByThemes: {
    default: CounterCardSettings[];
    cosmic: CounterCardSettings[];
    corporate: CounterCardSettings[];
    dark: CounterCardSettings[];
  } = {
    default: this.commonStatusCardsSet,
    cosmic: this.commonStatusCardsSet,
    corporate: [
      {
        ...this.teacherCounterCard,
        type: 'warning',
      },
      {
        ...this.studentCounterCard,
        type: 'primary',
      },
      {
        ...this.parentCounterCard,
        type: 'danger',
      },
      {
        ...this.staffCounterCard,
        type: 'info',
      },
    ],
    dark: this.commonStatusCardsSet,
  };

  constructor(
    private dashboardService: DashboardService,
    private themeService: NbThemeService,
    private solarService: SolarData) {

    this.solarService.getSolarData()
      .pipe(takeWhile(() => this.alive))
      .subscribe((data) => {
        this.solarValue = data;
      });
  }
  ngOnInit(): void {
    this.loadDashboardCounters();
  }

  ngOnDestroy() {
    this.alive = false;
  }

  loadDashboardCounters() {
    this.dashboardService.getDashboardCount().subscribe((result: DashboardCounter) => {
      this.teacherCounterCard.count = result.teacherCount;
      this.studentCounterCard.count = result.studentCount;
      this.parentCounterCard.count = result.parentCount;
      this.staffCounterCard.count = result.staffCount;
      this.themeService.getJsTheme()
        .pipe(takeWhile(() => this.alive))
        .subscribe(theme => {
          this.counterCards = this.counterCardsByThemes[theme.name];
      });
    });
  }
}
