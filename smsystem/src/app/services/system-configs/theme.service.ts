
import { Injectable } from '@angular/core';
import { NbThemeService } from '@nebular/theme';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Theme, ResponseWrapper } from 'app/models';
import { THEMES, API_END_POINTS} from 'app/utils';

@Injectable({
  providedIn: 'root',
})
export class ThemeService {

  themes = THEMES;
  systemTheme: string = 'default';
  userTheme: string = 'default';
  overrideUserTheme: boolean = false;
  mTheme: Theme = null;

  constructor(
    private http: HttpClient,
    private themeService: NbThemeService) { }

  getCurrentSystemTheme(): Observable<any> {
    return this.http.get(`${API_END_POINTS.pubThemes}`)
      .pipe(map((resp: ResponseWrapper) => {
        if (resp.success) {
          this.mTheme = resp.data;
         if (this.mTheme && this.mTheme.systemTheme) {
            this.systemTheme =  this.mTheme.systemTheme;
          }
          this.themeService.changeTheme(this.systemTheme);
        }
        return { systemTheme: this.systemTheme };
      }));
  }
  getUserAndSystemThemes(): Observable<any> {
    return this.http.get(`${API_END_POINTS.themes}`)
      .pipe(map( (resp: ResponseWrapper ) => {
        if (resp.success) {
          this.mTheme = resp.data;
          this.overrideUserTheme = false;
          this.userTheme =  this.userTheme;
          if (this.mTheme && this.mTheme.userTheme && !this.mTheme.overrideUserTheme) {
            this.userTheme =  this.mTheme.userTheme;
            this.overrideUserTheme = this.mTheme.overrideUserTheme;
            this.themeService.changeTheme(this.userTheme);
          } else if (this.mTheme && this.mTheme.systemTheme) {
            this.systemTheme =  this.mTheme.systemTheme;
            this.overrideUserTheme = this.mTheme.overrideUserTheme;
            if (this.mTheme && this.mTheme.userTheme) {
              this.userTheme =  this.mTheme.userTheme;
            }
            this.themeService.changeTheme(this.systemTheme);
          } else {
            this.themeService.changeTheme(this.systemTheme);
          }
        }
        return {
          systemTheme: this.systemTheme,
          overrideUserTheme: this.overrideUserTheme,
          userTheme: this.userTheme,
        };
      }));
  }
}
