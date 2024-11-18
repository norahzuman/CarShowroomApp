import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CarShowroomService} from "@app/services/car-showroom.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-showroom-form',
  templateUrl: './showroom-form.component.html',
  styleUrls: ['./showroom-form.component.css']
})
export class ShowroomFormComponent implements OnInit {

  showroomForm!: FormGroup;
  isEditMode: boolean = false;
  showroomId?: number;

  constructor(
      private fb: FormBuilder,
      private showroomService: CarShowroomService,
      private route: ActivatedRoute,
      private router: Router
  ) {}

  ngOnInit(): void {
    // Initialize the form with default values
    this.showroomForm = this.fb.group({
      name: [{ value: '', disabled: this.isEditMode }, [Validators.required, Validators.maxLength(100)]],
      contactNumber: ['', [Validators.required, Validators.pattern(/^\d+$/)]],
      commercialRegistrationNumber: [{ value: '', disabled: this.isEditMode }, [Validators.required]],
      address: ['', [Validators.required, Validators.maxLength(255)]],
      managerName: ['', [Validators.required, Validators.maxLength(100)]],
    });

    // Fetch showroom details if in edit mode
    this.route.queryParams.subscribe(params => {
          if (params['id']) {
            this.showroomId = this.route.snapshot.queryParams['id'];
          }
        });
    if (this.showroomId) {
      this.isEditMode = true;
      this.showroomService.getShowroomById(this.showroomId).subscribe({
        next: (showroom) => {
          this.showroomForm.patchValue({
            name: showroom.name,
            contactNumber: showroom.contactNumber,
            commercialRegistrationNumber: showroom.commercialRegistrationNumber,
            address: showroom.address,
            managerName: showroom.managerName,
          });
        },
        error: (err) => console.error('Error fetching showroom details:', err),
      });
    }
  }

  submitForm(): void {
    if (this.showroomForm.invalid) {
      return;
    }

    const showroomData = this.showroomForm.value;

    if (this.isEditMode && this.showroomId) {
      // Update showroom
      this.showroomService
          .updateShowroom(this.showroomId, showroomData)
          .subscribe({
            next: () => {
            this.router.navigate(['/showrooms']);
          },
            error: (err) => {
              console.error('Error updating showroom:', err);
              alert(`Failed to update showroom: ${err.error}`);
            }
          });
    } else {
      // Create new showroom
      this.showroomService.createShowroom(showroomData).subscribe({
        next: () => {
        alert('Showroom added successfully.');
        this.router.navigate(['/showrooms']);
      },
        error: (err) => {
          console.error('Error creating showroom:', err);
          alert(`Failed to create showroom: ${err.error}`);
        }
      });
    }
  }

  cancel(): void {
    this.router.navigate(['/showrooms']);
  }
}

