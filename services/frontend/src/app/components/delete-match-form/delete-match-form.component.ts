import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonActiveComponent } from '../button-active/button-active.component';
import { MatchService } from '../../services/match.service';
import { SeasonService } from '../../services/season.service';
import { MatchListComponent } from '../match-list/match-list.component';

@Component({
  selector: 'app-delete-match-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ButtonActiveComponent,
    MatchListComponent
  ],
  templateUrl: './delete-match-form.component.html',
  styleUrl: './delete-match-form.component.scss'
})
export class DeleteMatchFormComponent {
  error: Array<string> = [];
  success: boolean = false;

  selectedSeason: number | null = null;

  constructor(
    private matchService: MatchService,
    private seasonService: SeasonService
  ) {}

  matches: Array<any> = [];
  seasons: Array<any> = [];

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
        }
      })
    }
  }
}
