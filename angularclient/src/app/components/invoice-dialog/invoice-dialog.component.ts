import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { Invoice } from '../../model/invoice';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';

@Component({
  selector: 'app-invoice-dialog',
  standalone: true,
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule,
    MatTableModule
  ],
  templateUrl: './invoice-dialog.component.html',
  styleUrls: ['./invoice-dialog.component.scss']
})
export class InvoiceDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: Invoice) {}

  timeSlotColumns: string[] = ['startTime', 'endTime', 'pings'];
  transactionColumns: string[] = ['transactionId', 'startTime', 'endTime', 'wh'];
}
