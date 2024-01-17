import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { User } from '../../../Models/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  localhost: string = "http://localhost:8080/api/";
  urlUsers: string = "users/";

  constructor(private http: HttpClient) {}

  getAll(): Observable<any> {
    return this.http.
      get(this.localhost + this.urlUsers).pipe(map((response: any) => {
        return response;
      }));
  }

  get(id: number): Observable<any> {
    return this.http.
      get(this.localhost + this.urlUsers + id).pipe(map((response: any) => {
        return response;
      }));
  }

  post(data: User): Observable<any> {
    return this.http.
      post(this.localhost + this.urlUsers, data).pipe(map((response: any) => {
        return response;
      }));
  }

  put(data: User): Observable<any> {
    return this.http.
      put(this.localhost + this.urlUsers + data.id, data).pipe(map((response: any) => {
        return response;
      }));
  }

  delete(id: number): Observable<any> {
    return this.http.
      delete(this.localhost + this.urlUsers + id).pipe(map((response: any) => {
        return response;
      }));
  }
}
