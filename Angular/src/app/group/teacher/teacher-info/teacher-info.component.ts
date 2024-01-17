import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TeacherService } from '../teacher.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Teacher } from '../../../../Models/Teacher';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-teacher-info',
  standalone: true,
  imports: [CommonModule,HttpClientModule,ReactiveFormsModule],
  templateUrl: './teacher-info.component.html',
  styleUrl: './teacher-info.component.css',
})
export class TeacherInfoComponent {
  teacherForm!: FormGroup;
  teacher: Teacher = {} as Teacher;
  editing: boolean = false;

  constructor(private formBuilder: FormBuilder, private teacherService: TeacherService, private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.teacherForm = this.formBuilder.group({
      id: 0,
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      nationalId: ['', Validators.required],
      age: ['', [Validators.required, Validators.min(1)]],
      pictureUrl: [''],
      email: ['', Validators.required],
      phoneNumber: ['', Validators.required],
      address: ['', Validators.required],
    });

    this.route.params.subscribe(params => {
      if (params['id']) {
        this.editing = true;
        this.teacherService.get(+params['id']).subscribe(response => {
          this.teacher = response;
          this.teacherForm.patchValue({
            id: this.teacher.id,
            firstName: this.teacher.firstName,
            lastName: this.teacher.lastName,
            nationalId: this.teacher.nationalId,
            age: this.teacher.age,
            email: this.teacher.email,
            phoneNumber: this.teacher.phoneNumber,
            address: this.teacher.address,
          });
        });
      }
    })
  }

  onSubmit(): void {
    if (this.teacherForm.valid) {

      const oldPic = this.teacher.pictureUrl;
      const urlParts = this.teacherForm.value.pictureUrl.split('\\');
      const newPic = urlParts[urlParts.length-1];

      this.teacher = this.teacherForm.value;
      
      this.teacher.pictureUrl = !this.teacher.pictureUrl.length ? oldPic : newPic;

      // Add your logic to create a new teacher
      if(this.editing){
        this.teacherService.put(this.teacher).subscribe(response => {
          this.teacher = response;
           this.router.navigateByUrl('/teachers');
        });
      }
      else {
        console.log("teacher",this.teacher);
        this.teacherService.post(this.teacher);
        this.router.navigateByUrl('/teachers');
      }
      this.teacherForm.reset();
    } else {
      // Handle form validation errors or incomplete form
    }
  }

  addNewTeacher() {
    throw new Error('Method not implemented.');
  }

}
