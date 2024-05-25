import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TournamentService } from '../../services/tournament.service';
import { ButtonActiveComponent } from '../button-active/button-active.component';
import { DeleteTournamentRequest } from '../../models/request/tournament/deletetournament-request';
import { PopupComponent } from '../popup/popup.component';
import { PopupService } from '../../services/popup.service';

@Component({
  selector: 'app-delete-tournament-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ButtonActiveComponent,
    PopupComponent
  ],
  templateUrl: './delete-tournament-form.component.html',
  styleUrl: './delete-tournament-form.component.scss'
})
export class DeleteTournamentFormComponent {
  error: Array<string> = [];
  success: boolean = false;

  deleteTournamentRequest: DeleteTournamentRequest = {
    tournamentId: -1
  }

  constructor(
    private tournamentService: TournamentService,
    private popupService: PopupService
  ) {}

  tournaments: Array<any> = [];

  ngOnInit(): void {
    this.tournamentService.getAllTournaments().subscribe({
      next: (res) => {
        this.tournaments = res.tournaments;
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
    this.tournamentService.deleteTournament(this.deleteTournamentRequest).subscribe({
      next: () => {
        this.hidePopup();
        this.deleteTournamentRequest.tournamentId = -1;
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
