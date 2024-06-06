import { Routes } from '@angular/router';
import { SignupComponent } from './pages/signup/signup.component';
import { LoginComponent } from './pages/login/login.component';
import { adminGuard } from './services/admin.guard';
import { AdminComponent } from './pages/admin/admin.component';
import { ResultsComponent } from './pages/results/results.component';
import { HomeComponent } from './pages/home/home.component';
import { CreatePostComponent } from './pages/create-post/create-post.component';

export const routes: Routes = [
    { path: 'signup', component: SignupComponent},
    { path: 'login', component: LoginComponent },
    { path: 'results', component: ResultsComponent},
    { path: 'admin', component: AdminComponent, canActivate: [adminGuard]},
    { path: '', component: HomeComponent},
    { path: 'create-review', component: CreatePostComponent}
];
