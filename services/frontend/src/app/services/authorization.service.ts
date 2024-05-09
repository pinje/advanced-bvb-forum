import { Injectable } from '@angular/core';
import { TokenService } from './token.service';
import { JwtHelperService } from '@auth0/angular-jwt';
import { AuthenticationService } from './authentication.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {

  private apiUrl = 'http://localhost:8222/api/v1/auth/';

  constructor(
    private tokenService: TokenService,
    private http: HttpClient
  ) { }

  getUsername() {
    const token = this.tokenService.token;
    if (!token) {
      return "";
    }

    const jwtHelper = new JwtHelperService();
    const decoded = jwtHelper.decodeToken(token);

    return decoded.sub;
  }

  getUserRole(): Observable<any> {
    return this.http.post(this.apiUrl + 'authorities', {});
  }

  isAdmin() {
    const token = this.tokenService.token;
    if (!token) {
      return false;
    }

    const jwtHelper = new JwtHelperService();
    const decoded = jwtHelper.decodeToken(token);

    if (decoded.authorities[0] != 'ADMIN') {
      return false;
    }

    return true;
  }
}
