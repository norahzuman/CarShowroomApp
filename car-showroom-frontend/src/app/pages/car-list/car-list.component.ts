import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CarService} from "@app/services/car.service";
import {CarModel} from "@app/models/car.model";
import {AuthService} from "@app/services/auth.service";

@Component({
  selector: 'app-car-list',
  templateUrl: './car-list.component.html',
  styleUrls: ['./car-list.component.css']
})
export class CarListComponent implements OnInit {

  showroomId: any = '';
  showroomName: string = '';
  contactNumber: any = '';
  cars: CarModel[] = [];
  searchQuery = '';
  page = 0;
  size = 10;
  totalPages = 0;
  isAdmin: boolean = false;

  constructor(
    private carService: CarService,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.isAdmin = this.authService.getUserRoles().includes('ADMIN'); // Check if the user is an admin
    this.route.queryParams.subscribe((params) => {
      this.showroomId = params['showroomId'];
      this.showroomName = params['showroomName'];
      this.contactNumber = params['contactNumber'];
    });
    this.loadCars();
  }

  navigateToAddCar(): void {
    this.router.navigate(['/cars/add'], {
      queryParams: { showroomId: this.showroomId, showroomName: this.showroomName },
    });
  }

  loadCars(): void {
    // Include search query in the filters
    const filters: { [key: string]: string } = {};
    if (this.searchQuery.trim()) {
      filters['query'] = this.searchQuery.trim();
    }

    this.carService
        .listCarsByShowroomId(this.showroomId, filters, this.page, this.size, 'id', 'asc')
        .subscribe({
          next: (response) => {
            this.cars = response.content;
            this.totalPages = response.totalPages; // Update total pages for pagination
          },
          error: (err) => {
            console.error('Error fetching cars:', err);
          },
        });
  }

  filterCars(): void {
    this.page = 0; // Reset to first page when filtering
    this.loadCars();
  }

  nextPage(): void {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.loadCars();
    }
  }

  prevPage(): void {
    if (this.page > 0) {
      this.page--;
      this.loadCars();
    }
  }
}
