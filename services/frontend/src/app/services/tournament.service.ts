import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DeleteTournamentRequest } from '../models/request/tournament/deletetournament-request';
import { GetTournamentRequest } from '../models/request/tournament/gettournament-request';

@Injectable({
  providedIn: 'root'
})
export class TournamentService {
  private apiUrl = 'http://localhost:8222/api/v1/tournament';
  
  constructor(private http: HttpClient) {}

  getAllTournaments(): Observable<any> {
    return this.http.get(this.apiUrl, {withCredentials: true});
  }

  addTournament(body: FormData): Observable<any> {
    return this.http.post(this.apiUrl, body,  { withCredentials: true });
  }

  getTournament(body: GetTournamentRequest): Observable<any> {
    return this.http.post(this.apiUrl + '/' + body.tournamentId, {withCredentials: true});
  }

  deleteTournament(body: DeleteTournamentRequest):  Observable<any> {
    return this.http.delete(this.apiUrl + '/' + body.tournamentId, {withCredentials: true});
  }
}
