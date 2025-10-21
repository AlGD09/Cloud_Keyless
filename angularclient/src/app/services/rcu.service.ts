import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { Rcu } from '../model/rcu';



@Injectable({
  providedIn: 'root'
})

export class RcuService {

  private baseUrl: string;
  constructor(private http: HttpClient) {
        this.baseUrl = 'http://localhost:8080/api/rcu';
  }

registerRcu(rcu: Rcu): Observable<Rcu> {
    return this.http.post<Rcu>(`${this.baseUrl}/register`, rcu);
}

getAll(): Observable<Rcu[]> {
    return this.http.get<Rcu[]>(`${this.baseUrl}/list`);
}

assignSmartphone(rcuId: number, smartphoneId: number): Observable<Rcu> {
    return this.http.post<Rcu>(`${this.baseUrl}/assign`, { rcuId, smartphoneId });
}

getAssignedSmartphone(rcuId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${rcuId}/smartphones`);
}



  }





