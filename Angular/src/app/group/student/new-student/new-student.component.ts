import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Student } from '../../../../Models/Student';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { StudentService } from '../student.service';

@Component({
  selector: 'app-new-student',
  standalone: true,
  imports: [CommonModule, HttpClientModule, RouterLink, ReactiveFormsModule],
  templateUrl: './new-student.component.html',
  styleUrl: './new-student.component.css',
})
export class NewStudentComponent {

  studentForm!: FormGroup;
  student: Student = {} as Student;
  editing: boolean = false;

  constructor(private formBuilder: FormBuilder, private studentService: StudentService, private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.studentForm = this.formBuilder.group({
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
        this.studentService.get(+params['id']).subscribe(response => {
          this.student = response;
          this.studentForm.patchValue({
            id: this.student.id,
            firstName: this.student.firstName,
            lastName: this.student.lastName,
            nationalId: this.student.nationalId,
            age: this.student.age,
            email: this.student.email,
            phoneNumber: this.student.phoneNumber,
            address: this.student.address,

          });
        });
      }
    })
  }

  onSubmit(): void {
    if (this.studentForm.valid) {

      console.log("form student",this.studentForm.value);

      const oldPic = this.student.pictureUrl;
      const urlParts = this.studentForm.value.pictureUrl.split('\\');
      const newPic = urlParts[urlParts.length-1];

      this.student = this.studentForm.value;
      
      this.student.pictureUrl = !this.student.pictureUrl.length ? oldPic : newPic;
      // Add your logic to create a new student
      if(this.editing){

        this.studentService.put(this.student).subscribe(response => {
          this.student = response;
          this.router.navigateByUrl('/students');
        });
      }
      else {
        this.router.navigateByUrl('/students');
        this.studentService.post(this.student);
      }
      this.studentForm.reset();
    } else {
      // Handle form validation errors or incomplete form
    }
  }

  addNewStudent() {
    throw new Error('Method not implemented.');
  }

}
