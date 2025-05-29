import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Booking, BookingRegister, UpcomingBooking } from '../model/booking';
import { Observable } from 'rxjs/internal/Observable';
import { Invoice } from '../model/invoice';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

    private bookingUrl: string;

    constructor(private http: HttpClient) {
      this.bookingUrl = 'http://localhost:8080/api/bookings';
    }

    public findAllBookings(): Observable<Booking[]> {
      return this.http.get<Booking[]>(this.bookingUrl);
    }

getUpcomingBooking(userId: string, wallboxId: number): Observable<UpcomingBooking> {
  return this.http.get<UpcomingBooking>(`${this.bookingUrl}/upcoming`, {
    params: {
      username: userId,
      wallboxId: wallboxId.toString()
    }
  });
}

getInvoices(userId: string): Observable<Invoice[]> {
  return this.http.get<Invoice[]>(`${this.bookingUrl}/invoices`, {
    params: {
      username: userId
    }
  });
}


public getTimeSlotLength(): Observable<any> {
  return this.http.get<{timeSlotLength: number}>(this.bookingUrl + "/time-slot-length");
}


public registerBooking(booking: BookingRegister): Observable<any> {
  return this.http.post(this.bookingUrl, booking);

}

  }

