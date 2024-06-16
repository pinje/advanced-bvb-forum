import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-cookies',
  standalone: true,
  imports: [
    RouterLink,
    CommonModule
  ],
  templateUrl: './cookies.component.html',
  styleUrls: ['./cookies.component.scss', '../../app.component.scss']
})
export class CookiesComponent {

}
