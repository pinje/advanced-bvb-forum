import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ButtonActiveComponent } from '../button-active/button-active.component';
import { ButtonPassiveComponent } from '../button-passive/button-passive.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthenticationRequest } from '../../models/request/authentication-request';

@Component({
  selector: 'app-login-form',
  standalone: true,
  imports: [
    ButtonActiveComponent, 
    ButtonPassiveComponent,
    FormsModule,
    CommonModule,
    RouterModule
  ],
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.scss'
})
export class LoginFormComponent {
  @Output() onLogin: EventEmitter<AuthenticationRequest> = new EventEmitter();
  @Input() error: Array<string> = [];

  authRequest: AuthenticationRequest = {
    email: '',
    password: ''
  }

  constructor(private router: Router) {}

  onSubmit() {
    this.onLogin.emit(this.authRequest);
  }

  redirectToRegister() {
    this.router.navigate(['signup']);
  }
}
