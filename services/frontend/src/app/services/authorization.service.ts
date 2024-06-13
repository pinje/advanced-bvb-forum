import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {

  private apiUrl = environment.baseUrl + 'auth/';

  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  isAuthenticated$: Observable<boolean> = this.isAuthenticatedSubject.asObservable();

  private isAdminSubject = new BehaviorSubject<boolean>(false);
  isAdmin$: Observable<boolean> = this.isAdminSubject.asObservable();

  private userIdSubject = new BehaviorSubject<string>('');
  userId$: Observable<string> = this.userIdSubject.asObservable();

  private usernameSubject = new BehaviorSubject<string>('');
  username$: Observable<string> = this.usernameSubject.asObservable();

  constructor(
    private http: HttpClient
  ) { }

  getUserRole(): Observable<any> {
    return this.http.post(this.apiUrl + 'authorities', {}, {withCredentials: true});
  }

  getUserId(): Observable<any> {
    return this.http.post(this.apiUrl + 'userid', {}, {withCredentials: true});
  }

  getUsername(): Observable<any> {
    return this.http.post(this.apiUrl + 'username', {}, {withCredentials: true});
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

  checkUserId() {
    this.getUserId().subscribe({
      next: (res) => {
        this.updateUserId(res.userInfo);
      }
    })
  }

  checkUsername() {
    this.getUsername().subscribe({
      next: (res) => {
        this.updateUsername(res.userInfo);
      }
    })
  }

  updateAuthenticationStatus(isAuthenticated: boolean) {
    this.isAuthenticatedSubject.next(isAuthenticated);
  }

  updateAdminStatus(isAdmin: boolean) {
    this.isAdminSubject.next(isAdmin);
  }

  updateUserId(userId: string) {
    this.userIdSubject.next(userId);
  }

  updateUsername(username: string) {
    this.usernameSubject.next(username);
  }
}
