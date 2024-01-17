import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import {  HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { OktaAuth } from '@okta/okta-auth-js';
import { LoginComponent } from './Component/logging/login/login.component';
import { Subscription } from 'rxjs';
import { StorageService } from './Auths/storage.service';
import { NewAuthService } from './Auths/new-auth.service';
import { Student } from '../Models/Student';
import { StudentService } from './group/student/student.service';
import { Teacher } from '../Models/Teacher';
import { TeacherService } from './group/teacher/teacher.service';
import { QuestionService } from './group/exam/question/question.service';
import { ExamService } from './group/exam/exam.service';
import { GroupService } from './group/group.service';
import { HeaderComponent } from './Component/header/header.component';


@Component({
    selector: 'app-root',
    standalone: true,
    imports: [
      HttpClientModule,
      CommonModule,
      RouterOutlet,
      HeaderComponent,
      HttpClientModule,
    ],
    templateUrl: './app.component.html',
    styleUrl: './app.component.css',
    providers: [
      StorageService,
      NewAuthService,
      StudentService,
      TeacherService,
      QuestionService,
      ExamService,
      GroupService
    ]
})



export class AppComponent {
  private roles: string[] = [];
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  username?: string;

  eventBusSub?: Subscription;

  constructor(
    private storageService: StorageService,
  ) {}

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();
  }





}
