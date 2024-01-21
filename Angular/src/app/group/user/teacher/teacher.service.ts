import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map, tap } from 'rxjs';
import { Teacher } from '../../../../Models/Teacher';

@Injectable({
  providedIn: 'root'
})
export class TeacherService {

  localhost: string = "http://localhost:8080/api/";
  urlTeachers: string = "teachers/";
  urlGroups: string = "groups/";

  constructor(private http: HttpClient) {}

  getAll(): Observable<any> {
    return this.http.
      get(this.localhost + this.urlTeachers).pipe(map((response: any) => {
        return response;
      }));
  }

  get(id: number): Observable<any> {
    return this.http.
      get(this.localhost + this.urlTeachers + id).pipe(map((response: any) => {
        return response;
      }));
  }

  post(data: Teacher): Observable<any> {
    return this.http.
      post(this.localhost + this.urlTeachers, data).pipe(map((response: any) => {
        return response;
      }));
  }

  put(data: Teacher): Observable<any> {
    return this.http.
      put(this.localhost + this.urlTeachers + data.id, data).pipe(map((response: any) => {
        return response;
      }));
  }

  delete(id: number): Observable<any> {
    return this.http.
      delete(this.localhost + this.urlTeachers + id).pipe(map((response: any) => {
        return response;
      }));
  }

  ///////////// Group teachers ///////////////

  getGroupTeachers(id: number): Observable<any>  {
    return this.http.
      get(this.localhost + this.urlGroups + id + "/" + this.urlTeachers).pipe(map((response: any) => {
        return response;
      }));
  }

  postTeacherToGroup(groupId: number, id: number): Observable<any>  {
    return this.http.post(this.localhost + this.urlGroups + groupId + "/" + this.urlTeachers + id, null);
  }

  deleteTeacherFromGroup(groupId: number, id: number): Observable<any> {
    return this.http.delete(this.localhost + this.urlGroups + groupId + "/" + this.urlTeachers + id);
  }


}