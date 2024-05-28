import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PopupService {

  private popupVisibilityMap = new Map<string, BehaviorSubject<boolean>>();

  private getPopupSubject(id: string): BehaviorSubject<boolean> {
    if (!this.popupVisibilityMap.has(id)) {
      this.popupVisibilityMap.set(id, new BehaviorSubject<boolean>(false));
    }
    return this.popupVisibilityMap.get(id)!;
  }

  getPopupVisibility$(id: string): Observable<boolean> {
    return this.getPopupSubject(id).asObservable();
  }

  showPopup(id: string) {
    this.getPopupSubject(id).next(true);
  }

  hidePopup(id: string) {
    this.getPopupSubject(id).next(false);
  }
}
