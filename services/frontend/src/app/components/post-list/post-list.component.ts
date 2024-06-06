import { Component, Input, SimpleChanges } from '@angular/core';
import { DisplayPost } from '../../models/request/post/displaypost';
import { firstValueFrom } from 'rxjs';
import { TournamentService } from '../../services/tournament.service';
import { TeamService } from '../../services/team.service';
import { MatchService } from '../../services/match.service';
import { CommonModule } from '@angular/common';
import { MomentModule } from 'ngx-moment';
import { AuthenticationService } from '../../services/authentication.service';
import { AuthorizationService } from '../../services/authorization.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-post-list',
  standalone: true,
  imports: [
    CommonModule,
    MomentModule,
    RouterLink
  ],
  templateUrl: './post-list.component.html',
  styleUrl: './post-list.component.scss'
})
export class PostListComponent {
  @Input() posts: any;

  isAuthenticated: boolean = false;
  isAdmin: boolean = false;

  constructor(
    private tournamentService: TournamentService,
    private teamService: TeamService,
    private matchService: MatchService,
    private authorizationService: AuthorizationService,
    private authService: AuthenticationService
  ) { }


  displayPosts: DisplayPost[] = [];

  async ngOnChanges(changes: SimpleChanges): Promise<void> {
    if (changes['posts']) {
      this.authorizationService.checkUserRole();

      this.authorizationService.isAuthenticated$.subscribe((isAuthenticated: boolean) => {
        this.isAuthenticated = isAuthenticated;
      });

      this.authorizationService.isAdmin$.subscribe((isAdmin: boolean) => {
        this.isAdmin = isAdmin;
      });

      this.posts.sort((a: any, b: any) => new Date(a.postDate).getTime() - new Date(b.postDate).getTime());
      const infoPromises = this.posts.map(async (post: any) => {
        
        const matchInfo = await this.getMatchInfo(post.matchId);
        const tournamentInfo = await this.getTournamentInfo(matchInfo.tournamentId);
        const homeTeamInfo = await this.getHomeTeamInfo(matchInfo.homeTeamId);
        const awayTeamInfo = await this.getAwayTeamInfo(matchInfo.awayTeamId);

        return {
          postId: post.postId,
          userId: post.userId,
          username: post.username,
          postDate: post.postDate,
          review: post.review_text,
          matchId: post.matchId,
          tournamentName: tournamentInfo.tournamentName,
          homeTeamName: homeTeamInfo.homeTeamName,
          homeTeamLogo: homeTeamInfo.homeTeamLogo,
          awayTeamName: awayTeamInfo.awayTeamName,
          awayTeamLogo: awayTeamInfo.awayTeamLogo,
          homeTeamScore: matchInfo.homeTeamScore,
          awayTeamScore: matchInfo.awayTeamScore,
          matchDate: matchInfo.matchDate
              };
      });

      this.displayPosts = await Promise.all(infoPromises);
    }
  }

  async getTournamentInfo(tournamentId: number) {
    const res = await firstValueFrom(this.tournamentService.getTournament(tournamentId));
    return {
      tournamentName: res.tournamentName
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

  async getMatchInfo(matchId: number) {
    const res = await firstValueFrom(this.matchService.getMatchById(matchId));
    return {
      matchDate: res.matchDate,
      seasonId: res.seasonId,
      tournamentId: res.tournamentId,
      homeTeamId: res.homeTeamId,
      awayTeamId: res.awayTeamId,
      homeTeamScore: res.homeTeamScore,
      awayTeamScore: res.awayTeamScore
    }
  }
}
