import { Component, OnInit } from '@angular/core';
import { SeasonService } from '../../services/season.service';
import { of, switchMap } from 'rxjs';
import { CommonModule } from '@angular/common';
import { SeasonCollapsibleComponent } from '../../components/season-collapsible/season-collapsible.component';

@Component({
  selector: 'app-seasons',
  standalone: true,
  imports: [
    CommonModule,
    SeasonCollapsibleComponent
  ],
  templateUrl: './seasons.component.html',
  styleUrls: ['./seasons.component.scss', '../../app.component.scss']
})
export class SeasonsComponent implements OnInit {

  constructor(
    private seasonService: SeasonService
  ) {}

  ngOnInit(): void {
    this.getSeasons()
  }

  seasons: Array<any> = [];

  // need to clear up
  getSeasons(): void {
    this.seasonService.getAllSeasons().pipe(
      switchMap((res) => {
        // Handle success
        this.seasons = res.seasons;
        // reorder
        this.seasons.sort((a, b) => {
          return a.startYear - b.startYear;
        });

        return of(res); // Return whatever you need from the success response
      })
    ).subscribe((res) => {
      // Handle after refresh or success
      console.log('Response: ', res);
    }, (error) => {
      // Handle any errors after refresh
      console.error('Error: ', error);
    });
  }
}
