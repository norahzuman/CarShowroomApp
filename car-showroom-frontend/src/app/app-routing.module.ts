import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ShowroomListComponent} from "./pages/showroom-list/showroom-list.component";
import {ShowroomFormComponent} from "./pages/showroom-form/showroom-form.component";
import {CarListComponent} from "./pages/car-list/car-list.component";
import {HomeComponent} from "./pages/home/home.component";
import {LoginComponent} from "./pages/login/login.component";
import {AuthGuard} from "./guards/auth.guard";
import {ShowroomDetailsComponent} from "./pages/showroom-details/showroom-details.component";
import {CarFormComponent} from "./pages/car-form/car-form.component";

const routes: Routes = [

{ path: '', redirectTo: '/home', pathMatch: 'full' }, // Redirect empty path to home
{ path: 'home', component: HomeComponent },

// Login Routes
{ path: 'login', component: LoginComponent },

// Showrooms Routes - Protected by AuthGuard
{
  path: 'showrooms',
  component: ShowroomListComponent,
  canActivate: [AuthGuard],
  data: { roles: ['USER', 'ADMIN'] },
},
{
  path: 'showrooms/details/:id',
  component: ShowroomDetailsComponent,
  canActivate: [AuthGuard],
  data: { roles: ['USER', 'ADMIN'] }
},
{
  path: 'showrooms/add',
  component: ShowroomFormComponent,
  canActivate: [AuthGuard],
  data: { roles: ['ADMIN'] },
},
{
  path: 'showrooms/edit',
  component: ShowroomFormComponent,
  canActivate: [AuthGuard],
  data: { roles: ['ADMIN'] },
},

// Car Routes - Protected by AuthGuard
{
  path: 'showroom-cars',
  component: CarListComponent,
  canActivate: [AuthGuard],
  data: { roles: ['USER', 'ADMIN'] },
},
{
  path: 'cars/add',
  component: CarFormComponent,
  canActivate: [AuthGuard],
  data: { role: 'ADMIN' }
},

// Wildcard Route for a 404 Page (Redirect to home or any specific page)
{ path: '**', redirectTo: '/home' },
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
