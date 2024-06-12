import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegistrationRequest } from '../models/request/registration-request';
import { AuthenticationRequest } from '../models/request/authentication-request';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private apiUrl = environment + 'auth/';

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
