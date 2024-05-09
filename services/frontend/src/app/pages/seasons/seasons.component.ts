import { Component, OnInit } from '@angular/core';
import { SeasonService } from '../../services/season.service';
import { AuthenticationService } from '../../services/authentication.service';
import { TokenService } from '../../services/token.service';
import { catchError, of, switchMap } from 'rxjs';
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
    private seasonService: SeasonService,
    private authService: AuthenticationService,
    private tokenService: TokenService
  ) {}

  ngOnInit(): void {
    this.getSeasons()
  }

  seasons: Array<any> = [];

  // need to clear up
  getSeasons(): void {
    this.seasonService.getAllSeasons().pipe(
      catchError((error) => {
        if (error.status === 500) {
          return this.authService.refresh().pipe(
            switchMap((res) => {
              // Store the refreshed data in local storage
              this.tokenService.token = res.accessToken as string;
              // redo getSeasons
              return this.seasonService.getAllSeasons();
            })
          )
        } else {
          throw error; // Re-throw the error if it's not a 500 error
        }
      }),
      switchMap((res) => {
        // Handle success
        console.log(res.seasons[0])
        this.seasons = res.seasons;
        // reorder
        this.seasons.sort((a, b) => {
          return this.seasons.indexOf(a.startYear) - this.seasons.indexOf(b.startYear);
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
