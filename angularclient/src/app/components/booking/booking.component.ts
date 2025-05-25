import { BookingRegister } from './../../model/booking';
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
import { Booking } from '../../model/booking';
import { MatDividerModule } from '@angular/material/divider';
import { BookingService } from '../../services/booking.service';
import { firstValueFrom } from 'rxjs';


@Component({
  selector: 'app-booking',
  standalone: true,
  imports: [CommonModule, FormsModule, MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
  MatDividerModule],
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

  constructor(private wallboxService: RentedWallboxService, private bookingService: BookingService) {}


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

getGridColumn(slot: any): number {
  const minutes = slot.time.getMinutes();
  return Math.floor(minutes / this.slotDurationMinutes) + 1;
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
    this.bookingService.findAllBookins().subscribe(bookings => {
      this.bookings = bookings;
      this.generateSlots();
    });
  }

generateSlots() {
  const slots = [];
  const rentalPeriod = this.getRentalPeriod();

  if (!rentalPeriod) {
    this.timeSlots = [];
    return;
  }

  const { start, end } = rentalPeriod;
  let slotStart = new Date(this.selectedDate);
  slotStart.setHours(0, 0, 0, 0); // start of the selected day

  // move slotStart to max of start of rentalPeriod or start of day
  if (slotStart < start) {
    slotStart = new Date(start);
  }

  while (slotStart < end && slotStart.getDate() === this.selectedDate.getDate()) {
    // Check if slotStart is within rental period
    if (slotStart >= start && slotStart < end) {
      const slotKey = slotStart.toISOString();

      // const booked = this.bookings.some(booking =>
      //   new Date(booking.startTime) <= slotStart && new Date(booking.endTime) > slotStart
      // );

      slots.push({ time: new Date(slotStart), key: slotKey });
    }

    // increment by slot duration
    slotStart = addMinutes(slotStart, this.slotDurationMinutes);
  }

  this.timeSlots = slots;
}



  toggleSlot(slot: any) {
    if (slot.booked) return;
    if (this.selectedSlots.has(slot.key)) {
      this.selectedSlots.delete(slot.key);
    } else {
      this.selectedSlots.add(slot.key);
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
  startTime: start,
  endTime: end
}

console.log(booking)

this.bookingService.registerBooking(booking).subscribe({
  next: () => {
    this.selectedSlots.clear();
    this.fetchBookings();
  },
  error: err => {
    console.error('Booking failed:', err);
  }
})
}


  isSelected(slotKey: string) {
    return this.selectedSlots.has(slotKey);
  }

  getSelectedSlotRange(): string {
  if (this.selectedSlots.size === 0) return '';

  const sorted = Array.from(this.selectedSlots).sort();
  const start = new Date(sorted[0]);
  const end = new Date(sorted[sorted.length - 1]);
  return `${start.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })} - ${end.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}`;
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
}
