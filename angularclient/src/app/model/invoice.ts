import { BookedTimeSlot } from "./booking";

export interface Invoice {
  wallboxOwner: string;
  wallboxName: string;
  startTime: Date;
  endTime: Date;

  timeSlots: InvoiceTimeSlot[];
  pingsTotal: number;
  pricePerPing: number;
  pingPriceTotal: number;

  transactions: InvoiceTransaction[];
  whTotal: number;
  pricePerWh: number;
  whPriceTotal: number;

  priceTotal: number;
}

export interface InvoiceTimeSlot {
  timeSlot: BookedTimeSlot;
  pings: number;
}

export interface InvoiceTransaction {
  transaction: ChargingTransaction;
  wh: number;
}

export interface ChargingTransaction {
  id: number;
  transactionId: number;
  startWattsPerHour: number | null;
  endWattsPerHour: number | null;
  startTime: Date;
  endTime: Date;
}
