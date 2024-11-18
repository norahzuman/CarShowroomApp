import { Injectable } from '@angular/core';
import {catchError, Observable, tap, throwError} from "rxjs";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/auth';
  private user: any = null;

  constructor(private http: HttpClient) {}

  /**
   * Sends login credentials to the backend.
   * @param username - The user's username
   * @param password - The user's password
   * @returns An Observable of the backend response
   */
  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, { username, password }).pipe(
        tap((response) => {
          this.user = response; // Store user details in memory
        }),
        catchError(this.handleError)
    );
  }

  /**
   * Checks if the user is logged in.
   */
  isAuthenticated(): boolean {
    return this.user != null;
  }

  getUserRoles(): string[] {
    if (this.user && this.user.roles) {
      return this.user.roles;
    }
    return []; // Return an empty array if no roles are found
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 400) {
      // Handle bad request errors
      return throwError(error.error);
    } else if (error.status === 404) {
      // Handle not found errors
      return throwError(error.error);
    }
    // Handle other types of errors
    return throwError('An error occurred. Please try again later.');
  }
}
