import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RentedWallbox, Wallbox } from '../model/wallbox';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RentedWallboxService {

  private wallboxUrl: string;

  constructor(private http: HttpClient) {
    this.wallboxUrl = 'http://localhost:8080/api/wallboxes';
  }

  public findAllRented(): Observable<RentedWallbox[]> {
    return this.http.get<RentedWallbox[]>(this.wallboxUrl + "/rented");
  }

    public findAll(): Observable<Wallbox[]> {
    return this.http.get<Wallbox[]>(this.wallboxUrl);
  }
}
