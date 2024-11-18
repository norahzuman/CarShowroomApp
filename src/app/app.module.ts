import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ShowroomListComponent } from './pages/showroom-list/showroom-list.component';
import { ShowroomFormComponent } from './pages/showroom-form/showroom-form.component';
import { CarListComponent } from './pages/car-list/car-list.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { HomeComponent } from './pages/home/home.component';
import {LoginComponent} from "./pages/login/login.component";
import { ShowroomDetailsComponent } from './pages/showroom-details/showroom-details.component';
import { CarFormComponent } from './pages/car-form/car-form.component';
import { BackButtonComponent } from '@shared/back-button/back-button.component';

@NgModule({
  declarations: [
    AppComponent,
    ShowroomListComponent,
    ShowroomFormComponent,
    CarListComponent,
    LoginComponent,
    HomeComponent,
    ShowroomDetailsComponent,
    CarFormComponent,
    BackButtonComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
