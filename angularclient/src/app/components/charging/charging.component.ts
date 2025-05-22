import { ChargingService } from './../../services/charging.service';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';

@Component({
  selector: 'app-charging',
  templateUrl: './charging.component.html',
  imports: [CommonModule, HttpClientModule],
  styleUrl: './charging.component.scss'
})
export class ChargingComponent implements OnInit, OnDestroy {

  clickCount = 0;


   constructor(private chargingService: ChargingService) {
    }

  ngOnInit() {
  this.chargingService.turnOn()
  }

  ngOnDestroy() {
  this.chargingService.turnOff()
  }

  get buttonLabel(): string {
  return this.clickCount % 2 === 0 ? 'Laden' : 'Stop Laden';
  }

  handleClick() {
    this.clickCount++;
    if (this.clickCount % 2 === 0) {
      this.onEvenClick();
    } else {
      this.onOddClick();
    }
  }

  onEvenClick() {
  this.chargingService.remoteStop()
  }

  onOddClick() {
  this.chargingService.remoteStart()
  }

}
