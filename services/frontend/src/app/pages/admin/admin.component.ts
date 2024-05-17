import { Component } from '@angular/core';
import { UiTabsComponent } from '../../components/ui-tabs/ui-tabs.component';
import { UiTabItemComponent } from '../../components/ui-tab-item/ui-tab-item.component';
import { AddSeasonFormComponent } from '../../components/add-season-form/add-season-form.component';
import { DeleteSeasonFormComponent } from '../../components/delete-season-form/delete-season-form.component';
import { AddTournamentFormComponent } from '../../components/add-tournament-form/add-tournament-form.component';
import { DeleteTournamentFormComponent } from '../../components/delete-tournament-form/delete-tournament-form.component';
import { TournamentListComponent } from '../../components/tournament-list/tournament-list.component';

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
    TournamentListComponent
  ],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss', '../../app.component.scss']
})
export class AdminComponent {
  addSeasonTitle = 'Add season';
  deleteSeasonTitle = 'Delete season';

  addTournamentTitle = 'Add tournament';
  deleteTournamentTitle = 'Delete tournament';
  tournamentListTitle = 'Tournament List';
}
