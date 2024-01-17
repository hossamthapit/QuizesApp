import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Exam } from '../../../Models/Exam';
import { Observable, catchError, map, tap } from 'rxjs';
import { ExamRecord } from '../../../Models/ExamRecord';

@Injectable({
  providedIn: 'root'
})

export class ExamService {

  localhost: string = "http://localhost:8080/api/";
  urlExams: string = "exams/";
  urlStudents: string = "students/";
  urlGroups: string = "groups/";
  urlExamRecords : string = "examRecords/"


  constructor(private http: HttpClient) {  }

  getAll(): Observable<any> {
    return this.http.
      get(this.localhost + this.urlExams).pipe(map((response: any) => {
        return response;
      }));
  }

  get(id: number): Observable<any> {
    return this.http.
      get(this.localhost + this.urlExams + id).pipe(map((response: any) => {
        return response;
      }));
  }

  post(exam: Exam): Observable<any> {
    return this.http.
      post(this.localhost + this.urlExams,exam).pipe(map((response: any) => {
        return response;
      }));
  }

  put(exam: Exam): Observable<any> {
    return this.http.
      put(this.localhost + this.urlExams + exam.id,exam).pipe(map((response: any) => {
        return response;
      }));
  }

  delete(id: number): Observable<any> {
    return this.http.
      delete(this.localhost + this.urlExams + id).pipe(map((response: any) => {
        return response;
      }));
  }

  getByGroup(groupId : number) {
    return this.http.
      get(this.localhost +this.urlGroups + groupId + "/" + this.urlExams).pipe(map((response: any) => {
        return response;
      }));
  }

  postGroupExam( id:number, exam: Exam): Observable<any> {
    return this.http.
      post(this.localhost + this.urlGroups + id + '/' + this.urlExams,exam).pipe(map((response: any) => {
        return response;
      }));
  }


}

