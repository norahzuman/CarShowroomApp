import { Component, OnInit } from '@angular/core';
import {AuthService} from "@app/services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  isAuthenticated: boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.isAuthenticated = this.authService.isAuthenticated();

    if (!this.isAuthenticated) {
      this.router.navigate(['/login']); // Redirect to login if not authenticated
    }
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  logout(): void {
    const confirmLogout = window.confirm('Are you sure you want to logout?');
    if (confirmLogout) {
      this.router.navigate(['/login']);
    }
  }

  viewShowrooms(): void {
    this.router.navigate(['/showrooms']);
  }
}
