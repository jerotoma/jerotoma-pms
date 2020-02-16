
import { Injectable } from '@angular/core';
import { NbThemeService } from '@nebular/theme';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { SystemConfig, UserPreference, Theme, ResponseWrapper } from 'app/models';
import { THEMES, APP_CONSTANTS } from 'app/utils';
import { END_POINTS, QueryParam } from 'app/utils';

@Injectable({
  providedIn: 'root',
})
export class ThemeService {

  themes = THEMES;
  userPreferenceTheme: string = APP_CONSTANTS.userPreferenceTheme;
  currentTheme = 'default';
  mTheme: Theme = null;
  userPictureOnly: boolean = false;
  overrideUserTheme: boolean = false;
  user: any;
  systemConfig: SystemConfig = null;
  overrideSystemConfig: SystemConfig = null;
  userPreference: UserPreference = null;

  constructor(
    private http: HttpClient,
    private themeService: NbThemeService) { }

  getCurrentSystemTheme(): Observable<any> {
    return this.http.get<any>(`${END_POINTS.pubThemes}`)
      .pipe(map((resp: ResponseWrapper) => {
        if (resp.success) {
          this.mTheme = resp.data;
          this.systemConfig = this.mTheme.mapSystemConfigs ? this.mTheme.mapSystemConfigs.currentTheme : null;
          if (this.systemConfig) {
            this.currentTheme =  this.systemConfig.value;
            this.themeService.changeTheme(this.currentTheme);
          }
        }
        return resp.success;
      }));
  }
  getUserAndSystemThemes(): Observable<any> {
    return this.http.get(`${END_POINTS.themes}`)
      .pipe(map( (resp: ResponseWrapper ) => {
        if (resp.success) {
          this.mTheme = resp.data;
           this.systemConfig = this.mTheme.mapSystemConfigs ? this.mTheme.mapSystemConfigs.currentTheme : null;
          this.userPreference = this.mTheme.mapUserPreferences ? this.mTheme.mapUserPreferences.currentUserTheme : null;
          this.overrideSystemConfig = this.mTheme.mapSystemConfigs ? this.mTheme.mapSystemConfigs.overrideUserTheme : null;
          this.currentTheme =  this.userPreference && this.userPreference.value ? this.userPreference.value : this.systemConfig.value;
          if (this.overrideSystemConfig) {
            this.overrideUserTheme  = this.overrideSystemConfig.value === 'true';
          }
          if (this.overrideUserTheme) {
              this.themeService.changeTheme(this.systemConfig && this.systemConfig.value ? this.systemConfig.value : 'default');
          } else {
            this.themeService.changeTheme(this.currentTheme);
          }
        }
        return resp.success;
      }));
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server error');
  }
}
