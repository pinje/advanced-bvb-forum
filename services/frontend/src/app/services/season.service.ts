import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AddSeasonRequest } from '../models/request/addseason-request';
import { DeleteSeasonRequest } from '../models/request/deleteseason-request';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SeasonService {

  private apiUrl = environment + 'season';

  constructor(private http: HttpClient) {}

  getAllSeasons(): Observable<any> {
    return this.http.get(this.apiUrl, {withCredentials: true});
  }

  addSeason(body: AddSeasonRequest): Observable<any> {
    return this.http.post(this.apiUrl, body, {withCredentials: true});
  }

  deleteSeason(body: DeleteSeasonRequest):  Observable<any> {
    return this.http.delete(this.apiUrl + '/' + body.seasonId, {withCredentials: true});
  }
}
