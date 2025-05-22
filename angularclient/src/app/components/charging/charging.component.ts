import { ChargingService } from './../../services/charging.service';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-charging',
  templateUrl: './charging.component.html',
  imports: [CommonModule, HttpClientModule],
  styleUrl: './charging.component.scss'
})
export class ChargingComponent implements OnInit {

   constructor(private chargingService: ChargingService) {
    }

  ngOnInit() {
  this.chargingService.turnOn()
  }

}
