import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core'
import { AuthorizationService } from './authorization.service';
import { Observable, map, take } from 'rxjs';

export const authGuard: CanActivateFn = (): Observable<boolean> => {
  const router: Router = inject(Router);
  const authorizationService: AuthorizationService = inject(AuthorizationService);

  return authorizationService.isAuthenticated$.pipe(
    take(1), // Ensure the observable completes after emitting the first value
    map((isMember: boolean) => {
      if (isMember) {
        return true;
      } else {
        router.navigate(['/login']);
        return false;
      }
    })
  );
};