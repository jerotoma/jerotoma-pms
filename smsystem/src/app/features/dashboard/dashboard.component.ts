import {Component, OnDestroy, OnInit} from '@angular/core';
import { NbThemeService } from '@nebular/theme';
import { takeWhile } from 'rxjs/operators' ;

import { DashboardService, SecurityClearanceService, AuthService } from 'app/services';
import { DashboardCounter, USER_ROLE, Auth } from 'app/models';
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
  private userRole: USER_ROLE;
  private userRoles: USER_ROLE[];

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
  classCounterCard: CounterCardSettings = {
    title: 'Classes',
    iconClass: 'book-outline',
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
    this.classCounterCard,
    this.studentCounterCard,
    this.parentCounterCard,
    this.staffCounterCard,
  ];

  counterCardsByThemes: {
    default?: CounterCardSettings[];
    cosmic?: CounterCardSettings[];
    corporate?: CounterCardSettings[];
    dark?: CounterCardSettings[];
  } = {
    default: [],
    cosmic: [],
    corporate: [],
    dark: [],
  };

  constructor(
    private dashboardService: DashboardService,
    private authService: AuthService,
    private securityClearanceService: SecurityClearanceService,
    private themeService: NbThemeService) {}

  ngOnInit(): void {
    this.loadCurrentUser();
  }

  loadCurrentUser() {
    this.securityClearanceService.loadCurrentUser();
    this.authService.getAuthenticatedUser().subscribe((auth: Auth) => {
      this.securityClearanceService.set(auth.roles);
      this.loadDashboardCounters();
    });
  }

  ngOnDestroy() {
    this.alive = false;
  }

  loadDashboardCounters() {
    this.dashboardService.getDashboardCount().subscribe((result: DashboardCounter) => {
      this.studentCounterCard.count = result.studentCount;
      this.parentCounterCard.count = result.parentCount;
      this.staffCounterCard.count = result.staffCount;
      if (this.isAdminsOrExecutive || this.isAdmin) {
        this.teacherCounterCard.count = result.teacherCount;
        this.commonStatusCardsSet = this.commonStatusCardsSet.filter((counter: CounterCardSettings) => counter.title !== 'Classes');
      } else if (this.isTeacher) {
        this.classCounterCard.count = result.classCount;
        this.commonStatusCardsSet = this.commonStatusCardsSet.filter((counter: CounterCardSettings) => counter.title !== 'Teachers');
      }
      this.themeService.getJsTheme()
        .pipe(takeWhile(() => this.alive))
        .subscribe(theme => {
          this.counterCardsByThemes[theme.name] = this.commonStatusCardsSet;
          this.counterCards = this.counterCardsByThemes[theme.name];
      });
    });
  }

  get hasResult() {
    return this.securityClearanceService.hasResult;
  }

  get isAdminsOrExecutive(): boolean {
    return this.securityClearanceService.isAdminsOrExecutive;
  }

  get isAdmin() {
    return this.securityClearanceService.isAdmin;
  }

  get isStaff() {
    return this.securityClearanceService.isStaff;
  }

  get isTeacher() {
    return this.securityClearanceService.isTeacher;
  }

  get isStudent() {
    return this.securityClearanceService.isStudent;
  }
}
