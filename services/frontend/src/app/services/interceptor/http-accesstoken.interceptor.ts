import { HttpErrorResponse, HttpHeaders, HttpInterceptorFn, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';
import { TokenService } from '../token.service';
import { catchError, throwError } from 'rxjs';
import { AuthenticationService } from '../authentication.service';

export const httpAccesstokenInterceptor: HttpInterceptorFn = (req, next) => {

  const tokenService = inject(TokenService);
  const authService = inject(AuthenticationService);

  const token: string = tokenService.token;
  
  if (token) {
    const authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      },
      withCredentials: true
    });
    return next(authReq);
  }
  
  return next(req);
};
