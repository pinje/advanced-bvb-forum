import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RegistrationRequest } from '../../models/request/registration-request';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { ButtonActiveComponent } from '../button-active/button-active.component';
import { ButtonPassiveComponent } from '../button-passive/button-passive.component';

@Component({
  selector: 'app-signup-form',
  standalone: true,
  imports: [
    ButtonActiveComponent, 
    ButtonPassiveComponent,
    FormsModule,
    CommonModule,
    RouterModule
],
  templateUrl: './signup-form.component.html',
  styleUrl: './signup-form.component.scss'
})
export class SignupFormComponent {
  @Output() onSubmitNewUser: EventEmitter<RegistrationRequest> = new EventEmitter();
  @Input() error: Array<string> = [];

  registerRequest: RegistrationRequest = {
    email: '',
    username: '',
    password: ''
  }

  constructor(private router: Router) {}

  onSubmit() {
    this.onSubmitNewUser.emit(this.registerRequest);
  }

  redirectToLogin() {
    this.router.navigate(['login']);
  }
}
