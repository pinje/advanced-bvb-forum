import { CommonModule } from '@angular/common';
import { Component, Input, TemplateRef } from '@angular/core';
import { LoginComponent } from '../../pages/login/login.component';

@Component({
  selector: 'ui-tab-item',
  standalone: true,
  imports: [
    CommonModule,
    LoginComponent
  ],
  templateUrl: './ui-tab-item.component.html',
  styleUrl: './ui-tab-item.component.scss'
})
export class UiTabItemComponent {
  @Input() tabName? = 'default';
  @Input() templateRef!: TemplateRef<any>;
}
