import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({ providedIn: 'root' })
export class BookingService {
  constructor(private http: HttpClient) {}

getWallboxes(): Observable<any[]> {
  return this.http.get<any[]>('/api/wallboxes');
}

getBookings(wallboxId: number): Observable<any[]> {
  return this.http.get<any[]>(`/api/bookings?wallboxId=${wallboxId}`);
}

  bookSlot(wallboxId: number, start: Date, end: Date) {
    return this.http.post('/api/bookings', {
      wallboxId,
      startTime: start.toISOString(),
      endTime: end.toISOString()
    });
  }
}
