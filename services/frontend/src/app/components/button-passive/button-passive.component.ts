import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-button-passive',
  standalone: true,
  imports: [],
  templateUrl: './button-passive.component.html',
  styleUrl: './button-passive.component.scss'
})
export class ButtonPassiveComponent {
  @Input() title: string = "";
}
