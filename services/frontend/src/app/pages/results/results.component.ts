import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatchListComponent } from '../../components/match-list/match-list.component';
import { MatchService } from '../../services/match.service';
import { SeasonService } from '../../services/season.service';
import { FormsModule } from '@angular/forms';
import { ResultListComponent } from '../../components/result-list/result-list.component';

@Component({
  selector: 'app-results',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ResultListComponent
  ],
  templateUrl: './results.component.html',
  styleUrls: ['./results.component.scss', '../../app.component.scss']
})
export class ResultsComponent {

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
