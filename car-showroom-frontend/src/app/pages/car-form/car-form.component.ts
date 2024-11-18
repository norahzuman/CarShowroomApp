import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {CarService} from "@app/services/car.service";

@Component({
  selector: 'app-car-form',
  templateUrl: './car-form.component.html',
  styleUrls: ['./car-form.component.css']
})
export class CarFormComponent implements OnInit {

  showroomId!: number;
  showroomName!: string;
  addCarForm!: FormGroup;

  constructor(
      private fb: FormBuilder,
      private carService: CarService,
      private route: ActivatedRoute,
      private router: Router
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.showroomId = params['showroomId'];
      this.showroomName = params['showroomName'];
    });

    this.addCarForm = this.fb.group({
      vin: ['', [Validators.required, Validators.maxLength(25)]],
      maker: ['', Validators.required, Validators.maxLength(25)],
      model: ['', Validators.required, Validators.maxLength(25)],
      modelYear: ['', [Validators.required, Validators.min(1800), Validators.max(new Date().getFullYear())]],
      price: ['', [Validators.required, Validators.min(0)]]
    });
  }

  onSubmit(): void {
    if (this.addCarForm.valid) {
      const newCar = { ...this.addCarForm.value, showroomId: this.showroomId };
      this.carService.createCar(newCar).subscribe({
        next: () => {
          alert('Car added successfully!');
          this.router.navigate(['/showroom-cars'], {
            queryParams: { showroomId: this.showroomId, showroomName: this.showroomName },
          });
        },
        error: (err) => {
          console.error('Error adding car:', err);
          alert('Failed to add car. Please try again.');
        },
      });
    }
  }
}
