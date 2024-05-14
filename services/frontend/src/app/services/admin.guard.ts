import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core'
import { AuthorizationService } from './authorization.service';

export const adminGuard: CanActivateFn = () => {
  const router: Router = inject(Router);
  const authorizationService: AuthorizationService = inject(AuthorizationService);

  let test: boolean = false;

  authorizationService.checkUserRole();

  authorizationService.isAdmin$.subscribe((isAdmin: boolean) => {
    if(isAdmin) {
      test = true;
    }
  });

  return test;
};
