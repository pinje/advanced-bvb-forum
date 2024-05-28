import { Component, Input, OnInit } from '@angular/core';
import { PopupService } from '../../services/popup.service';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';
import { Observable } from 'rxjs';

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
  @Input() id: string = '';
  xmark = faXmark;

  visible$: Observable<boolean> | undefined;

  constructor(private popupService: PopupService) { }

  ngOnInit() {
    if (this.id) {
      this.visible$ = this.popupService.getPopupVisibility$(this.id);
    }
  }

  close() {
    this.popupService.hidePopup(this.id);
  }
}
