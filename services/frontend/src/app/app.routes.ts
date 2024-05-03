import { Routes } from '@angular/router';
import { SignupComponent } from './pages/signup/signup.component';
import { LoginComponent } from './pages/login/login.component';
import { SeasonsComponent } from './pages/seasons/seasons.component';

export const routes: Routes = [
    { path: 'signup', component: SignupComponent},
    { path: 'login', component: LoginComponent },
    { path: 'seasons', component: SeasonsComponent }
];
