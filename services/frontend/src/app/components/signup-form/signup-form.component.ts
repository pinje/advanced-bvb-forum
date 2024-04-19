import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ButtonComponent } from '../button/button.component';
import { FormsModule } from '@angular/forms';
import { CreateUser } from '../../models/request/create-user.interface';

@Component({
  selector: 'app-signup-form',
  standalone: true,
  imports: [ButtonComponent, FormsModule],
  templateUrl: './signup-form.component.html',
  styleUrl: './signup-form.component.scss'
})
export class SignupFormComponent {
  @Output() onSubmitNewUser: EventEmitter<CreateUser> = new EventEmitter();

  error: string = "";
  email: string = "";
  username: string = "";
  password: string = "";

  constructor() {}

  onSubmit() {
    let newUser: CreateUser = {
      "email": this.email,
      "username": this.username,
      "password": this.password
    }

    this.onSubmitNewUser.emit(newUser);
  }
}
