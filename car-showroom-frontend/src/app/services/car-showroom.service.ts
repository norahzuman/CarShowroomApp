import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CarShowroomService {
  private apiUrl = 'http://localhost:8080/car-showrooms';

  constructor(private http: HttpClient) {}

  /**
   * Retrieves a paginated and sorted list of car showrooms.
   * Optionally, a search query can be applied to filter results.
   *
   * @param page - The page number to retrieve (0-based).
   * @param size - The number of items per page.
   * @param sort - The sorting criteria in the format `field,direction`.
   * @param query - An optional search query to filter showrooms by name or other fields.
   * @returns Observable - An observable containing the paginated list of showrooms.
   */
  listShowrooms(
      page: number = 0,
      size: number = 10,
      sort: string = 'name,asc',
      query: string = ''
  ): Observable<any> {
    let params = new HttpParams()
        .set('page', page.toString())
        .set('size', size.toString())
        .set('sort', sort);

    if (query) {
      params = params.set('query', query);
    }
    return this.http.get(`${this.apiUrl}/all`, { params });
  }


  /**
   * Retrieves the details of a specific showroom by its ID.
   *
   * @param showroomId - The ID of the showroom to retrieve.
   * @returns Observable - An observable containing the showroom details.
   */
  getShowroomById(showroomId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${showroomId}`);
  }

  /**
   * Creates a new showroom in the system.
   *
   * @param showroom - The data for the new showroom.
   * @returns Observable - An observable containing the response from the server.
   */
  createShowroom(showroom: any): Observable<any> {
    return this.http.post(this.apiUrl, showroom).pipe(
        catchError(this.handleError)
    );
  }

  /**
   * Updates an existing showroom by its ID.
   *
   * @param showroomId - The ID of the showroom to update.
   * @param showroom - The updated data for the showroom.
   * @returns Observable - An observable containing the response from the server.
   */
  updateShowroom(showroomId: number, showroom: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${showroomId}`, showroom).pipe(
        catchError(this.handleError)
    );
  }

  /**
   * Deletes a showroom (soft delete) by its ID.
   *
   * @param showroomId - The ID of the showroom to delete.
   * @returns Observable - An observable containing the response from the server.
   */
  deleteShowroom(showroomId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${showroomId}`).pipe(
        catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 400) {
      // Return validation errors
      return throwError(error.error);
    } else if (error.status === 404) {
      // Handle not found errors
      return throwError(error.error);
    }
    // Handle other types of errors
    return throwError('An error occurred. Please try again later.');
  }
}
