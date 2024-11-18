import { Component } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-back-button',
  template: `
    <button class="btn btn-secondary btn-sm" (click)="goBack()">
      <i class="fas fa-arrow-left"></i> Back
    </button>
  `,
  styleUrls: ['./back-button.component.css']
})
export class BackButtonComponent {
  constructor(private location: Location) {}

  goBack(): void {
    this.location.back();
  }
}
