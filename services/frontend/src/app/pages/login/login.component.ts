import { Component } from '@angular/core';
import { LoginFormComponent } from '../../components/login-form/login-form.component';
import { Router, RouterModule } from '@angular/router';
import { AuthenticationService } from '../../services/authentication.service';
import { AuthenticationRequest } from '../../models/request/authentication-request';
import { TokenService } from '../../services/token.service';

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
    private router: Router,
    private tokenService: TokenService
  ) {}

  error: Array<string> = [];

  login($event: AuthenticationRequest): void {
    this.error = [];
    this.authService.login($event).subscribe({
      next: (res) => {
        this.tokenService.token = res.token as string;
        this.router.navigate(['']);
      },
      error: (err) => {
        if (err.error.validationErrors) {
          this.error = err.error.validationErrors;
        } else {
          this.error = ['Incorrect email or password'];
        }
      }
    })
  }
}
