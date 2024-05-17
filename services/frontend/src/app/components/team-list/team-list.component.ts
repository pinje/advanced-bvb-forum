import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TeamService } from '../../services/team.service';

@Component({
  selector: 'app-team-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './team-list.component.html',
  styleUrl: './team-list.component.scss'
})
export class TeamListComponent implements OnInit {

  constructor(
    private teamService: TeamService
  ) {}

  teams: Array<any> = [];

  ngOnInit(): void {
    this.teamService.getAllTeams().subscribe({
      next: (res) => {
        this.teams = res.teams;
      }
    })
  }
}
