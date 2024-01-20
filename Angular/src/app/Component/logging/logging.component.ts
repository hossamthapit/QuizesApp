import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { SignupComponent } from './signup/signup.component';
import { LoginComponent } from './login/login.component';
import { CommonModule } from '@angular/common';
import { ExamService } from '../../group/exam/exam.service';
import { Exam } from '../../../Models/Exam';
import { MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { ModalComponent } from '../modal/modal.component';

@Component({
  selector: 'app-logging',
  standalone: true,
  imports: [RouterLink,RouterOutlet,SignupComponent,LoginComponent,CommonModule],
  templateUrl: './logging.component.html',
  styleUrl: './logging.component.css',
})
export class LoggingComponent {

  isLogin: boolean = true;

  setLogin( enabled: boolean): void {
    this.isLogin = enabled;
  }

  constructor(private examService: ExamService) {
  }
}
