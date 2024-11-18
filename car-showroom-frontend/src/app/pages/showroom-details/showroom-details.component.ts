import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {CarShowroomService} from "@app/services/car-showroom.service";
import {ShowroomModel} from "@app/models/showroom.model";

@Component({
  selector: 'app-showroom-details',
  templateUrl: './showroom-details.component.html',
  styleUrls: ['./showroom-details.component.css']
})
export class ShowroomDetailsComponent implements OnInit {

  showroomId: number = 0;
  showroomDetails: ShowroomModel = new ShowroomModel();

  constructor(
      private route: ActivatedRoute,
      private showroomService: CarShowroomService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.showroomId = params['id'];
      this.fetchShowroomDetails();
    });
  }

  fetchShowroomDetails(): void {
    this.showroomService.getShowroomById(this.showroomId).subscribe({
      next: (response) => {
        this.showroomDetails = response;
      },
      error: (err) => {
        console.error('Error fetching showroom details:', err);
      }
    });
  }
}
