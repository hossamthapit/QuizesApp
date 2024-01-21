import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Exam } from '../../../../Models/Exam';
import { Question } from '../../../../Models/Question';
import { ActivatedRoute, NavigationExtras, Router } from '@angular/router';
import { ExamService } from '../exam.service';
import { QuestionService } from '../question/question.service';
import { Observable, Subject, interval, min, takeUntil } from 'rxjs';
import { CommonModule } from '@angular/common';
import { ExamRecord } from '../../../../Models/ExamRecord';
import { StudentService } from '../../user/student/student.service';
import { Student } from '../../../../Models/Student';

@Component({
  selector: 'app-exam-process',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './exam-process.component.html',
  styleUrl: './exam-process.component.css',
})
export class ExamProcessComponent implements OnInit {

  QuestionForm!: FormGroup;

  exam: Exam = {} as Exam;
  curQuestion: Question = {} as Question;
  questions : Question[] = [];

  score : number = 0;
  curQuestionIdx : number = 0;
  startDate : Date = new Date();
  endDate : Date = new Date();
  student : Student = {} as Student;
  recordAdded : boolean = false;
  timerElement = interval(10).subscribe();

  constructor(private formBuilder: FormBuilder, private examService: ExamService, 
    private route: ActivatedRoute,private router: Router,private questionService: QuestionService,private studentService: StudentService) { }

  ngOnDestroy(){
    if(!this.recordAdded)
      this.addTheRecord(0,this.score,this.student,this.exam,new Date());
    this.timerElement.unsubscribe();
  }

  async loadQuestoins(examId: number) {
    const questions = await this.questionService.getExamQuestions(examId).toPromise();
    this.questions = questions.content;
    return this.questions;
  }

  ngOnInit(): void {

    console.log("ng on init called in exam process " );
    this.QuestionForm = this.formBuilder.group({
      id: 0,
      description: ['', Validators.required],
      answer: ['', Validators.required],
      timerHours: ['', Validators.required],
      timerMinutes: ['', Validators.required],
      timerSeconds: ['', Validators.required],
    });

    this.loadExam().then(() => {
      this.startDate = new Date();
      this.updateTimerComponent();
    });
  }

  updateTimerComponent(){
    this.timerElement = interval(100)
    .subscribe(x => {
      console.log(this.curQuestionIdx);
      const val = this.questions[this.curQuestionIdx].seconds - Math.floor(Math.abs(new Date().getTime() - this.startDate.getTime())/1000);
      this.calcTime(val);
    });
  }

  calcTime(val : number) {
    const hours = Math.floor(val / 3600);
    const minutes = Math.floor((val - (hours * 3600)) / 60);
    const seconds = Math.floor((val - (hours * 3600) - (minutes * 60)));
    
    if(hours <0 || minutes < 0 || seconds < 0){
      this.onSubmit();
      return ;
    }
    this.QuestionForm.patchValue({
      timerHours: hours,
      timerMinutes: minutes,
      timerSeconds: seconds
    });
  }

  loadExam() {
    return new Promise<void>((resolve, reject) => {
      this.route.params.subscribe(params => {
        if (params['examId']) {
          this.examService.get(+params['examId']).subscribe(response => {
            this.exam = response;
            this.loadQuestoins(this.exam.id)
              .then(questions => {
                this.questions = questions;
                resolve();
              })
              .catch(error => {
                reject(error);
              });
          });
        }
        if (params['studentId']) {
          this.studentService.get(+params['studentId']).subscribe(response => {
            this.student = response;
          });
        }
      });
    });
  }


  onSubmit(): void {

    this.startDate = new Date();

    this.checkAnswer(+this.QuestionForm.value.answer, +this.questions[this.curQuestionIdx].answer);

    this.QuestionForm.reset();
    
    if (this.curQuestionIdx == this.questions.length - 1) {
      console.log("exam is over score : ",this.score);
      this.timerElement.unsubscribe();

      // add exam record
      this.addTheRecord(0,this.score,this.student,this.exam,new Date());


      this.router.navigateByUrl("/exams/" + this.exam.id + "/result?score="+this.score+"");
      return ;
    }
    
    this.curQuestionIdx++;
    this.curQuestion = this.questions[this.curQuestionIdx];
    // this.setTimer(this.questions[this.curQuestionIdx].seconds);
  }
  
  
  checkAnswer(studentAnswer: number, correctAnswer: number) {
    console.log("answer : ",this.QuestionForm.value.answer,"correct answer : ",this.questions[this.curQuestionIdx].answer);
    if (studentAnswer == correctAnswer) {
      this.score += this.questions[this.curQuestionIdx].score;
    }
  }


  addTheRecord(id: number, score: number, student : Student, exam : Exam, examDate: Date){
      this.recordAdded = true;
      const record = new ExamRecord(0,this.score,this.student,this.exam,new Date());
      this.studentService.postExamRecord(record).subscribe(response => {
        console.log("record added : ", response);
      })
  }

}

class content {
  exam: Exam[] = [];
  examRecord: ExamRecord[] = [];
}
