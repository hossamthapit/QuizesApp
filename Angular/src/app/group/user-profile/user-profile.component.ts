import { Component, OnInit } from '@angular/core';
import { Student } from '../../../Models/Student';
import { FormBuilder, FormGroup, FormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { StudentService } from '../student/student.service';
import { TeacherService } from '../teacher/teacher.service';
import { Teacher } from '../../../Models/Teacher';
import { CommonModule } from '@angular/common';
import { NewAuthService } from '../../Auths/new-auth.service';
import { ExamRecord } from '../../../Models/ExamRecord';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [RouterLink,CommonModule,FormsModule,HttpClientModule],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css',
})
export class UserProfileComponent implements OnInit {

  isTeacher : boolean = false;
  student!: Student;
  teacher!: Teacher;
  user : Student = {} as Student;
  prevUrl : string = "";
  takenExamsRecord : ExamRecord[] = [];

  constructor(private formBuilder: FormBuilder, private studentService: StudentService,private teacherService: TeacherService, private route: ActivatedRoute,
    private router: Router, public authService : NewAuthService) {
      //this.prevUrl = this.router.getCurrentNavigation()!.previousNavigation!.finalUrl!.toString()
      console.log("prev url" ,this.prevUrl);

     }

    ngOnInit(): void {

      this.isTeacher = this.authService.isTeacher();

      this.route.params.subscribe(params => {
        if (params['userId']) {
          const isStudent = this.route.snapshot.queryParamMap.get('student');
          console.log(isStudent);
            if(isStudent){
              this.studentService.get(+params['userId']).subscribe(response => {
                this.student = response;
                this.user = response;
              });
              this.studentService.getStudentExamRecord(+params['userId']).subscribe(response => {
                console.log( "takenExamsRecord : ",response.content);
                this.takenExamsRecord = response.content;
                console.log('Type of takenExamsRecord:', Array.isArray(this.takenExamsRecord));

              });
            }
            else {
              this.teacherService.get(+params['userId']).subscribe(response => {
                this.teacher = response;
                this.user = response;
              });
            }
        }
      })
    }




}

class content{
  examRecord: ExamRecord[] = [];
}
