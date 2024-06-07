import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { PopupService } from '../../services/popup.service';
import { AuthorizationService } from '../../services/authorization.service';
import { PopupComponent } from '../../components/popup/popup.component';
import { ButtonActiveComponent } from '../../components/button-active/button-active.component';
import { AuthenticationService } from '../../services/authentication.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    PopupComponent,
    ButtonActiveComponent
  ],
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss', '../../app.component.scss']
})
export class UserComponent implements OnInit {
  error: Array<string> = [];
  success: boolean = false;
  
  userId: string = '';
  username: string = '';
  activeUserId: string | null = null;

  constructor(
    private userService: UserService,
    private popupService: PopupService,
    private authorizationService: AuthorizationService,
    private authService: AuthenticationService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.authorizationService.checkUserId();
    this.authorizationService.checkUsername();

    this.authorizationService.userId$.subscribe((userId: string) => {
      this.userId = userId;
    });

    this.authorizationService.username$.subscribe((username: string) => {
      this.username = username;
    });

    // Using snapshot
    this.activeUserId = this.route.snapshot.paramMap.get('id');

    // Using paramMap observable
    this.route.paramMap.subscribe(params => {
      this.activeUserId = params.get('id');
    });
  }


  showPopup(id: string) {
    this.popupService.showPopup(id);
  }

  hidePopup(id: string) {
    this.popupService.hidePopup(id);
  }

  onSubmit() {
    this.error = [];
    this.success = false;
    this.userService.deleteUser(this.userId).subscribe({
      next: () => {
        this.hidePopup('user');
        this.logout();
        this.router.navigate(['/login']);
      },
      error: (err) => {
        if (err.error) {
          this.error = err.error.error
        }
      }
    })
  }

  logout() {
    this.authService.logout().subscribe({
      next: () => {
        this.authorizationService.updateAuthenticationStatus(false);
        this.authorizationService.updateAdminStatus(false);
        this.authorizationService.updateUserId('');
      }
    })
  }
}
