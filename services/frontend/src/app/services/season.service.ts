import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AddSeasonRequest } from '../models/request/addaseason-request';
import { DeleteSeasonRequest } from '../models/request/deleteseason-request';

@Injectable({
  providedIn: 'root'
})
export class SeasonService {

  private apiUrl = 'http://localhost:8222/api/v1/season';

  constructor(private http: HttpClient) {}

  getAllSeasons(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  addSeason(body: AddSeasonRequest): Observable<any> {
    return this.http.post(this.apiUrl, body);
  }

  deleteSeason(body: DeleteSeasonRequest):  Observable<any> {
    return this.http.delete(this.apiUrl + '/' + body.seasonId);
  }
}
