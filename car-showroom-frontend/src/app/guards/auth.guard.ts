import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import {AuthService} from "../services/auth.service";

@Injectable({
    providedIn: 'root',
})
export class AuthGuard implements CanActivate {
    constructor(private authService: AuthService, private router: Router) {}

    canActivate(route: ActivatedRouteSnapshot): boolean {
        const requiredRoles: string[] = route.data['roles'];
        const userRoles: string[] = this.authService.getUserRoles();

        if (!this.authService.isAuthenticated()) {
            this.router.navigate(['/login']);
            return false;
        }
        // Check if userRoles has at least one role in requiredRoles
        if (requiredRoles && !requiredRoles.some(role => userRoles.includes(role))) {
            this.router.navigate(['/home']);
            return false;
        }
        return true; // User is authenticated and has a matching role
    }
}
