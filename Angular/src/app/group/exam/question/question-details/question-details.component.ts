import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Question } from '../../../../../Models/Question';
import { QuestionService } from '../question.service';

@Component({
  selector: 'app-question-details',
  standalone: true,
  imports: [CommonModule, HttpClientModule, RouterLink, ReactiveFormsModule],
  templateUrl: './question-details.component.html',
  styleUrl: './question-details.component.css',
})
export class QuestionDetailsComponent {

  questionForm!: FormGroup;
  question: Question = {} as Question;
  examId !: number;
  editing: boolean = false;

  constructor(private formBuilder: FormBuilder, private questionService: QuestionService, 
    private route: ActivatedRoute,private router: Router) { }

  ngOnInit(): void {
    this.questionForm = this.formBuilder.group({
      id: 0,
      description: ['', Validators.required],
      answer: ['', Validators.required],
      score: ['', Validators.required],
      seconds: ['', Validators.required],
    });

    this.route.params.subscribe(params => {
      if (params['id']) {
        this.editing = true;
        this.questionService.get(+params['id']).subscribe(response => {
          this.question = response;
          this.questionForm.patchValue({
            id: this.question.id,
            description: this.question.description,
            answer: this.question.answer,
            score: this.question.score,
            seconds: this.question.seconds
          });
        });
      }
      if (params['examId']) {
        this.examId = +params['examId'];
      }

    })
  }

  onSubmit(): void {
    if (this.questionForm.valid) {

      console.log("question details onSubmit()",this.question,this.questionForm.value);

      this.question = this.questionForm.value;

      // Add your logic to create a new student
      if(this.editing){
        this.questionService.put(this.question).subscribe(response => {
          this.question = response;
          // this.router.navigateByUrl('/questions');
        });
      }
      else {
        console.log("question details onSubmit() addNew",this.question,this.questionForm.value);
        // this.router.navigateByUrl(this.router.url);
        // this.router.navigateByUrl("/exams/" + this.examId + "/questions");
        this.questionService.postExamQuestion(this.examId,this.question).subscribe(response => {
          console.log("question details onSubmit() addNew response message : ",response);
        });
      }
      this.questionForm.reset();
    } else {
      // Handle form validation errors or incomplete form
    }
  }

}
