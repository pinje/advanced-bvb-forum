import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  set token(token: string) {
    localStorage.removeItem('accessToken');
    localStorage.setItem('accessToken', token);
  }

  get token() {
    return localStorage.getItem('accessToken') as string;
  }

  // check refresh token validity
  isTokenValid() {
    const token = this.token;
    if (!token) {
      return false;
    }

    // decode token
    const jwtHelper = new JwtHelperService();
    const isTokenExpired = jwtHelper.isTokenExpired(token);

    if (isTokenExpired) {
      localStorage.clear();
      return false;
    }

    return true;
  }
}
