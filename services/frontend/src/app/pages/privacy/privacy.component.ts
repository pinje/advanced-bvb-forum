import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-privacy',
  standalone: true,
  imports: [
    RouterLink,
    CommonModule
  ],
  templateUrl: './privacy.component.html',
  styleUrls: ['./privacy.component.scss', '../../app.component.scss']
})
export class PrivacyComponent {

}
