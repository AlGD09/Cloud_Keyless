import { InvoiceDialogComponent } from './../invoice-dialog/invoice-dialog.component';
import { Component, OnInit } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { HttpClient } from '@angular/common/http';
import { UpcomingBooking } from '../../model/booking';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-upcoming-bookings',
    standalone: true,
  imports: [CommonModule, MatTableModule],
  templateUrl: './invoices.component.html',
  styleUrls: ['./invoices.component.scss']
})
export class InvoicesComponent implements OnInit {
  bookings: UpcomingBooking[] = [];
  isLoading = true;

  constructor(private http: HttpClient, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.http.get<UpcomingBooking[]>('/api/upcoming-bookings')
      .subscribe({
        next: data => {
          this.bookings = data;
          this.isLoading = false;
        },
        error: () => {
          this.bookings = [];
          this.isLoading = false;
        }
      });
  }

  openDialog(booking: UpcomingBooking): void {
    this.dialog.open(InvoiceDialogComponent, {
      data: booking
    });
  }
}
