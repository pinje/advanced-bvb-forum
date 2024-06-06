import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AddPostRequest } from '../models/request/post/addpost-request';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private apiUrl = 'http://localhost:8222/api/v1/post';

  constructor(private http: HttpClient) {}

  getAllPosts(): Observable<any> {
    return this.http.get(this.apiUrl + '/get', {withCredentials: true});
  }

  addPost(body: AddPostRequest): Observable<any> {
    return this.http.post(this.apiUrl + '/protected', body, {withCredentials: true});
  }

  deletePost(postId: number):  Observable<any> {
    return this.http.delete(this.apiUrl + '/protected/' + postId, {withCredentials: true});
  }
}
