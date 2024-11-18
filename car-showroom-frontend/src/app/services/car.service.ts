import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CarService {
  private baseUrl = 'http://localhost:8080/car';

  constructor(private http: HttpClient) {}

  /**
   * Creates a new car
   * @param carData - The data required to create a car
   * @returns An Observable of the created car
   */
  createCar(carData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}`, carData).pipe(
        catchError(this.handleError)
    );
  }

  /**
   * Fetches a paginated and filtered list of cars
   * @param showroomId - The id of the showroom to fetch the list of cars
   * @param filters - An object containing filter key-value pairs
   * @param page - The page number to fetch
   * @param size - The number of items per page
   * @param sortBy - The field to sort by
   * @param sortDirection - The direction of sorting ('asc' or 'desc')
   * @returns An Observable of the paginated car list
   */
  listCarsByShowroomId(
      showroomId: number,
      filters: { [key: string]: string } = {},
      page: number = 0,
      size: number = 10,
      sortBy: string = 'id',
      sortDirection: string = 'asc'
  ): Observable<any> {
    let params = new HttpParams()
        .set('page', page.toString())
        .set('size', size.toString())
        .set('sortBy', sortBy)
        .set('sortDirection', sortDirection);

    params = params.set('showroomId', showroomId.toString());

    for (const key in filters) {
      if (filters.hasOwnProperty(key)) {
        params = params.set(key, filters[key]);
      }
    }
    return this.http.get(`${this.baseUrl}/showroom-cars`, { params });
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 400) {
      // Return validation errors
      return throwError(error.error);
    }
    // Handle other types of errors
    return throwError('An error occurred. Please try again later.');
  }
}
