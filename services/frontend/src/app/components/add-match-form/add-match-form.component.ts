import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonActiveComponent } from '../button-active/button-active.component';
import { AddMatchRequest } from '../../models/request/match/addmatch-request';
import { MatchService } from '../../services/match.service';
import { TeamService } from '../../services/team.service';
import { SeasonService } from '../../services/season.service';
import { TournamentService } from '../../services/tournament.service';

@Component({
  selector: 'app-add-match-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ButtonActiveComponent
  ],
  templateUrl: './add-match-form.component.html',
  styleUrl: './add-match-form.component.scss'
})
export class AddMatchFormComponent {
  error: string = "";
  validationErrors: Array<string> = [];
  success: boolean = false;

  teams: Array<any> = [];
  tournaments: Array<any> = [];
  seasons: Array<any> = [];

  selectedDate: string = "";


  addMatchRequest: AddMatchRequest = {
    matchDate: null,
    seasonId: null,
    tournamentId: null,
    homeTeamId: null,
    awayTeamId: null,
    homeTeamScore: "",
    awayTeamScore: ""
  }

  constructor(
    private matchService: MatchService,
    private teamService: TeamService,
    private seasonService: SeasonService,
    private tournamentService:TournamentService
  ) {}

  ngOnInit(): void {
    this.teamService.getAllTeams().subscribe({
      next: (res) => {
        this.teams = res.teams;
      }
    });

    this.tournamentService.getAllTournaments().subscribe({
      next: (res) => {
        this.tournaments = res.tournaments;
      }
    });

    this.seasonService.getAllSeasons().subscribe({
      next: (res) => {
        this.seasons = res.seasons;
      }
    });
  }

  convertDate() {
    if(this.selectedDate) {
      const date = new Date(this.selectedDate);
      this.addMatchRequest.matchDate = date.getTime();
    }
  }

  onSubmit() {
    this.error = "";
    this.validationErrors = [];
    this.success = false;
    this.matchService.addMatch(this.addMatchRequest).subscribe({
      next: () => {
        this.selectedDate = "";
        this.addMatchRequest.matchDate = null;

        this.addMatchRequest.seasonId = null;
        this.addMatchRequest.tournamentId = null;
        this.addMatchRequest.homeTeamId = null;
        this.addMatchRequest.awayTeamId = null;

        this.addMatchRequest.homeTeamScore = '';
        this.addMatchRequest.awayTeamScore = '';
        this.success = true;

        this.ngOnInit();
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
