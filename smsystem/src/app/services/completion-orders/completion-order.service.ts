import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { API_END_POINTS } from 'app/utils';
import { ResponseWrapper, CompletionOrder } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class CompletionOrderService {

  constructor(private http: HttpClient) { }

  getCompletionOrder(completionOrderId: number): Observable<CompletionOrder> {
    return this.http
      .get(`${API_END_POINTS.completionOrders}/${completionOrderId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  getCompletionOrders(): Observable<CompletionOrder[]> {
    return this.http.get(`${API_END_POINTS.completionOrders}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  createCompletionOrder(data?: any): Observable<CompletionOrder> {
    return this.http.post(`${API_END_POINTS.completionOrders}`, data)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  deleteCompletionOrder(completionOrderId: number): Observable<boolean> {
    return this.http.delete(`${API_END_POINTS.completionOrders}/${completionOrderId}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updateCompletionOrder(data?: any): Observable<CompletionOrder> {
    return this.http.put(`${API_END_POINTS.completionOrders}`, data)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }
}
