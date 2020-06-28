import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { END_POINTS, QueryParam } from 'app/utils';
import { ResponseWrapper, Media } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class MediaService {

  constructor(private http: HttpClient) { }

  getMedia(mediaId: number): Observable<Media> {
    return this.http
      .get(`${END_POINTS.uploads}/${mediaId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadMedia(): Observable<Media[]> {
    return this.http.get(`${END_POINTS.uploads}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadMediaChunks(): Observable<Array<Media[]>> {
    return this.http.get(`${END_POINTS.uploads}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  getMediaPaginated(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get(`${END_POINTS.uploads}/paginated?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}&userType=${param.userType}`)
        .pipe(map((resp: ResponseWrapper) => resp));
  }

  createMedia(data?: any): Observable<Media> {
    return this.http.post(`${END_POINTS.uploads}`, data)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  deleteMedia(mediaId: number): Observable<any> {
    return this.http.delete(`${END_POINTS.uploads}/${mediaId}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updateMedia(data?: any): Observable<Media> {
    return this.http.put(`${END_POINTS.uploads}`, data)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  search(data: any, param: QueryParam): Observable<ResponseWrapper> {
    return this.http.post(`${END_POINTS.uploads}/search?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}&userType=${param.userType}`, data)
    .pipe(map((resp: ResponseWrapper) => resp));
  }

}
