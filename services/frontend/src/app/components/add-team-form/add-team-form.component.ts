import { CommonModule } from '@angular/common';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonActiveComponent } from '../button-active/button-active.component';
import { TeamService } from '../../services/team.service';
import { AddTeamRequest } from '../../models/request/team/addteam-request';

@Component({
  selector: 'app-add-team-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ButtonActiveComponent
  ],
  templateUrl: './add-team-form.component.html',
  styleUrl: './add-team-form.component.scss'
})
export class AddTeamFormComponent {
  @ViewChild('fileInput') fileInput!: ElementRef;

  error: string = "";
  validationErrors: Array<string> = [];
  success: boolean = false;

  constructor(
    private teamService: TeamService
  ) {}

  addTeamRequest: AddTeamRequest = {
    teamName: '',
    logo: new File([], '')
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    this.addTeamRequest.logo = file;
  }

  onSubmit() {
    this.error = "";
    this.validationErrors = [];
    this.success = false;
    const formData = new FormData();
    formData.append('teamName', this.addTeamRequest.teamName);
    formData.append('logo', this.addTeamRequest.logo);

    this.teamService.addTeam(formData).subscribe({
      next: () => {
        this.addTeamRequest.teamName = '';
        this.addTeamRequest.logo = new File([], '');
        this.fileInput.nativeElement.value = '';
        this.success = true;
      },
      error: (err) => {
        if (err.error.error) {
          this.error = err.error.error;
        } else if (err.error.validationErrors) {
          this.validationErrors = err.error.validationErrors;
        }
      }
    })
  }
}
