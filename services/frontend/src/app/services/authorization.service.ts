import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {

  private apiUrl = 'http://localhost:8222/api/v1/auth/';

  constructor(
    private http: HttpClient
  ) { }

  getUserRole(): Observable<any> {
    return this.http.post(this.apiUrl + 'authorities', {}, {withCredentials: true});
  }
}
