import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core'

export const authGuard: CanActivateFn = () => {
  const router: Router = inject(Router);

  
  // if (!tokenService.isTokenValid()) {
  //   router.navigate(['login']);
  //   return false;
  // }
  return true;
};
