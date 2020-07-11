import { Injectable } from '@angular/core';
import { HttpClient, HttpEventType, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import { API_END_POINTS } from 'app/utils';
import { ResponseWrapper, Media } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class UploadService {

  constructor(private http: HttpClient) { }

  uploadFile(data?: any): Observable<Media> {
    return this.http.post(`${API_END_POINTS.uploads}`, data).pipe(map((resp: ResponseWrapper) => resp.data));
  }

  uploadProfileImageTrackProgress(userId: number, formData: FormData): Observable<any> {
    return this.http.post(`${API_END_POINTS.uploads}/users/${userId}/profile`, formData, {
      reportProgress: true,
      observe: 'events',
    }).pipe(catchError(this.errorMessage));
  }

  uploadFiles(data?: any): Observable<Media[]> {
    return this.http.post(`${API_END_POINTS.uploads}`, data).pipe(map((resp: ResponseWrapper) => resp.data));
  }

  deleteFile(mediaId: number): Observable<boolean> {
    return this.http.delete(`${API_END_POINTS.uploads}/${mediaId}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updateFile(data?: any): Observable<ResponseWrapper> {
    return this.http.put(`${API_END_POINTS.uploads}`, data).pipe(map((resp: ResponseWrapper) => resp));
  }

  updateFiles(data?: any): Observable<ResponseWrapper> {
    return this.http.put(`${API_END_POINTS.uploads}`, data).pipe(map((resp: ResponseWrapper) => resp));
  }

  uploadFileTrackProgress(data?: any): Observable<any> {
    return this.http.post(`${API_END_POINTS.uploads}`, data, {
      reportProgress: true,
      observe: 'events',
    }).pipe(catchError(this.errorMessage));
  }

  errorMessage(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(errorMessage);
  }

}
