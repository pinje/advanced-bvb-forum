import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { DeleteSeasonRequest } from '../../models/request/deleteseason-request';
import { SeasonService } from '../../services/season.service';
import { ButtonActiveComponent } from '../button-active/button-active.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PopupService } from '../../services/popup.service';
import { PopupComponent } from '../popup/popup.component';

@Component({
  selector: 'app-delete-season-form',
  standalone: true,
  imports: [
    ButtonActiveComponent,
    CommonModule,
    FormsModule,
    PopupComponent
  ],
  templateUrl: './delete-season-form.component.html',
  styleUrl: './delete-season-form.component.scss'
})
export class DeleteSeasonFormComponent implements OnInit {

  error: Array<string> = [];
  success: boolean = false;

  deleteSeasonRequest: DeleteSeasonRequest = {
    seasonId: -1
  }

  constructor(
    private seasonService: SeasonService,
    private popupService: PopupService
  ) {}

  seasons: Array<any> = [];

  ngOnInit(): void {
    this.seasonService.getAllSeasons().subscribe({
      next: (res) => {
        this.seasons = res.seasons;
        this.seasons.sort((a, b) => {
          return a.startYear - b.startYear;
        });
      }
    })
  }

  showPopup() {
    this.popupService.showPopup();
  }

  hidePopup() {
    this.popupService.hidePopup();
  }

  onSubmit() {
    this.error = [];
    this.success = false;
    this.seasonService.deleteSeason(this.deleteSeasonRequest).subscribe({
      next: () => {
        this.hidePopup();
        this.deleteSeasonRequest.seasonId = -1;
        this.success = true;
        this.ngOnInit();
      },
      error: (err) => {
        if (err.error) {
          this.error = err.error.error
        }
      }
    })
  }
}
