import { RentedWallbox } from './../../model/wallbox';
import { ChargingService } from './../../services/charging.service';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { BookingService } from '../../services/booking.service';
import { Wallbox } from '../../model/wallbox';
import { Booking, UpcomingBooking } from '../../model/booking';
import { format } from 'date-fns';
import { RentedWallboxService } from '../../services/rentedWallbox.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-charging',
  standalone: true,
  templateUrl: './charging.component.html',
  styleUrls: ['./charging.component.scss'],
  imports: [CommonModule, HttpClientModule, FormsModule]
})
export class ChargingComponent implements OnInit, OnDestroy {

  clickCount = 0;
  USER = "Elekey";
  wallboxes: Wallbox[] = [];
  selectedWallbox!: Wallbox;
  upcomingBooking: UpcomingBooking | null = null;
  currentTime: Date = new Date();
  isAtWallbox = false;
  isCharging = false;

  constructor(
    private chargingService: ChargingService,
    private bookingService: BookingService,
    private rentedWallboxService: RentedWallboxService
  ) {}

  ngOnInit() {
    this.loadWallboxes();
    this.fetchUpcomingBooking();
  }

  ngOnDestroy() {
    if (this.upcomingBooking) {
      this.chargingService.turnOff();
    }
  }

loadWallboxes() {
  this.rentedWallboxService.findAll().subscribe(wallboxes => {
    this.wallboxes = wallboxes;
    this.selectedWallbox = wallboxes[0]; // Default selection
    this.fetchUpcomingBooking(); // Now safe to call
  });
}

  fetchUpcomingBooking() {
    this.bookingService.getUpcomingBooking(this.USER, this.selectedWallbox.id).subscribe(booking => {
      this.upcomingBooking = booking;
    });
  }

  get label(): string {
    if (!this.upcomingBooking) {
      return 'You have no upcoming booking!';
    }

    const now = new Date();
    if (now < new Date(this.upcomingBooking.startTime)) {
      return `Your booking starts at ${format(new Date(this.upcomingBooking.startTime), 'HH:mm')}`;
    }

    if (now >= new Date(this.upcomingBooking.startTime) && now <= new Date(this.upcomingBooking.endTime)) {
      return `Your booking ends at ${format(new Date(this.upcomingBooking.endTime), 'HH:mm')}`;
    }

    return 'You have no upcoming booking!';
  }

    get buttonLabel(): string {
      return this.clickCount % 2 === 0 ? 'I\'m at the booked wallbox' : 'I left the booked wallbox';
  }

  handleClick() {
    this.clickCount++;
    if (this.clickCount % 2 === 0) {
      this.onEvenClick();
    } else {
      this.onOddClick();
    }
    this.isAtWallbox = !this.isAtWallbox;

  }

  onEvenClick() {
    this.chargingService.turnOff();
  }

  onOddClick() {
    this.chargingService.turnOn();
  }

  get chargeButtonLabel(): string {
    return this.clickCount % 2 === 0 ? 'Charge' : 'Stop charging';
  }

  handleChargeClick() {
    if (this.clickCount % 2 === 0) {
      this.chargingService.remoteStart();
    } else {
      this.chargingService.remoteStop();
    }
    this.clickCount++;
  }

  onWallboxChange() {
  this.fetchUpcomingBooking();
}

  get isBookingActive(): boolean {
  if (!this.upcomingBooking) return false;
  const now = new Date();
  const start = new Date(this.upcomingBooking.startTime);
  const end = new Date(this.upcomingBooking.endTime);
  return now >= start && now <= end;
}

toggleCharging() {
  if (this.isCharging) {
    this.chargingService.remoteStop();
  } else {
    this.chargingService.remoteStart();
  }
  this.isCharging = !this.isCharging;
}
}
