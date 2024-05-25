import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PopupService {

  private popupVisibleSubject = new BehaviorSubject<boolean>(false);
  popupVisible$ = this.popupVisibleSubject.asObservable();

  showPopup() {
    this.popupVisibleSubject.next(true);
  }

  hidePopup() {
    this.popupVisibleSubject.next(false);
  }
}
