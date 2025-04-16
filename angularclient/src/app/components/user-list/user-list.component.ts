import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { User } from '../../model/user/user';
import { UserService } from '../../services/user-service.service';

@Component({
  selector: 'app-user-list',
  providers: [UserService],
  templateUrl: './user-list.component.html',
  imports: [CommonModule, HttpClientModule],
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {

  users: User[] = [];

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.userService.findAll().subscribe((data : User[])=> {
      this.users = data;
    });
  }
}
