import { Component } from '@angular/core';
import { UiTabsComponent } from '../../components/ui-tabs/ui-tabs.component';
import { UiTabItemComponent } from '../../components/ui-tab-item/ui-tab-item.component';
import { AddSeasonFormComponent } from '../../components/add-season-form/add-season-form.component';
import { DeleteSeasonFormComponent } from '../../components/delete-season-form/delete-season-form.component';
import { AddTournamentFormComponent } from '../../components/add-tournament-form/add-tournament-form.component';
import { DeleteTournamentFormComponent } from '../../components/delete-tournament-form/delete-tournament-form.component';
import { TournamentListComponent } from '../../components/tournament-list/tournament-list.component';
import { AddTeamFormComponent } from '../../components/add-team-form/add-team-form.component';
import { DeleteTeamFormComponent } from '../../components/delete-team-form/delete-team-form.component';
import { TeamListComponent } from '../../components/team-list/team-list.component';
import { AddMatchFormComponent } from '../../components/add-match-form/add-match-form.component';
import { DeleteMatchFormComponent } from '../../components/delete-match-form/delete-match-form.component';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [
    UiTabsComponent,
    UiTabItemComponent,
    AddSeasonFormComponent,
    DeleteSeasonFormComponent,
    AddTournamentFormComponent,
    DeleteTournamentFormComponent,
    TournamentListComponent,
    AddTeamFormComponent,
    DeleteTeamFormComponent,
    TeamListComponent,
    AddMatchFormComponent,
    DeleteMatchFormComponent
  ],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss', '../../app.component.scss']
})
export class AdminComponent {
  addSeasonTitle = 'Add season';
  deleteSeasonTitle = 'Delete season';

  addTournamentTitle = 'Add tournament';
  deleteTournamentTitle = 'Delete tournament';
  tournamentListTitle = 'Tournament list';

  addTeamTitle = 'Add team';
  deleteTeamTitle = 'Delete team';
  teamListTitle = 'Team list';

  addMatchTitle = 'Add match';
  deleteMatchTitle = 'Delete match';
}
