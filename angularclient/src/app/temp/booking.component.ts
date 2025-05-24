import { Wallbox, RentedWallbox } from './../model/wallbox';
import { Component, OnInit } from '@angular/core';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { WallboxService } from '../services/wallbox.service';
import { addMinutes, format, startOfDay } from 'date-fns';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { Booking, dummyBookings } from '../model/booking';
import { MatDividerModule } from '@angular/material/divider';


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
export class BookingComponentGPT implements OnInit {
  wallboxes: Wallbox[] = [];
  selectedWallbox: any;
  selectedDate: Date = new Date();
  bookings: Booking[] = [];
  timeSlots: any[] = [];
  selectedSlots: Set<string> = new Set();

  constructor(private wallboxService: WallboxService) {}

  ngOnInit() {
    this.loadWallboxes();
  }

  loadWallboxes() {
    this.wallboxService.findAllRented().subscribe(rentedWallboxes => {
      this.wallboxes = rentedWallboxes.map(rw => rw.wallbox);
      this.selectedWallbox = this.wallboxes[0];
      this.fetchBookings();
    });
  }

onDateChange(event: MatDatepickerInputEvent<Date>) {
  const selected = event.value;
  console.log('Selected date:', selected);
}

  onWallboxChange(wallboxId: number) {
    this.selectedWallbox = this.wallboxes.find(w => w.id === wallboxId);
    this.fetchBookings();
  }

  fetchBookings() {
    // this.wallboxService.getBookings(this.selectedWallbox.id).subscribe(data => {
      this.bookings = dummyBookings;
      this.generateSlots();
    // });
  }

  generateSlots() {
    const slots = [];
    const dayStart = startOfDay(this.selectedDate);
    for (let hour = 0; hour <= 23; hour++) {
      for (let min of [0, 15, 30, 45]) {
        const slotStart = new Date(this.selectedDate.setHours(hour, min, 0, 0));
        const slotKey = slotStart.toISOString();
        const booked = this.bookings.some(booking =>
          new Date(booking.startTime) <= slotStart && new Date(booking.endTime) > slotStart
        );
        slots.push({ time: slotStart, key: slotKey, booked });
      }
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
    // const sorted = Array.from(this.selectedSlots).sort();
    // const start = new Date(sorted[0]);
    // const end = addMinutes(new Date(sorted[sorted.length - 1]), 15);
    // this.wallboxService.bookSlot(this.selectedWallbox.id, start, end).subscribe(() => {
    //   this.selectedSlots.clear();
    //   this.fetchBookings();
    // });
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
}
