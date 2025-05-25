import { User } from "./user/user";
import { RentedWallbox } from "./wallbox";

export interface BookingRegister {
  bookingUserId: number;
  rentedWallboxId: number;
  startTime: Date;
  endTime: Date;
}

export interface Booking {
  id: number;
  rentedWallbox: RentedWallbox;
  bookingUser: User;
  bookedSlots: BookedTimeSlot[];
}

export interface BookedTimeSlot {
    id: number;
    startTime: Date;
    endTime: Date;
}

export interface TimeSlot {
    startTime: Date;
    endTime: Date;
    bookingUser: User;
}
