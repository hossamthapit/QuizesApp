import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Group } from '../../Models/Group';
import { Observable, catchError, map, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  localhost: string = "http://localhost:8080/api/";
  urlExams: string = "exams/";
  urlStudents: string = "students/";
  urlGroups: string = "groups/";
  urlExamRecords : string = "examRecords/"

  constructor(private http: HttpClient) {
    
  }
  
  getAll(): Observable<any> {
    return this.http.
      get(this.localhost + this.urlGroups).pipe(map((response: any) => {
        return response;
      }));
  }

  get(id: number): Observable<any> {
    return this.http.
      get(this.localhost + this.urlGroups + id).pipe(map((response: any) => {
        return response;
      }));
  }

  post(group: Group): Observable<any> {
    return this.http.
      post(this.localhost + this.urlGroups,group).pipe(map((response: any) => {
        return response;
      }));
  }

  put(group: Group): Observable<any> {
    return this.http.
      put(this.localhost + this.urlGroups + group.id,group).pipe(map((response: any) => {
        return response;
      }));
  }

  delete(id: number): Observable<any> {
    return this.http.
      delete(this.localhost + this.urlGroups + id).pipe(map((response: any) => {
        return response;
      }));
  }
  
}
