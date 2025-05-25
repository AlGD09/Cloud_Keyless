import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ChargingService {

  private steveControllerUrl: string;

  constructor(private http: HttpClient) {
    this.steveControllerUrl = 'http://localhost:8080/api/steve';
  }

  public turnOn(): void {
    this.http.post(this.steveControllerUrl + "/turnOn", null)
    .subscribe({
      next: res => console.log('Turned on successfully', res),
      error: err => console.error('Failed to turn on:', err)
    });
  }

  public turnOff(): void {
    this.http.post(this.steveControllerUrl + "/turnOff", null)
    .subscribe({
      next: res => console.log('Turned off successfully', res),
      error: err => console.error('Failed to turn off:', err)
    });
  }

  public remoteStart(): void {
    this.http.post(this.steveControllerUrl + "/remoteStart", null)
    .subscribe({
      next: res => console.log('remoteStart successfully', res),
      error: err => console.error('Failed to remoteStart:', err)
    });
  }

  public remoteStop(): void {
    this.http.post(this.steveControllerUrl + "/remoteStop", null)
    .subscribe({
      next: res => console.log('remoteStop successfully', res),
      error: err => console.error('Failed to remoteStop:', err)
    });
  }
}

