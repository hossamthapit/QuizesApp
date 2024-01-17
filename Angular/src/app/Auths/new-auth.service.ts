import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from './storage.service';
import { Router } from '@angular/router';
import { Roles } from '../../Models/User';
import { Student } from '../../Models/Student';

@Injectable({
  providedIn: 'root'
})
export class NewAuthService {


  private apiUrl = 'http://localhost:8080/api/auth'; // Your authentication API endpoint


  constructor(private http: HttpClient, private storageService: StorageService, private router: Router) {}
  
  
  run() {
    this.http.get(this.apiUrl).subscribe(data => {
      console.log(data);
    });
  }
  
  // register, login, logout
  login(credentials: { email: string; password: string }): Observable<any> {   
    return this.http.post<any>(`${this.apiUrl}/login`, credentials);
  }

  signUp(student : Student) {   
    
    return this.http.post<any>(`${this.apiUrl}/signup`, student).subscribe(data => {
      console.log("response: ",data);
    });
  }

  logout(){
    this.storageService.clear();
    this.router.navigateByUrl('/');
  }

  isLoggedIn(){
    return this.storageService.isLoggedIn();
  }

  isTeacher() : boolean {
    return this.storageService.getRoles() == Roles.teacher;
  }

  isAdmin(): boolean {
    return this.storageService.getRoles() == Roles.admin;
  }
  isStudent() : boolean {
    return this.storageService.getRoles() == Roles.student;
  }


  loggedUserId(){
    return this.storageService.getItem<string>('id');
  }

  loggedUserPictureUrl(){
    return this.storageService.getItem<string>('pictureUrl');
  }




  

  
  // refreshToken() {
    //   return this.http.post(AUTH_API + 'refreshtoken', { }, httpOptions);
    // }
    
  }