import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Question } from '../../../../Models/Question';
import { QuestionService } from './question.service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ExamComponent } from '../exam.component';
import { ExamService } from '../exam.service';
import { Exam } from '../../../../Models/Exam';

@Component({
  selector: 'app-question',
  standalone: true,
  imports: [CommonModule,RouterLink],
  templateUrl: './question.component.html',
  styleUrl: './question.component.css',
})
export class QuestionComponent implements OnInit {

  questions: Question[] = [];
  exam!: Exam;
  addingQuestionsToExam: boolean = false;

  addNewQuestion(examId: number) {

  }

  constructor(private questionService: QuestionService,private examService: ExamService ,
     private route: ActivatedRoute,private router: Router) { }

  ngOnInit() {
      
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.examService.get(+params['id']).subscribe(response => {
          this.exam = response;
          // existing group id
          if (this.exam != undefined) {
          this.questionService.getExamQuestions(this.exam.id).subscribe(response => {
            this.questions = response.content;
          });
        }
        // wrong group id redirect to groups page or make an error page
        else {
          
        }
      })
    }
    // Get All Students No Group Id found
    else {
      this.questionService.getAll().subscribe(response => { this.questions = response.content; console.log(this.questions) });
    }
  });
}
  // ngOnInit() {

  //   this.questionService.getQuestions().subscribe(response => { this.questions = response.content; console.log(this.questions) });

  // }

  // groupTeachers(arg0: number) {
  //   this.router.navigateByUrl("/groups/" + arg0 + "/teachers" );
  // }

  deleteQuestion(questionid : number) {
    if(confirm("Are you sure to delete "+name)) {

    this.questionService.delete(questionid).subscribe(response => {
      this.questions = this.questions.filter(question => question.id !== questionid);
    });
  }
  }

  editQuestion(questionid : number) {
    this.router.navigateByUrl("/questions/" + questionid + "/edit");
  }
}

class content {
  questions : Question[] = [];
}
