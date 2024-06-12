import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DeleteTournamentRequest } from '../models/request/tournament/deletetournament-request';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TournamentService {
  private apiUrl = environment + 'tournament';
  
  constructor(private http: HttpClient) {}

  getAllTournaments(): Observable<any> {
    return this.http.get(this.apiUrl + '/protected', {withCredentials: true});
  }

  addTournament(body: FormData): Observable<any> {
    return this.http.post(this.apiUrl + '/protected', body,  { withCredentials: true });
  }

  getTournament(tournamentId: number): Observable<any> {
    return this.http.get(this.apiUrl + '/get/' + tournamentId, {withCredentials: true});
  }

  deleteTournament(body: DeleteTournamentRequest):  Observable<any> {
    return this.http.delete(this.apiUrl + '/protected/' + body.tournamentId, {withCredentials: true});
  }
}
