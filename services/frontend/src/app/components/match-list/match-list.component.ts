import { CommonModule } from '@angular/common';
import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TournamentService } from '../../services/tournament.service';
import { DisplayMatch } from '../../models/request/match/displaymatch';
import { firstValueFrom } from 'rxjs';
import { TeamService } from '../../services/team.service';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faTrash } from '@fortawesome/free-solid-svg-icons';
import { MatchService } from '../../services/match.service';

@Component({
  selector: 'app-match-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    FontAwesomeModule
  ],
  templateUrl: './match-list.component.html',
  styleUrl: './match-list.component.scss'
})
export class MatchListComponent implements OnChanges {
  @Input() matches: any;

  trash = faTrash;

  constructor(
    private tournamentService: TournamentService,
    private teamService: TeamService,
    private matchService: MatchService
  ) { }

  realmatches: DisplayMatch[] = [];

  tournamentLogo: string = "";

  homeTeamName: string = "";
  homeTeamLogo: string = "";
  awayTeamName: string = "";
  awayTeamLogo: string = "";

  async ngOnChanges(changes: SimpleChanges): Promise<void> {
    if (changes['matches']) {
      this.matches.sort((a: any, b: any) => new Date(a.matchDate).getTime() - new Date(b.matchDate).getTime());
      const tournamentInfoPromises = this.matches.map(async (match: any) => {
        const tournamentInfo = await this.getTournamentInfo(match.tournamentId);
        const homeTeamInfo = await this.getHomeTeamInfo(match.homeTeamId);
        const awayTeamInfo = await this.getAwayTeamInfo(match.awayTeamId);
        return {
          tournamentLogo: tournamentInfo.tournamentLogo,
          matchId: match.id,
          homeTeamName: homeTeamInfo.homeTeamName,
          homeTeamLogo: homeTeamInfo.homeTeamLogo,
          awayTeamName: awayTeamInfo.awayTeamName,
          awayTeamLogo: awayTeamInfo.awayTeamLogo,
          homeTeamScore: match.homeTeamScore,
          awayTeamScore: match.awayTeamScore
        };
      });

      this.realmatches = await Promise.all(tournamentInfoPromises);
    }
  }

  async getTournamentInfo(tournamentId: number) {
    const res = await firstValueFrom(this.tournamentService.getTournament(tournamentId));
    return {
      tournamentLogo: res.logoId
    };
  }

  async getHomeTeamInfo(teamId: number) {
    const res = await firstValueFrom(this.teamService.getTeam(teamId));
    return {
      homeTeamName: res.teamName,
      homeTeamLogo: res.logoId
    }
  }

  async getAwayTeamInfo(teamId: number) {
    const res = await firstValueFrom(this.teamService.getTeam(teamId));
    return {
      awayTeamName: res.teamName,
      awayTeamLogo: res.logoId
    }
  }

  error: Array<string> = [];
  success: boolean = false;

  delete(matchId: number) {
    this.error = [];
    this.success = false;
    this.matchService.deleteMatch(matchId).subscribe({
      next: () => {
        this.success = true;
        this.realmatches = this.realmatches.filter(match => match.matchId !== matchId);
      },
      error: (err) => {
        if (err.error) {
          this.error = err.error.error
        }
      }
    })
  }
}
