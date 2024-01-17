import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ExamService } from './exam.service';
import { Exam } from '../../../Models/Exam';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Question } from '../../../Models/Question';
import { GroupService } from '../group.service';
import { Group } from '../../../Models/Group';
import { NewAuthService } from '../../Auths/new-auth.service';
import { ExamRecord } from '../../../Models/ExamRecord';
import { StudentService } from '../student/student.service';

@Component({
  selector: 'app-exam',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './exam.component.html',
  styleUrl: './exam.component.css',
})
export class ExamComponent {

  exams: Exam[] = [];
  group: Group = {} as Group;
  isTeacher: boolean = false;
  isStudent: boolean = false;


  constructor(private groupService: GroupService, private examService: ExamService, private router: Router,
    private route: ActivatedRoute, public authService: NewAuthService,private studentService: StudentService) { }

  ngOnInit() {

    this.isTeacher = this.authService.isTeacher();
    this.isStudent = this.authService.isStudent();

    
    this.route.params.subscribe(params => {
      if (params['groupId']) {
        this.examService.getByGroup(+params['groupId']).subscribe(response => {
           if(!this.isStudent)this.exams = response.content;  // to prevent load and hide again
           console.log(this.exams)
        });
        this.groupService.get(+params['groupId']).subscribe(response => {
          this.group = response;
          // existing group id
          if (this.group) {
            this.examService.getByGroup(this.group.id).subscribe(response => {
              if(this.isStudent){
                const id = this.authService.loggedUserId()?.toString();
                this.studentService.getStudentExamRecord(+id!).subscribe(studentExamRecord => {
                    console.log("exams and records",response.content,studentExamRecord.content);
                    const examsWithRecords = this.filterExamsWithoutRecords(response.content, studentExamRecord.content);
                    console.log("filtered exams ",examsWithRecords);
                    this.exams = examsWithRecords;
                    //this.exams = examsWithRecords;
                    
                  })
                }
              });
              
          }
          // wrong group id redirect to groups page or make an error page
          else {

          }
        })
      }
      // Get All Students No Group Id found
      else {
        this.examService.getAll().subscribe(response => { this.exams = response.content; console.log(this.exams) });
      }
    });

  }
  filterExamsWithoutRecords(exams: Exam[], records: ExamRecord[]): Exam[] {
    const examRecordIds = new Set(records.map(record => record.exam.id));
    return exams.filter(exam => !examRecordIds.has(exam.id));;
  }
  

  startExam(examId: number) {
    this.router.navigateByUrl("/exams/" + examId + "/questions");
  }

  // groupTeachers(arg0: number) {
  //   this.router.navigateByUrl("/groups/" + arg0 + "/teachers" );
  // }

  deleteExam(examId: number) {
    if(confirm("Are you sure to delete "+name)) {

    this.examService.delete(examId).subscribe(
      response => {
        this.exams = this.exams.filter(exam => exam.id !== examId);
        // this.router.navigateByUrl("/groups/" + this.group.id + "/exams" );
      }
    )
    }
  }
  updateExam(arg0: number) {
    throw new Error('Method not implemented.');
  }

}

class content{
  exam: Exam[] = [];
  examRecord: ExamRecord[] = [];
}