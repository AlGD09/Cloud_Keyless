import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { Rcu } from '../model/rcu';
import { Smartphone } from '../model/smartphone';
import { Subject } from 'rxjs';


@Injectable({
  providedIn: 'root'
})

export class RcuService {

  private baseUrl: string;

  private refreshRequested = new Subject<void>();
  refreshRequested$ = this.refreshRequested.asObservable();

  constructor(private http: HttpClient) {
        this.baseUrl = 'http://localhost:8080/api/rcu';
  }

registerRcu(rcu: Rcu): Observable<Rcu> {
    return this.http.post<Rcu>(`${this.baseUrl}/register`, rcu);
}

getAllRcus(): Observable<Rcu[]> {
    return this.http.get<Rcu[]>(`${this.baseUrl}/list`);
}

assignSmartphone(rcuId: number, smartphoneId: number): Observable<Rcu> {
    return this.http.post<Rcu>(`${this.baseUrl}/assign`, { rcuId, smartphoneId });
}

getAssignedSmartphone(rcuId: number): Observable<Smartphone> {
    return this.http.get<Smartphone>(`${this.baseUrl}/${rcuId}/smartphones`);
}

deleteRcu(id: number) {
    return this.http.delete(`${this.baseUrl}/delete/${id}`);
}

requestRefresh(): void {
    this.refreshRequested.next();
}



  }





