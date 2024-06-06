import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonActiveComponent } from '../button-active/button-active.component';
import { MatchService } from '../../services/match.service';
import { SeasonService } from '../../services/season.service';
import { firstValueFrom } from 'rxjs';
import { TeamService } from '../../services/team.service';
import { PostService } from '../../services/post.service';
import { AddPostRequest } from '../../models/request/post/addpost-request';

@Component({
  selector: 'app-add-post-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ButtonActiveComponent
  ],
  templateUrl: './add-post-form.component.html',
  styleUrl: './add-post-form.component.scss'
})
export class AddPostFormComponent {
  error: string = "";
  validationErrors: Array<string> = [];
  success: boolean = false;

  constructor(
    private matchService: MatchService,
    private seasonService: SeasonService,
    private teamService: TeamService,
    private postService: PostService
  ) {}

  matches: Array<any> = [];
  seasons: Array<any> = [];
  selectedSeason: number | null = null;

  ngOnInit(): void {
    this.seasonService.getAllSeasons().subscribe({
      next: (res) => {
        this.seasons = res.seasons;
      }
    })
  }

  updateMatchList() {
    if(this.selectedSeason) {
      this.matchService.getAllMatchesBySeason(this.selectedSeason).subscribe({
        next: (res) => {
          this.matches = [...res.matches];
          this.matches.forEach(async match => {
            const homeTeamInfo = await this.getHomeTeamInfo(match.homeTeamId);
            const awayTeamInfo = await this.getAwayTeamInfo(match.awayTeamId);
            match.homeTeamId = homeTeamInfo.homeTeamName;
            match.awayTeamId = awayTeamInfo.awayTeamName;
          });
        }
      })
    }
  }

  async getHomeTeamInfo(teamId: number) {
    const res = await firstValueFrom(this.teamService.getTeam(teamId));
    return {
      homeTeamName: res.teamName
    }
  }

  async getAwayTeamInfo(teamId: number) {
    const res = await firstValueFrom(this.teamService.getTeam(teamId));
    return {
      awayTeamName: res.teamName
    }
  }

  addPostRequest: AddPostRequest = {
    matchId: null,
    review: ''
  }

  onSubmit() {
    this.error = "";
    this.validationErrors = [];
    this.success = false;
    this.postService.addPost(this.addPostRequest).subscribe({
      next: () => {
        // what to do when success
        this.addPostRequest.matchId = -1;
        this.addPostRequest.review = '';
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
