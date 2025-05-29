import { BookingRegister, TimeSlot } from './../../model/booking';
import { Wallbox, RentedWallbox } from '../../model/wallbox';
import { Component, OnInit } from '@angular/core';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { RentedWallboxService } from '../../services/rentedWallbox.service';
import { addMinutes, format, startOfDay } from 'date-fns';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Booking } from '../../model/booking';
import { MatDividerModule } from '@angular/material/divider';
import { BookingService } from '../../services/booking.service';
import { firstValueFrom } from 'rxjs';
import { User } from '../../model/user/user';


@Component({
  selector: 'app-booking',
  standalone: true,
  imports: [CommonModule, FormsModule, MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatDividerModule,
    MatSnackBarModule
],
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.scss']
})
export class BookingComponent implements OnInit {
  wallboxes: Wallbox[] = [];
  selectedWallbox: any;
  selectedDate: Date = new Date();
  bookings: Booking[] = [];
  timeSlots: any[] = [];
  selectedSlots: Set<string> = new Set();
  rentedWallboxes: RentedWallbox[] = [];

  slotDurationMinutes = 15;
  columns = 60 / this.slotDurationMinutes;

  constructor(
    private wallboxService: RentedWallboxService,
    private bookingService: BookingService,
    private snackBar: MatSnackBar,
  ) {}


  async ngOnInit() {
  const response = await firstValueFrom(this.bookingService.getTimeSlotLength());
  this.slotDurationMinutes = response;
  this.columns = 60 / this.slotDurationMinutes;

    this.loadWallboxes();
  }

loadWallboxes() {
  this.wallboxService.findAllRented().subscribe(rentedWallboxes => {
    this.rentedWallboxes = rentedWallboxes;
    this.wallboxes = rentedWallboxes.map(rw => rw.wallbox);
    this.selectedWallbox = this.wallboxes[0];
    this.fetchBookings();
  });
}

getGridColumn(slot: TimeSlot): number {
  return Math.floor(slot.startTime.getMinutes() / this.slotDurationMinutes) + 1;
}


onDateChange(event: MatDatepickerInputEvent<Date>) {
  const selected = event.value;
  if (!selected) return; // handle null/undefined
  this.selectedDate = selected;
  this.fetchBookings();  // <-- trigger bookings reload and slots update
}

onWallboxChange(wallboxId: number) {
  this.selectedWallbox = this.wallboxes.find(w => w.id === wallboxId);
  this.selectedSlots.clear();  // <-- clear previous selections here
  this.fetchBookings();
}

  fetchBookings() {
    this.bookingService.findAllBookings().subscribe(bookings => {
      this.bookings = bookings;
      this.generateSlots();
    });
  }

generateSlots() {
  const slots: TimeSlot[] = [];
  const rentalPeriod = this.getRentalPeriod();

  if (!rentalPeriod) {
    this.timeSlots = [];
    return;
  }

  const { start, end } = rentalPeriod;
  let slotStart = new Date(this.selectedDate);
  slotStart.setHours(0, 0, 0, 0); // start of the selected day

  // Ensure start is within the rental period
  if (slotStart < start) {
    slotStart = new Date(start);
  }

  while (slotStart < end && slotStart.getDate() === this.selectedDate.getDate()) {
    const slotEnd = addMinutes(slotStart, this.slotDurationMinutes);

    // Determine if this slot is booked
    let bookingUser: User | null = null;

    for (const booking of this.bookings) {
      for (const ts of booking.bookedSlots) {
        const tsStart = new Date(ts.startTime);
        const tsEnd = new Date(ts.endTime);

        if (slotStart >= tsStart && slotStart < tsEnd) {
          bookingUser = booking.bookingUser;
          break;
        }
      }
      if (bookingUser) break;
    }

    slots.push({
      startTime: new Date(slotStart),
      endTime: slotEnd,
      bookingUser: bookingUser!
    });

    slotStart = slotEnd;
  }

  this.timeSlots = slots;
}


toggleSlot(slot: TimeSlot) {
  // Don't allow selection of already booked slots
  if (slot.bookingUser) return;

  const key = slot.startTime.toISOString();

  if (this.selectedSlots.has(key)) {
    this.selectedSlots.delete(key);
  } else {
    this.selectedSlots.add(key);
  }
}

confirmBooking() {
  const sorted = Array.from(this.selectedSlots).sort();
  if (sorted.length === 0) return;

  const start = new Date(sorted[0]);
  const end = addMinutes(new Date(sorted[sorted.length - 1]), this.slotDurationMinutes);

  const booking: BookingRegister = {
    bookingUserId: 1,
    rentedWallboxId: this.selectedWallbox.id,
    startTime: this.toLocalISOString(start),
    endTime: this.toLocalISOString(end)
  };

  this.bookingService.registerBooking(booking).subscribe({
    next: () => {
      this.selectedSlots.clear();
      this.fetchBookings();

      // ✅ Show success snackbar
      this.snackBar.open('Booking confirmed successfully!', 'Close', {
        duration: 3000,
        panelClass: 'snackbar-success'
      });
    },
    error: err => {
      console.error('Booking failed:', err);

      // ❌ Show error snackbar
      this.snackBar.open('Booking failed. Please try again.', 'Close', {
        duration: 3000,
        panelClass: 'snackbar-error'
      });
    }
  });
}



isSelected(slot: TimeSlot): boolean {
  return this.selectedSlots.has(slot.startTime.toISOString());
}

getSelectedSlotRange(): { start: Date, end: Date } | null {
  if (this.selectedSlots.size === 0) return null;

  const sorted = Array.from(this.selectedSlots).sort();
  const start = new Date(sorted[0]);
  const end = addMinutes(new Date(sorted[sorted.length - 1]), this.slotDurationMinutes);

  return { start, end };
}

getRentalPeriod() {
  if (!this.selectedWallbox) return null;
  const rented = this.rentedWallboxes.find(rw => rw.wallbox.id === this.selectedWallbox.id);
  if (!rented) return null;
  return {
    start: new Date(rented.startTime),
    end: new Date(rented.endTime)
  };
}

// Helper to strip timezone (Berlin assumed to be system time)
toLocalISOString(date: Date): string {
  const tzOffset = date.getTimezoneOffset() * 60000; // offset in ms
  const localISOTime = new Date(date.getTime() - tzOffset).toISOString().slice(0, 19);
  return localISOTime;
}
}
