import { Component } from '@angular/core';
import { LoginFormComponent } from '../../components/login-form/login-form.component';
import { Router, RouterModule } from '@angular/router';
import { AuthenticationService } from '../../services/authentication.service';
import { AuthenticationRequest } from '../../models/request/authentication-request';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    LoginFormComponent,
    RouterModule
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss', '../../app.component.scss']
})
export class LoginComponent {
  constructor(
    private authService: AuthenticationService,
    private router: Router
  ) {}

  error: Array<string> = [];

  login($event: AuthenticationRequest): void {
    this.error = [];
    this.authService.login($event).subscribe({
      next: () => {
        this.router.navigate(['']);
      },
      error: (err) => {
        this.error = err.error.validationErrors;
      }
    })
  }
}
