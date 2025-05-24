export interface Booking {
  id: number;
  userId: number;
  wallboxId: number;
  startTime: Date;
  endTime: Date;
}

export const dummyBookings: Booking[] = [
  {
    id: 1,
    userId: 101,
    wallboxId: 1,
    startTime: new Date('2025-05-22T10:00:00Z'),
    endTime: new Date('2025-05-22T11:00:00Z')
  },
  {
    id: 2,
    userId: 102,
    wallboxId: 1,
    startTime: new Date('2025-05-22T12:30:00Z'),
    endTime: new Date('2025-05-22T13:30:00Z')
  },
  {
    id: 3,
    userId: 103,
    wallboxId: 2,
    startTime: new Date('2025-05-22T09:00:00Z'),
    endTime: new Date('2025-05-22T10:15:00Z')
  }
];
