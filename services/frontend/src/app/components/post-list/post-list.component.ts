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
import { PopupComponent } from '../popup/popup.component';
import { PopupService } from '../../services/popup.service';
import { PostService } from '../../services/post.service';
import { faTrash } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FormsModule } from '@angular/forms';
import { ButtonPassiveComponent } from '../button-passive/button-passive.component';

@Component({
  selector: 'app-post-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MomentModule,
    RouterLink,
    PopupComponent,
    FontAwesomeModule,
    ButtonPassiveComponent
  ],
  templateUrl: './post-list.component.html',
  styleUrl: './post-list.component.scss'
})
export class PostListComponent {
  @Input() posts: any;

  trash = faTrash;

  isAuthenticated: boolean = false;
  isAdmin: boolean = false;
  userId: string = '';

  constructor(
    private tournamentService: TournamentService,
    private teamService: TeamService,
    private matchService: MatchService,
    private authorizationService: AuthorizationService,
    private authService: AuthenticationService,
    private popupService: PopupService,
    private postService: PostService
  ) { }


  displayPosts: DisplayPost[] = [];

  showPopup(id: string) {
    this.popupService.showPopup(id);
  }

  hidePopup(id: string) {
    this.popupService.hidePopup(id);
  }

  async ngOnChanges(changes: SimpleChanges): Promise<void> {
    if (changes['posts']) {
      this.authorizationService.checkUserRole();
      this.authorizationService.checkUserId();

      this.authorizationService.isAuthenticated$.subscribe((isAuthenticated: boolean) => {
        this.isAuthenticated = isAuthenticated;
      });

      this.authorizationService.isAdmin$.subscribe((isAdmin: boolean) => {
        this.isAdmin = isAdmin;
      });

      this.authorizationService.userId$.subscribe((userId: string) => {
        this.userId = userId;
      });

      this.posts.sort((a: any, b: any) => new Date(a.postDate).getTime() - new Date(b.postDate).getTime());
      const infoPromises = this.posts.map(async (post: any) => {
        
        const matchInfo = await this.getMatchInfo(post.matchId);
        const tournamentInfo = await this.getTournamentInfo(matchInfo.tournamentId);
        const homeTeamInfo = await this.getHomeTeamInfo(matchInfo.homeTeamId);
        const awayTeamInfo = await this.getAwayTeamInfo(matchInfo.awayTeamId);

        return {
          postId: post.id,
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

  error: Array<string> = [];
  success: boolean = false;

  delete(postId: number) {
    this.error = [];
    this.success = false;
    this.postService.deletePost(postId).subscribe({
      next: () => {
        this.hidePopup(postId.toString());
        this.success = true;
        this.displayPosts = this.displayPosts.filter(post => post.postId !== postId);
      },
      error: (err) => {
        if (err.error) {
          this.error = err.error.error
        }
      }
    })
  }
}
