import { Component } from '@angular/core';
import { UiTabsComponent } from '../../components/ui-tabs/ui-tabs.component';
import { UiTabItemComponent } from '../../components/ui-tab-item/ui-tab-item.component';
import { LoginFormComponent } from '../../components/login-form/login-form.component';
import { SignupComponent } from '../signup/signup.component';
import { SignupFormComponent } from '../../components/signup-form/signup-form.component';
import { AddSeasonFormComponent } from '../../components/add-season-form/add-season-form.component';
import { DeleteSeasonFormComponent } from '../../components/delete-season-form/delete-season-form.component';
import { AddSeasonRequest } from '../../models/request/addaseason-request';
import { SeasonService } from '../../services/season.service';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [
    UiTabsComponent,
    UiTabItemComponent,
    AddSeasonFormComponent,
    DeleteSeasonFormComponent
  ],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss', '../../app.component.scss']
})
export class AdminComponent {
  addSeasonTitle = 'Add season';
  deleteSeasonTitle = 'Delete season';
}
