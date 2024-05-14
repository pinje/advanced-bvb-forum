import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {

  private apiUrl = 'http://localhost:8222/api/v1/auth/';

  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  isAuthenticated$: Observable<boolean> = this.isAuthenticatedSubject.asObservable();

  private isAdminSubject = new BehaviorSubject<boolean>(false);
  isAdmin$: Observable<boolean> = this.isAdminSubject.asObservable();

  constructor(
    private http: HttpClient
  ) { }

  getUserRole(): Observable<any> {
    return this.http.post(this.apiUrl + 'authorities', {}, {withCredentials: true});
  }

  checkUserRole() {
    this.getUserRole().subscribe({
      next: (res) => {
        if (res.userInfo == 'MEMBER') {
          this.updateAuthenticationStatus(true);
        } else if (res.userInfo == 'ADMIN') {
          this.updateAuthenticationStatus(true);
          this.updateAdminStatus(true);
        }
      }
    })
  }

  updateAuthenticationStatus(isAuthenticated: boolean) {
    this.isAuthenticatedSubject.next(isAuthenticated);
  }

  updateAdminStatus(isAdmin: boolean) {
    this.isAdminSubject.next(isAdmin);
  }
}
