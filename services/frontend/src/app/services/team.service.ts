import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DeleteTeamRequest } from '../models/request/team/deleteteam-request';

@Injectable({
  providedIn: 'root'
})
export class TeamService {
  private apiUrl = 'http://localhost:8222/api/v1/team';
  
  constructor(private http: HttpClient) {}

  getAllTeams(): Observable<any> {
    return this.http.get(this.apiUrl, {withCredentials: true});
  }

  addTeam(body: FormData): Observable<any> {
    return this.http.post(this.apiUrl, body,  { withCredentials: true });
  }

  getTeam(teamId: number): Observable<any> {
    return this.http.get(this.apiUrl + '/' + teamId, {withCredentials: true});
  }

  deleteTeam(body: DeleteTeamRequest):  Observable<any> {
    return this.http.delete(this.apiUrl + '/' + body.teamId, {withCredentials: true});
  }
}
