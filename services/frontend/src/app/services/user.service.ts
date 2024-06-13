import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = environment.baseUrl + 'user';
  
  constructor(private http: HttpClient) {}

  deleteUser(userId: string):  Observable<any> {
    return this.http.delete(this.apiUrl + '/protected/' + userId, {withCredentials: true});
  }
}
