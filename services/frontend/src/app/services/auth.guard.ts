import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core'
import { TokenService } from './token.service';

export const authGuard: CanActivateFn = () => {
  const router: Router = inject(Router);
  const tokenService: TokenService = inject(TokenService);

  
  if (!tokenService.isTokenValid()) {
    router.navigate(['login']);
    return false;
  }
  return true;
};
