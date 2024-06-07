import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8222/api/v1/user';
  
  constructor(private http: HttpClient) {}

  deleteUser(userId: string):  Observable<any> {
    return this.http.delete(this.apiUrl + '/protected/' + userId, {withCredentials: true});
  }
}
