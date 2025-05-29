import { User } from "./user/user";
import { RentedWallbox } from "./wallbox";

export interface BookingRegister {
  bookingUserId: number;
  rentedWallboxId: number;
  startTime: string; //string to enforece Berlin time
  endTime: string; //string to enforece Berlin time
}

export interface Booking {
  id: number;
  rentedWallbox: RentedWallbox;
  bookingUser: User;
  bookedSlots: BookedTimeSlot[];
}

export interface UpcomingBooking {
  booking: Booking;
  startTime: Date;
  endTime: Date;
}

export interface BookedTimeSlot {
    id: number;
    startTime: Date;
    endTime: Date;
    bookingTime: Date;
}

export interface TimeSlot {
    startTime: Date;
    endTime: Date;
    bookingUser?: User;
}
