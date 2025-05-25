import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Booking, BookingRegister } from '../model/booking';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

    private bookingUrl: string;

    constructor(private http: HttpClient) {
      this.bookingUrl = 'http://localhost:8080/api/bookings';
    }

    public findAllBookins(): Observable<Booking[]> {
      return this.http.get<Booking[]>(this.bookingUrl);
    }

    public getTimeSlotLength(): Observable<any> {
      return this.http.get<{timeSlotLength: number}>(this.bookingUrl + "/time-slot-length");
    }


public registerBooking(booking: BookingRegister): Observable<any> {
  return this.http.post(this.bookingUrl, booking);
}

  }

