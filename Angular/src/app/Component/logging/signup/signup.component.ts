import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NewAuthService } from '../../../Auths/new-auth.service';
import { StorageService } from '../../../Auths/storage.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { StudentService } from '../../../group/student/student.service';
import { HeaderComponent } from '../../header/header.component';
import { InputRequiredDirective } from '../../../Directives/input-required.directive';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    HeaderComponent,
    InputRequiredDirective
  ],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css',
})
export class SignupComponent {

  constructor(
    private formBuilder: FormBuilder,
    private authService: NewAuthService,
    private storageService: StorageService,
    private router: Router,
    private StudentService: StudentService
  ) { 
    this.studentForm = this.formBuilder.group({
      id: 0,
      firstName: ['Hossam', [Validators.required, Validators.maxLength(20), Validators.minLength(3)]],
      lastName: ['Ahmed', [Validators.required, Validators.maxLength(20), Validators.minLength(3)]],
      nationalId: ['29810162500393', [Validators.required, Validators.minLength(14), Validators.maxLength(14), Validators.pattern('^[0-9]*$')]],
      email: ['hossamthapit0@gmail.com', [Validators.required,Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]],
      password: ['12345678', [Validators.required, Validators.minLength(8)]],
      age: ['25', [Validators.min(4), Validators.max(85)]],
      phoneNumber: ['01067648695', [Validators.minLength(11), Validators.maxLength(11), Validators.pattern('^01[0125][0-9]{8}$')]],
      address: ['Egypt,Assiut.Ring Road', [Validators.minLength(3), Validators.maxLength(60)]],
      pictureUrl: [''],
    });
  }

  studentForm!: FormGroup;

  ngOnInit(): void {
  }

  onSubmit(): void {
    const urlParts = this.studentForm.value.pictureUrl.split('\\');
    const newPic = urlParts[urlParts.length-1];
    this.studentForm.value.pictureUrl = newPic;
    console.log("studentForm2: sunmit", this.studentForm.value , this.studentForm.valid , this.studentForm.hasError);
    if (this.studentForm.valid) {
      this.authService.signUp(this.studentForm.value);
      this.studentForm.reset();

    } else {
      // Handle form validation errors or incomplete form
    }

  }

}

