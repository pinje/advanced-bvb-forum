import { Component, Input, OnInit } from '@angular/core';
import { PopupService } from '../../services/popup.service';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-popup',
  standalone: true,
  imports: [
    CommonModule,
    FontAwesomeModule
  ],
  templateUrl: './popup.component.html',
  styleUrl: './popup.component.scss'
})
export class PopupComponent implements OnInit {
  @Input() title: string = 'Default Title';
  visible = false;
  xmark = faXmark;

  constructor(
    private popupService: PopupService
  ) {}

  ngOnInit(): void {
    this.popupService.popupVisible$.subscribe(visible => {
      this.visible = visible;
    });
  }

  close() {
    this.popupService.hidePopup();
  }



}
