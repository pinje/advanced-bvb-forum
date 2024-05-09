import { CommonModule } from '@angular/common';
import { Component, ContentChildren, QueryList } from '@angular/core';
import { UiTabItemComponent } from '../ui-tab-item/ui-tab-item.component';

@Component({
  selector: 'ui-tabs',
  standalone: true,
  imports: [
    CommonModule,
    UiTabItemComponent
  ],
  templateUrl: './ui-tabs.component.html',
  styleUrl: './ui-tabs.component.scss'
})
export class UiTabsComponent {
  @ContentChildren(UiTabItemComponent) tabs!: QueryList<UiTabItemComponent>;
  activeComponent!: UiTabItemComponent;

  ngAfterContentInit() {
    this.activeComponent = this.tabs.first;
  }

  activateTab(tab: UiTabItemComponent) {
    this.activeComponent = tab;
  }
}
