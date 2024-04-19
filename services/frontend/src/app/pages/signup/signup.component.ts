import { Component } from '@angular/core';
import { SignupFormComponent } from '../../components/signup-form/signup-form.component';
import { UserService } from '../../services/user.service';
import { CreateUser } from '../../models/request/create-user.interface';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [SignupFormComponent],
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss', '../../app.component.scss']
})
export class SignupComponent {
  constructor(private userService: UserService) {}

  error!: string;

  createNewUser($event: CreateUser): void {
    this.userService.createUser($event).subscribe((res) => {
      this.error = res.error.detail;
      console.log(this.error)
    });
  }
}
