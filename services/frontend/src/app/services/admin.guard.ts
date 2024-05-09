import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core'
import { AuthorizationService } from './authorization.service';

export const adminGuard: CanActivateFn = () => {
  const router: Router = inject(Router);
  const authorizationService: AuthorizationService = inject(AuthorizationService);

  if (!authorizationService.isAdmin()) {
    router.navigate(['login']);
    return false;
  }

  return true;
};
