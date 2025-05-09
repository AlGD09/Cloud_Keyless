import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { WallboxService } from '../../services/wallbox.service';
import { RentedWallbox } from '../../model/wallbox';

@Component({
  selector: 'app-booking',
  providers: [WallboxService],
  templateUrl: './booking.component.html',
  imports: [CommonModule, HttpClientModule],
  styleUrl: './booking.component.scss'
})
export class BookingComponent implements OnInit {

  rentedWallboxes: RentedWallbox[] = [];

  constructor(private wallboxService: WallboxService) {
  }

  ngOnInit() {
    this.wallboxService.findAllRented().subscribe((data : RentedWallbox[])=> {
      this.rentedWallboxes = data;
      console.log(this.rentedWallboxes)
    });
  }

}
