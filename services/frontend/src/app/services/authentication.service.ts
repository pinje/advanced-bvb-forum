import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegistrationRequest } from '../models/request/registration-request';
import { AuthenticationRequest } from '../models/request/authentication-request';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private apiUrl = 'http://localhost:8222/api/v1/auth/';

  constructor(private http: HttpClient) {}

  register(body: RegistrationRequest): Observable<any> {
    return this.http.post(this.apiUrl + 'register', body);
  };

  login(body: AuthenticationRequest): Observable<any> {
    return this.http.post(this.apiUrl + 'login', body, {withCredentials: true});
  }

  logout(): Observable<any> {
    return this.http.post(this.apiUrl + 'logout', {}, {withCredentials: true});
  }
}
