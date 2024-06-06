import { CommonModule } from '@angular/common';
import { Component, Input, SimpleChanges } from '@angular/core';
import { DisplayMatch } from '../../models/request/match/displaymatch';
import { firstValueFrom } from 'rxjs';
import { TournamentService } from '../../services/tournament.service';
import { TeamService } from '../../services/team.service';

@Component({
  selector: 'app-result-list',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './result-list.component.html',
  styleUrl: './result-list.component.scss'
})
export class ResultListComponent {
  @Input() matches: any;

  constructor(
    private tournamentService: TournamentService,
    private teamService: TeamService
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
          awayTeamScore: match.awayTeamScore,
          matchDate: match.matchDate
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
}
