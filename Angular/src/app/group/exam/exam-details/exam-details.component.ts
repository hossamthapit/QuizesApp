import { Component } from '@angular/core';
import { ExamService } from '../exam.service';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Exam } from '../../../../Models/Exam';
import { GroupService } from '../../group.service';
import { Group } from '../../../../Models/Group';
import { InputRequiredDirective } from '../../../Directives/input-required.directive';

@Component({
  selector: 'app-exam-details',
  standalone: true,
  imports: [CommonModule, HttpClientModule, RouterLink, ReactiveFormsModule,InputRequiredDirective],
  templateUrl: './exam-details.component.html',
  styleUrl: './exam-details.component.css',
})
export class ExamDetailsComponent {
  examForm!: FormGroup;
  exam: Exam = {} as Exam;
  group: Group = {} as Group;
  examId !: number;
  groupId !: number;
  editing: boolean = false;
  prevUrl : string | null = null;

  constructor(private formBuilder: FormBuilder, private examService: ExamService, 
    private route: ActivatedRoute,private router: Router,private groupService: GroupService) {
      var url = this.router.getCurrentNavigation()?.previousNavigation?.finalUrl ;
      this.prevUrl = url ? url.toString() : null;
     }

     goBack(){
      this.router.navigate([this.prevUrl]);
     }

  ngOnInit(): void {
    console.log("exam details ngOnInit() called");
    this.examForm = this.formBuilder.group({
      id: 0,
      description: ['', [Validators.required, Validators.maxLength(60), Validators.minLength(3)]],
      title: ['', [Validators.required, Validators.maxLength(60), Validators.minLength(3)]],
    });

    this.route.params.subscribe(params => {
      if (params['groupId']) {
        this.groupId = +params['groupId'];
        this.groupService.get(this.groupId).subscribe(response => {
          console.log("exam group: ",response);
          this.group = response;
        })
      }
    })

    this.route.params.subscribe(params => {
      if (params['examId']) {
        this.editing = true;
        this.examService.get(+params['examId']).subscribe(response => {
          this.exam = response;
          this.examForm.patchValue({
            id: this.exam.id,
            description: this.exam.description,
            title: this.exam.title,
          });
        });
      }
    })
  }

  onSubmit(): void {
    if (this.examForm.valid) {

      this.exam = this.examForm.value;

      if(this.editing){
        this.examService.put(this.exam).subscribe(response => {
          this.exam = response;
          this.goBack();
        });
      }
      else {
        this.exam.group = this.group;
        this.exam.group = this.group;
        this.examService.postGroupExam(this.group.id,this.exam).subscribe(response => {
          this.goBack();
        });
      }
      this.examForm.reset();
    } else {
      // Handle form validation errors or incomplete form
    }
  }

  

}
