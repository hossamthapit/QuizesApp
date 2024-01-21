import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NewAuthService } from '../../../Auths/new-auth.service';
import { HttpClientModule } from '@angular/common/http';
import { StorageService } from '../../../Auths/storage.service';
import { Router } from '@angular/router';
import { HeaderComponent } from '../../header/header.component';
import { InputRequiredDirective } from '../../../Directives/input-required.directive';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    HeaderComponent,
    InputRequiredDirective
  ]
})
export class LoginComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private authService: NewAuthService,
    private storageService: StorageService,
    private router: Router
  ) {}

  loginForm!: FormGroup;
  private apiUrl = 'http://localhost:8080/api/groups'; // URL for the groups endpoint

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      id: 0,
      email: ['hossamthapit0@gmail.com', [Validators.required,Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]],
      password: ['12345678', [Validators.required, Validators.minLength(8)]],
    });
  }

  onSubmit(): void {
    this.authService.login(this.loginForm.value).subscribe(
      (data) => {
        console.log("hello ," ,data.user.pictureUrl);
        this.storageService.setItem('token', data.token);
        this.storageService.setItem('email', data.email);
        this.storageService.setItem('firstName', data.user.firstName);
        this.storageService.setItem('lastName', data.user.lastName);
        this.storageService.setItem('roles', data.user.roles);
        this.storageService.setItem('id', data.user.id);
        this.storageService.setItem('pictureUrl', data.user.pictureUrl);
        this.storageService.setAuthState(true);
        this.router.navigateByUrl('/groups');
        console.log(data);
      }
    )
  }
}

// import { Component } from '@angular/core';
// import { AuthService } from '../auth.service';
// import { authenticate } from '@okta/okta-auth-js';
// import { StorageService } from '../storage.service';
// import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
// import { HttpClient, HttpClientModule } from '@angular/common/http';
// import { StudentService } from '../group/student/student.service';
// import { Group } from '../../Models/Group';
// import { RouterLink } from '@angular/router';
// import { CommonModule } from '@angular/common';

// @Component({
//   selector: 'app-login',
//   standalone: true,
//   imports: [CommonModule, RouterLink,HttpClientModule,ReactiveFormsModule],
//   templateUrl: './login.component.html',
//   styleUrl: './login.component.css',
//     // AuthService,
//     // StudentService,
//   ]
// })
// export class LoginComponent {
//   constructor(
//     // private studentService: StudentService, 
//     // private authService: AuthService, 
//     private formBuilder: FormBuilder,
//     private storageService: StorageService,
//     private http: HttpClient
//     ) {}

//   loginForm!: FormGroup;

//   ngOnInit(): void {
//     this.loginForm = this.formBuilder.group({
//       id: 0,
//       email: ['asd@example.com', Validators.required],
//       password: ['123', Validators.required],
//     });
//   }


//   private apiUrl = 'http://localhost:8080/api/groups'; // URL for the groups endpoint

//   onSubmit(): void {

//     this.http.get<Group[]>(this.apiUrl, { headers: { 'Authorization': `Bearer ${this.storageService.getItem('token')}` } }).subscribe(
//       (data) => {
//         console.log(data);
//       }
//     )


    

//     return ;


//     // console.log(this.loginForm.value);
//     // if (!this.loginForm.valid) return ;

//     // this.authService.login(this.loginForm.value)
//     //   .subscribe(
//     //     (data) => {
//     //       // Handle successful login, e.g., store tokens or user data in storageService
//     //       console.log('Login successful', data);
//     //       this.storageService.setAuthState(true);
//     //       this.storageService.setItem('token', data.token);
//     //       this.storageService.setItem('email', data.email);
//     //     },
//     //     (error) => {
//     //       // Handle login error, show message to user
//     //       console.error('Login error', error);
//     //     }
//     //   );
//   }
  
// }

