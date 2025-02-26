import { Component } from '@angular/core';
import { SignupFormComponent } from '../../components/signup-form/signup-form.component';
import { RegistrationRequest } from '../../models/request/registration-request';
import { Router, RouterModule } from '@angular/router';
import { AuthenticationService } from '../../services/authentication.service';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [
    SignupFormComponent,
    RouterModule
  ],
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss', '../../app.component.scss']
})
export class SignupComponent {
  constructor(
    private authService: AuthenticationService,
    private router: Router
  ) {}

  error: Array<string> = [];

  register($event: RegistrationRequest): void {
    this.error = [];
    this.authService.register($event).subscribe({
      next: () => {
        this.router.navigate(['login']);
      },
      error: (err) => {
        this.error = err.error.validationErrors;
      }
    });
  }
}
