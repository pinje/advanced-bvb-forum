import { Component, OnInit } from '@angular/core';
import { TournamentService } from '../../services/tournament.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-tournament-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './tournament-list.component.html',
  styleUrl: './tournament-list.component.scss'
})
export class TournamentListComponent implements OnInit {

  constructor(
    private tournamentService: TournamentService
  ) {}

  tournaments: Array<any> = [];

  ngOnInit(): void {
    this.tournamentService.getAllTournaments().subscribe({
      next: (res) => {
        this.tournaments = res.tournaments;
      }
    })
  }
}
