import { Component, Input } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faChevronUp } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-season-collapsible',
  standalone: true,
  imports: [FontAwesomeModule],
  templateUrl: './season-collapsible.component.html',
  styleUrl: './season-collapsible.component.scss'
})
export class SeasonCollapsibleComponent {
  @Input() season: any = [];

  chevronup = faChevronUp;
}
