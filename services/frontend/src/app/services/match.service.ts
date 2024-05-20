import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AddMatchRequest } from '../models/request/match/addmatch-request';

@Injectable({
  providedIn: 'root'
})
export class MatchService {
  private apiUrl = 'http://localhost:8222/api/v1/match';
  
  constructor(private http: HttpClient) {}

  getAllMatchesBySeason(seasonId: number): Observable<any> {
    return this.http.get(this.apiUrl + '/' + seasonId, {withCredentials: true});
  }

  addMatch(body: AddMatchRequest): Observable<any> {
    return this.http.post(this.apiUrl, body,  { withCredentials: true });
  }

  // getTournament(body: GetTournamentRequest): Observable<any> {
  //   return this.http.post(this.apiUrl + '/' + body.tournamentId, {withCredentials: true});
  // }

  deleteMatch(matchId: number):  Observable<any> {
    return this.http.delete(this.apiUrl + '/' + matchId, {withCredentials: true});
  }
}
