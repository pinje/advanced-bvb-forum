import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonActiveComponent } from '../button-active/button-active.component';
import { DeleteTeamRequest } from '../../models/request/team/deleteteam-request';
import { TeamService } from '../../services/team.service';
import { PopupService } from '../../services/popup.service';
import { PopupComponent } from '../popup/popup.component';

@Component({
  selector: 'app-delete-team-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ButtonActiveComponent,
    PopupComponent
  ],
  templateUrl: './delete-team-form.component.html',
  styleUrl: './delete-team-form.component.scss'
})
export class DeleteTeamFormComponent {
  error: Array<string> = [];
  success: boolean = false;

  deleteTeamRequest: DeleteTeamRequest = {
    teamId: -1
  }

  constructor(
    private teamService: TeamService,
    private popupService: PopupService
  ) {}

  teams: Array<any> = [];

  ngOnInit(): void {
    this.teamService.getAllTeams().subscribe({
      next: (res) => {
        this.teams = res.teams;
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
    this.teamService.deleteTeam(this.deleteTeamRequest).subscribe({
      next: () => {
        this.hidePopup();
        this.deleteTeamRequest.teamId = -1;
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
