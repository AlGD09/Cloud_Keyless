import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../services/user-service.service';
import { User } from '../../model/user/user';

@Component({
  selector: 'app-user-form',
  providers: [UserService],
  templateUrl: './user-form.component.html',
  imports: [FormsModule, HttpClientModule],
  styleUrls: ['./user-form.component.scss']
})
export class UserFormComponent {

  user: User;

  constructor(
    private route: ActivatedRoute,
      private router: Router,
        private userService: UserService) {
    this.user = {
                   id: '',
                   name: '',
                   email: ''
                 };
}

  onSubmit() {
    this.userService.save(this.user).subscribe(result => this.gotoUserList());
  }

  gotoUserList() {
    this.router.navigate(['/users']);
  }
}
