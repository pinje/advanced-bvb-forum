import { CommonModule } from '@angular/common';
import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthorizationService } from '../../services/authorization.service';
import { AuthenticationService } from '../../services/authentication.service';
import { Observable, catchError, of, switchMap } from 'rxjs';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent implements OnInit {

  constructor(
    private authorizationService: AuthorizationService
  ) {}

  // getUserRole() {
  //   return 'MEMBER';
  // }

  ngOnInit(): void {
    this.getUserRole();
  }

  userRole: string = "";

  getUserRole(): any {
    this.authorizationService.getUserRole().subscribe({
      next: (res) => {
        this.userRole = res.userInfo;
      }
    })
  }
}
