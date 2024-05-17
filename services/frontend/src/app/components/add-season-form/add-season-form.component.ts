import { Component } from '@angular/core';
import { AddSeasonRequest } from '../../models/request/addseason-request';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonActiveComponent } from '../button-active/button-active.component';
import { SeasonService } from '../../services/season.service';

@Component({
  selector: 'app-add-season-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ButtonActiveComponent
  ],
  templateUrl: './add-season-form.component.html',
  styleUrl: './add-season-form.component.scss'
})
export class AddSeasonFormComponent {

  error: string = "";
  validationErrors: Array<string> = [];
  success: boolean = false;

  addSeasonRequest: AddSeasonRequest = {
    startYear: '',
    endYear: ''
  }

  constructor(
    private seasonService: SeasonService
  ) {}

  updateEndYear() {
    if (this.addSeasonRequest.startYear) {
      const newEndYear = parseInt(this.addSeasonRequest.startYear, 10) + 1;
      this.addSeasonRequest.endYear = newEndYear.toString();
    }
  }

  onSubmit() {
    this.error = "";
    this.validationErrors = [];
    this.success = false;
    this.seasonService.addSeason(this.addSeasonRequest).subscribe({
      next: () => {
        // what to do when success
        this.addSeasonRequest.startYear = '';
        this.addSeasonRequest.endYear = '';
        this.success = true;
      },
      error: (err) => {
        if (err.error.error) {
          this.error = err.error.error;
        } else if (err.error.validationErrors) {
          this.validationErrors = err.error.validationErrors;
        }
      }
    })
  }
}
