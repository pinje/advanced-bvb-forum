import { Routes } from '@angular/router';
import { SignupComponent } from './pages/signup/signup.component';
import { LoginComponent } from './pages/login/login.component';
import { adminGuard } from './services/admin.guard';
import { AdminComponent } from './pages/admin/admin.component';
import { ResultsComponent } from './pages/results/results.component';
import { HomeComponent } from './pages/home/home.component';
import { CreatePostComponent } from './pages/create-post/create-post.component';
import { UserComponent } from './pages/user/user.component';
import { authGuard } from './services/auth.guard';
import { NotFoundComponent } from './pages/not-found/not-found.component';

export const routes: Routes = [
    { path: 'signup', component: SignupComponent},
    { path: 'login', component: LoginComponent },
    { path: 'results', component: ResultsComponent},
    { path: 'admin', component: AdminComponent, canActivate: [adminGuard]},
    { path: '', component: HomeComponent},
    { path: 'create-review', component: CreatePostComponent, canActivate: [authGuard]},
    { path: 'user/:id', component: UserComponent},
    { path: '**', component: NotFoundComponent}
];
