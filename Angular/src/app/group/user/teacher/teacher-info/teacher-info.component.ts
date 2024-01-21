import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TeacherService } from '../teacher.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Teacher } from '../../../../../Models/Teacher';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { InputRequiredDirective } from '../../../../Directives/input-required.directive';

@Component({
  selector: 'app-teacher-info',
  standalone: true,
  imports: [CommonModule,HttpClientModule,ReactiveFormsModule,InputRequiredDirective],
  templateUrl: './teacher-info.component.html',
  styleUrl: './teacher-info.component.css',
})
export class TeacherInfoComponent {
  teacherForm!: FormGroup;
  teacher: Teacher = {} as Teacher;
  editing: boolean = false;

  prevUrl : string | null = null;
    constructor(private formBuilder: FormBuilder, private teacherService: TeacherService, private route: ActivatedRoute,
      private router: Router) {
        
        var url = this.router.getCurrentNavigation()?.previousNavigation?.finalUrl ;
        this.prevUrl = url ? url.toString() : null;

       }
      
      goBack(){
       this.router.navigate([this.prevUrl]);
      }
  ngOnInit(): void {
    this.teacherForm = this.formBuilder.group({
      id: 0,
      firstName: ['', [Validators.required, Validators.maxLength(20), Validators.minLength(3)]],
      lastName: ['', [Validators.required, Validators.maxLength(20), Validators.minLength(3)]],
      nationalId: ['', [Validators.required, Validators.minLength(14), Validators.maxLength(14), Validators.pattern('^[0-9]*$')]],
      age: ['', [Validators.min(4), Validators.max(85)]],
      pictureUrl: [''],
      email: ['', [Validators.required,Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]],
      phoneNumber: ['', [Validators.minLength(11), Validators.maxLength(11), Validators.pattern('^01[0125][0-9]{8}$')]],
      address: ['', [Validators.minLength(3), Validators.maxLength(60)]],
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
          console.log("edit teacher",this.teacher);
          this.teacher = response;
           //this.router.navigateByUrl('/teachers');
        });
      }
      else {
        console.log("new teacher",this.teacher);
        this.teacherService.post(this.teacher).subscribe(response => {
          console.log("add teacher",this.teacher);
        })
        //this.router.navigateByUrl('/teachers');
      }
      this.teacherForm.reset();
      this.goBack();
    } else {
      // Handle form validation errors or incomplete form
    }
  }

  addNewTeacher() {
    throw new Error('Method not implemented.');
  }

}
