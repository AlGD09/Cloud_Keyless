import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserListComponent } from './components/user-list/user-list.component';
import { UserFormComponent } from './components/user-form/user-form.component';
import { BookingComponentGPT } from './temp/booking.component';
import { ChargingComponent } from './components/charging/charging.component';

export const routes: Routes = [
  { path: 'users', component: UserListComponent },
  { path: 'adduser', component: UserFormComponent },
  { path: '', redirectTo: 'buchen', pathMatch: 'full' },
  { path: 'buchen', component: BookingComponentGPT },
    { path: 'laden', component: ChargingComponent },
];

// @NgModule({
//   imports: [RouterModule.forRoot(routes)],
//   exports: [RouterModule]
// })
export class AppRoutingModule { }
