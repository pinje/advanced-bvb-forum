import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthorizationService } from '../../services/authorization.service';
import { AuthenticationService } from '../../services/authentication.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent implements OnInit {
  
  isAuthenticated: boolean = false;
  isAdmin: boolean = false;

  constructor(
    private authorizationService: AuthorizationService,
    private authService: AuthenticationService
  ) {}

  ngOnInit(): void {
    this.authorizationService.checkUserRole();

    this.authorizationService.isAuthenticated$.subscribe((isAuthenticated: boolean) => {
      this.isAuthenticated = isAuthenticated;
    });

    this.authorizationService.isAdmin$.subscribe((isAdmin: boolean) => {
      this.isAdmin = isAdmin;
    });
  }

  logout() {
    this.authService.logout().subscribe({
      next: () => {
        this.authorizationService.updateAuthenticationStatus(false);
        this.authorizationService.updateAdminStatus(false);
      }
    })
  }
}
