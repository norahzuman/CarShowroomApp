import { Component, OnInit } from '@angular/core';
import {CarShowroomService} from "@app/services/car-showroom.service";
import {Router} from "@angular/router";
import {ShowroomModel} from "@app/models/showroom.model";
import {AuthService} from "@app/services/auth.service";

@Component({
  selector: 'app-showroom-list',
  templateUrl: './showroom-list.component.html',
  styleUrls: ['./showroom-list.component.css']
})
export class ShowroomListComponent implements OnInit {

  showrooms: ShowroomModel[] = [];
  page: number = 0;
  size: number = 10;
  sort: string = 'name,asc';
  totalPages: number = 0;
  isAdmin: boolean = false;
  searchQuery: string = '';

  constructor(
    private showroomService: CarShowroomService,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.isAdmin = this.authService.getUserRoles().includes('ADMIN'); // Check if the user is an admin
    this.loadShowrooms();
  }

  // Load showrooms with pagination and sorting
  loadShowrooms(): void {
    this.showroomService
        .listShowrooms(this.page, this.size, this.sort, this.searchQuery)
        .subscribe({
          next: (response) => {
            this.showrooms = response.content;
            this.totalPages = response.totalPages;
          },
          error: (err) => {
            console.error('Error loading showrooms:', err);
            alert(`Failed to load showrooms: ${err.error}`);
          }
        });
  }

  // Filter showrooms by search query
  filterShowrooms(): void {
    this.page = 0; // Reset to the first page
    this.loadShowrooms();
  }

  viewDetails(showroomId: number): void {
    this.router.navigate(['/showrooms/details', showroomId]);
  }

  viewCars(showroomId: number, showroomName: string, contactNumber: number): void {
    this.router.navigate(['/showroom-cars'], { queryParams: { showroomId, showroomName,
        contactNumber, } });
  }

  // Navigate to the add showroom form
  addShowroom(): void {
    this.router.navigate(['/showrooms/add']);
  }

  // Navigate to the edit showroom form for a specific showroom ID
  editShowroom(id: number): void {
    this.router.navigate(['/showrooms/edit'], { queryParams: { id } });
  }

  // Delete a showroom by its ID
  deleteShowroom(id: number): void {
    const confirmDelete = window.confirm('Are you sure you want to delete this showroom?');
    if (confirmDelete) {
      this.showroomService.deleteShowroom(id).subscribe({
        next: () => {
          alert('Showroom deleted successfully.');
          this.loadShowrooms(); // Reload the list after deletion
        },
        error: (err) => {
          console.error('Error deleting showroom:', err);
          alert(`Failed to delete showroom: ${err.error}`);
        }
      });
    }
  }

  // Navigate to the next page
  nextPage(): void {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.loadShowrooms();
    }
  }

  // Navigate to the previous page
  prevPage(): void {
    if (this.page > 0) {
      this.page--;
      this.loadShowrooms();
    }
  }
}
