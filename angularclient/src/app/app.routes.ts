import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserListComponent } from './components/user-list/user-list.component';
import { UserFormComponent } from './components/user-form/user-form.component';
import { BookingComponent } from './components/booking/booking.component';
import { ChargingComponent } from './components/charging/charging.component';

export const routes: Routes = [
  { path: 'users', component: UserListComponent },
  { path: 'adduser', component: UserFormComponent },
  { path: '', redirectTo: 'book', pathMatch: 'full' },
  { path: 'book', component: BookingComponent },
    { path: 'charge', component: ChargingComponent },
];

// @NgModule({
//   imports: [RouterModule.forRoot(routes)],
//   exports: [RouterModule]
// })
export class AppRoutingModule { }
