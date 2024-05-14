import { Routes } from '@angular/router';
import { SignupComponent } from './pages/signup/signup.component';
import { LoginComponent } from './pages/login/login.component';
import { SeasonsComponent } from './pages/seasons/seasons.component';
import { authGuard } from './services/auth.guard';
import { adminGuard } from './services/admin.guard';
import { AdminComponent } from './pages/admin/admin.component';

export const routes: Routes = [
    { path: 'signup', component: SignupComponent},
    { path: 'login', component: LoginComponent },
    { path: 'seasons', component: SeasonsComponent},
    { path: 'admin', component: AdminComponent, canActivate: [adminGuard]}
];
