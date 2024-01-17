import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map, tap } from 'rxjs';
import { Student } from '../../../Models/Student';
import { ExamRecord } from '../../../Models/ExamRecord';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  localhost: string = "http://localhost:8080/api/";
  urlExams: string = "exams/";
  urlStudents: string = "students/";
  urlGroups: string = "groups/";
  urlExamRecords: string = "examRecords/"


  constructor(private http: HttpClient) { }

  getAll(): Observable<any> {
    return this.http.
      get(this.localhost + this.urlStudents).pipe(map((response: any) => {
        return response;
      }));
  }

  get(id: number): Observable<any> {
    return this.http.
      get(this.localhost + this.urlStudents + id).pipe(map((response: any) => {
        return response;
      }));
  }

  post(student: Student): Observable<any> {
    return this.http.
      post(this.localhost + this.urlStudents, student).pipe(map((response: any) => {
        return response;
      }));
  }

  put(student: Student): Observable<any> {
    return this.http.
      put(this.localhost + this.urlStudents + student.id, student).pipe(map((response: any) => {
        return response;
      }));
  }

  delete(id: number): Observable<any> {
    return this.http.
      delete(this.localhost + this.urlStudents + id).pipe(map((response: any) => {
        return response;
      }));
  }

  ///////////// Group students ////////////////
  getGroupStudents(groupId: number): Observable<any>  {
    return this.http.
      get(this.localhost + this.urlGroups + groupId + "/" + this.urlStudents).pipe(map((response: any) => {
        return response;
      }));
  }

  postStudentToGroup(groupId: number, studentId: number): Observable<any>  {
    return this.http.post(this.localhost + this.urlGroups + groupId + "/" + this.urlStudents + studentId, null);
  }

  deleteStudentFromGroup(groupId: number, studentId: number): Observable<any> {
    return this.http.delete(this.localhost + this.urlGroups + groupId + "/" + this.urlStudents + studentId);
  }
  
  ///////////// exam records ////////////////
  postExamRecord(record: ExamRecord): Observable<any> {
    return this.http.
    post(this.localhost + this.urlExamRecords, record).pipe(map((response: any) => {
      return response;
    }));
  }
  
  getStudentExamRecord(studentId: number): Observable<any>  {
    return this.http.
    get(this.localhost + this.urlStudents + studentId + "/" + this.urlExamRecords).pipe(map((response: any) => {
      return response;
    }));
  }
  

}
