import { CommonModule } from '@angular/common';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonActiveComponent } from '../button-active/button-active.component';
import { AddTournamentRequest } from '../../models/request/tournament/addtournament-request';
import { TournamentService } from '../../services/tournament.service';

@Component({
  selector: 'app-add-tournament-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ButtonActiveComponent
  ],
  templateUrl: './add-tournament-form.component.html',
  styleUrl: './add-tournament-form.component.scss'
})
export class AddTournamentFormComponent {
  @ViewChild('fileInput') fileInput!: ElementRef;

  error: string = "";
  validationErrors: Array<string> = [];
  success: boolean = false;

  constructor(
    private tournamentService: TournamentService
  ) {}

  addTournamentRequest: AddTournamentRequest = {
    tournamentName: '',
    logo: new File([], ''), 
    category: ''
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    this.addTournamentRequest.logo = file;
  }

  onSubmit() {
    this.error = "";
    this.validationErrors = [];
    this.success = false;
    const formData = new FormData();
    formData.append('tournamentName', this.addTournamentRequest.tournamentName);
    formData.append('logo', this.addTournamentRequest.logo);
    formData.append('category', this.addTournamentRequest.category);

    this.tournamentService.addTournament(formData).subscribe({
      next: () => {
        this.addTournamentRequest.tournamentName = '';
        this.addTournamentRequest.logo = new File([], '');
        this.addTournamentRequest.category = '';
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
