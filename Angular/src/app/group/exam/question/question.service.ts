import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Question } from '../../../../Models/Question';
import { Observable, catchError, map, tap } from 'rxjs';
import { Student } from '../../../../Models/Student';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  localhost: string = "http://localhost:8080/api/";
  urlQuestions: string = "questions/";
  urlExams: string = "exams/";


  constructor(private http: HttpClient) {}

  getAll(): Observable<any> {
    return this.http.
      get(this.localhost + this.urlQuestions).pipe(map((response: any) => {
        return response;
      }));
  }

  get(id: number): Observable<any> {
    return this.http.
      get(this.localhost + this.urlQuestions + id).pipe(map((response: any) => {
        return response;
      }));
  }

  post(data: Question): Observable<any> {
    return this.http.
      post(this.localhost + this.urlQuestions, data).pipe(map((response: any) => {
        return response;
      }));
  }

  put(data: Question): Observable<any> {
    return this.http.
      put(this.localhost + this.urlQuestions + data.id, data).pipe(map((response: any) => {
        return response;
      }));
  }

  delete(id: number): Observable<any> {
    return this.http.
      delete(this.localhost + this.urlQuestions + id).pipe(map((response: any) => {
        return response;
      }));
  }


  ////////////// Exam Questions //////////////////

  postExamQuestion(id: number, question: Question) {
    return this.http.
      post(this.localhost + this.urlExams +  id + "/" + this.urlQuestions, question).pipe(map((response: any) => {
      return response;
    }));
  }
  
  getExamQuestions(id : number) {
    return this.http.
      get(this.localhost + this.urlExams +  id + "/" + this.urlQuestions).pipe(map((response: any) => {
        return response;
      }));
  }

}
