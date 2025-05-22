import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ChargingService {

  private steveControllerUrl: string;

  constructor(private http: HttpClient) {
    this.steveControllerUrl = 'http://localhost:8080/steve';
  }

  public turnOn(): void {
    console.log(this.steveControllerUrl + "/turnOn")
    this.http.post(this.steveControllerUrl + "/turnOn", null)
    .subscribe({
      next: res => console.log('Turned on successfully', res),
      error: err => console.error('Failed to turn on:', err)
    });
  }
}

